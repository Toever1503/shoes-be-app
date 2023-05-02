package com.photoism.cms.domain.user;

import com.photoism.cms.common.enums.RoleEnum;
import com.photoism.cms.common.exception.ObjectAlreadExistException;
import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.exception.SigninFailedException;
import com.photoism.cms.common.exception.UserNotFoundException;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.domain.etc.entity.CodeEntity;
import com.photoism.cms.domain.etc.repository.CodeRepository;
import com.photoism.cms.domain.user.dto.*;
import com.photoism.cms.domain.user.entity.RoleEntity;
import com.photoism.cms.domain.user.entity.UserEntity;
import com.photoism.cms.domain.user.repository.UserQueryRepository;
import com.photoism.cms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @Transactional
    public CommonIdResult add(UserReqDto reqDto) {
        if (userRepository.findByUserIdAndDel(reqDto.getUserId(), false).isPresent())
            throw new ObjectAlreadExistException("userId");

        UserEntity userEntity = reqDto.toEntity();
        userEntity.setPassword(passwordEncoder.encode(reqDto.getUserId()));

        List<RoleEntity> roleEntityList = new ArrayList<>();
        reqDto.getRoleList().forEach(roleEnum -> {
            if (roleEnum.equals(RoleEnum.ROLE_STORE))
                userEntity.setStoreUser(true);

            CodeEntity codeEntity = codeRepository.findByCode(roleEnum.name()).orElseThrow(() -> new ObjectNotFoundException("role"));
            RoleEntity roleEntity = RoleEntity.builder()
                    .user(userEntity)
                    .role(codeEntity.getCode())
                    .build();
            roleEntityList.add(roleEntity);
        });
        userEntity.setRoles(roleEntityList);

        return new CommonIdResult(userRepository.save(userEntity).getId());
    }

    @Transactional
    public CommonIdResult updateUser(Long id, UserReqDto reqDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.update(reqDto);
        return new CommonIdResult(userEntity.getId());
    }

    @Transactional
    public CommonIdResult deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setDel();
        return new CommonIdResult(id);
    }

    @Transactional(readOnly = true)
    public UserDetailResDto getUser(Long id) {
        UserEntity userEntity = userRepository.findByIdAndDel(id, false).orElseThrow(UserNotFoundException::new);
        return new UserDetailResDto(userEntity);
    }

    @Transactional
    public CommonIdResult changePassword(Long id, ChangePasswordReqDto reqDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String oldPassword = userEntity.getPassword() != null ? userEntity.getPassword() : userEntity.getTmpPassword();
        if (!passwordEncoder.matches(reqDto.getOldPassword(), oldPassword)) {
            throw new SigninFailedException();
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
    public UserListResDto getStaffUserList(String userId, String name, String department, String phone, String email, Pageable pageable) {
        Page<UserResDto> userResDtoPage = userQueryRepository.findStaffUserList(userId, name, department, phone, email, pageable);
        return new UserListResDto(userResDtoPage);
    }

    @Transactional(readOnly = true)
    public UserListResDto getStoreUserList(String userId, String name, String phone, String email, Pageable pageable) {
        Page<UserResDto> userResDtoPage = userQueryRepository.findStoreUserList(userId, name, phone, email, pageable);
        return new UserListResDto(userResDtoPage);
    }
}
