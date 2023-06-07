package com.photoism.cms.domain.user;

import com.photoism.cms.common.exception.ObjectAlreadExistException;
import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.exception.SigninFailedException;
import com.photoism.cms.common.exception.UserNotFoundException;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.domain.auth.entity.RoleEntity;
import com.photoism.cms.domain.auth.repository.RoleRepository;
import com.photoism.cms.domain.etc.entity.CodeEntity;
import com.photoism.cms.domain.etc.repository.CodeRepository;
import com.photoism.cms.domain.user.dto.*;
import com.photoism.cms.domain.user.entity.UserEntity;
import com.photoism.cms.domain.user.repository.UserQueryRepository;
import com.photoism.cms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final CodeRepository codeRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public CommonIdResult addUser(UserReqDto reqDto) {
        if (userRepository.findByUserIdAndDel(reqDto.getUserId(), false).isPresent())
            throw new ObjectAlreadExistException("userId");

        UserEntity userEntity = reqDto.toEntity();
        userEntity.setPassword(passwordEncoder.encode(reqDto.getUserId()));

        List<RoleEntity> roleEntityList = new ArrayList<>();
        CodeEntity codeEntity = codeRepository.findByCode(reqDto.getRole().name()).orElseThrow(() -> new ObjectNotFoundException("role"));
        if (!codeEntity.getCodeGroup().equals("ROLE"))
            throw new ObjectNotFoundException("role");

        RoleEntity roleEntity = RoleEntity.builder()
                .user(userEntity)
                .roleCd(codeEntity.getCode())
                .build();
        roleEntityList.add(roleEntity);
        userEntity.setRoles(roleEntityList);

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

        if (reqDto.getRole() != null) {
            List<RoleEntity> oldRoleEntityList = roleRepository.findByUser(userEntity);
            roleRepository.deleteAll(oldRoleEntityList);

            List<RoleEntity> newRoleEntityList = new ArrayList<>();
            CodeEntity codeEntity = codeRepository.findByCode(reqDto.getRole().name()).orElseThrow(() -> new ObjectNotFoundException("role"));
            RoleEntity roleEntity = RoleEntity.builder()
                    .user(userEntity)
                    .roleCd(codeEntity.getCode())
                    .build();
            newRoleEntityList.add(roleEntity);
            userEntity.setRoles(newRoleEntityList);
        }

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
        return userRepository.findByNameAndPhone(name, phone).stream().map(UserEntity::getUserId).toList();
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
        userEntity.setTmpPassword(passwordEncoder.encode(userEntity.getUserId()));
        return new CommonIdResult(userEntity.getId());
    }

    @Transactional(readOnly = true)
    public UserListResDto getStaffUserList(String userId, String name, String roleCd, String phone, String email, Boolean approved, Pageable pageable) {
        return new UserListResDto(userQueryRepository.findStaffUserList(userId, name, roleCd, phone, email, approved, pageable));
    }

    @Transactional(readOnly = true)
    public UserListResDto getStoreUserList(String userId, String name, String phone, String email, Boolean approved, Pageable pageable) {
        return new UserListResDto(userQueryRepository.findStoreUserList(userId, name, phone, email, approved, pageable));
    }

    @Transactional(readOnly = true)
    public List<UserResDto> getUserForStoreMapping(String userId, String name) {
        return userQueryRepository.getUserForStoreMapping(userId, name);
    }
}
