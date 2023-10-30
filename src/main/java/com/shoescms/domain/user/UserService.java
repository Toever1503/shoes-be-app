package com.shoescms.domain.user;

import com.google.gson.Gson;
import com.shoescms.common.config.CommonConfig;
import com.shoescms.common.enums.RoleEnum;
import com.shoescms.common.exception.*;
import com.shoescms.common.model.response.CommonIdResult;
import com.shoescms.common.service.MailService;
import com.shoescms.domain.auth.entity.RoleEntity;
import com.shoescms.domain.auth.repository.RoleRepository;
import com.shoescms.domain.user.dto.*;
import com.shoescms.domain.user.entity.UserEntity;
import com.shoescms.domain.user.repository.UserQueryRepository;
import com.shoescms.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final RoleRepository roleRepository;
    private final CommonConfig config;
    private final MailService mailService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserQueryRepository userQueryRepository, RoleRepository roleRepository, CommonConfig config, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userQueryRepository = userQueryRepository;
        this.roleRepository = roleRepository;
        this.config = config;
        this.mailService = mailService;
    }

//    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserQueryRepository userQueryRepository, RoleRepository roleRepository, CommonConfig config, MailService mailService) {
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//        this.userQueryRepository = userQueryRepository;
//        this.roleRepository = roleRepository;
//        this.config = config;
//        this.mailService = mailService;
//        initRole();
// UserEntity adminUser =        this.userRepository.findByUserNameAndDel("admin", false).orElse(
//         UserEntity.builder()
//                 .userName("admin")
//                 .password(this.passwordEncoder.encode("123456"))
//                 .name("admin")
//                 .approved(true)
//                 .email("admin@email.com")
//                 .phone("0958572838")
//                 .role(this.roleRepository.findByRoleCd(RoleEnum.ROLE_ADMIN.getTitle()))
//                 .del(false)
//                 .build()
// );
// this.userRepository.saveAndFlush(adminUser);
//    }

    private void initRole(){
        this.roleRepository.saveAndFlush(RoleEntity.builder()
                        .id(RoleEnum.ROLE_ADMIN.getId())
                        .roleCd(RoleEnum.ROLE_ADMIN.getTitle())
                .build());
        this.roleRepository.saveAndFlush(RoleEntity.builder()
                .id(RoleEnum.ROLE_USER.getId())
                .roleCd(RoleEnum.ROLE_USER.getTitle())
                .build());
    }
    @Transactional
    public CommonIdResult addUser(UserReqDto reqDto) {
        if (userRepository.findByUserNameAndDel(reqDto.getUserName(), false).isPresent())
            throw new ObjectAlreadExistException("userName");

        UserEntity userEntity = reqDto.toEntity();
        System.out.println("pass = " + reqDto.getPassword());
        userEntity.setPassword(passwordEncoder.encode(reqDto.getPassword()));

        userEntity.setApproved(true);

        userEntity.setRole(this.roleRepository.findByRoleCd(reqDto.getRole().getTitle()));

        return new CommonIdResult(userRepository.save(userEntity).getId());
    }

    @Transactional
    public CommonIdResult approval(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setApproved(true);
        return new CommonIdResult(userEntity.getId());
    }

    @Transactional
    public CommonIdResult updateUser(Long id, UserUpdateReqDto reqDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.update(reqDto);
        userEntity.setRole(this.roleRepository.findByRoleCd(reqDto.getRole().getTitle()));
        return new CommonIdResult(userEntity.getId());
    }

    @Transactional
    public CommonIdResult deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setDel();
        return new CommonIdResult(id);
    }

    @Transactional(readOnly = true)
    public UserDetailResDto getDetail(Long id) {
        return userQueryRepository.getDetail(id).orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<String> findId(String name, String phone) {
        return userRepository.findByNameAndPhone(name, phone).stream().map(UserEntity::getUsername).toList();
    }

    @Transactional(readOnly = true)
    public void findPassword(String userName, String name, String email) throws Exception {
        UserEntity userEntity = userRepository.findByUserNameAndNameAndEmail(userName, name, email).orElseThrow(UserNotFoundException::new);

        // make expire time
        LocalDateTime currentTime = LocalDateTime.now().plusDays(1L);
        String expire = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Gson gson = new Gson();
        UserPasswordCodeDto dto = new UserPasswordCodeDto();
        dto.setId(userEntity.getId());
        dto.setExpire(expire);
        String param = gson.toJson(dto);

        // encrypt info
        String encParam = "www";
        ClassPathResource resource = new ClassPathResource("html/mail-body.html");
        if (resource.exists()) {
            try {
                log.info("start send mail");
                InputStreamReader inputStreamReader =  new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                Stream<String> streamOfString= new BufferedReader(inputStreamReader).lines();
                String content = streamOfString.collect(Collectors.joining());
                content = content.replaceAll("__enc_param", encParam);
                content = content.replaceAll("__password_change_url", config.getVnpayRedirectURl());
                log.info(content);
                if (!mailService.sendMail(userEntity.getEmail(), "포토이즘 비밀번호 변경", content))
                    throw new AuthFailedException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new ObjectNotFoundException("mail-body.html");
        }
    }

    @Transactional
    public CommonIdResult changePassword(Long id, ChangePasswordReqDto reqDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String oldPassword = userEntity.getPassword() != null ? userEntity.getPassword() : userEntity.getTmpPassword();
        if (!passwordEncoder.matches(reqDto.getOldPassword(), oldPassword)) {
            throw new SigninFailedException("ID/PW");
        }

        userEntity.setPassword(passwordEncoder.encode(reqDto.getNewPassword()));
        userEntity.setTmpPassword(null);
        return new CommonIdResult(userEntity.getId());
    }

    @Transactional
    public CommonIdResult resetPassword(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setPassword(null);
        userEntity.setTmpPassword(passwordEncoder.encode(userEntity.getUsername()));
        return new CommonIdResult(userEntity.getId());
    }

    @Transactional(readOnly = true)
    public UserListResDto getStaffUserList(String userName, String name, String roleCd, String phone, String email, Boolean approved, Pageable pageable) {
        return new UserListResDto(userQueryRepository.findStaffUserList(userName, name, roleCd, phone, email, approved, pageable));
    }

    @Transactional(readOnly = true)
    public UserListResDto getStoreUserList(String userName, String name, String phone, String email, Boolean approved, Pageable pageable) {
        return new UserListResDto(userQueryRepository.findStoreUserList(userName, name, phone, email, approved, pageable));
    }

    @Transactional(readOnly = true)
    public List<UserResDto> getUserForStoreMapping(String userName, String name) {
        return userQueryRepository.getUserForStoreMapping(userName, name);
    }

    @Transactional(readOnly = true)
    public UserEntity findByUserId(String userName) {
        return userRepository.findByUserName(userName);
    }
}
