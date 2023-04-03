package com.photoism.cms.domain.user;

import com.photoism.cms.common.enums.Role;
import com.photoism.cms.common.exception.ObjectAlreadExistException;
import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.domain.etc.entity.CodeEntity;
import com.photoism.cms.domain.etc.repository.CodeRepository;
import com.photoism.cms.domain.user.dto.AddAdminReqDto;
import com.photoism.cms.domain.user.entity.RoleEntity;
import com.photoism.cms.domain.user.entity.UserEntity;
import com.photoism.cms.domain.user.repository.RoleRepository;
import com.photoism.cms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CodeRepository codeRepository;

    @Transactional
    public Long add(Role role, AddAdminReqDto reqDto) {
        if (userRepository.findByUserIdAndDel(reqDto.getUserId(), false).isPresent())
            throw new ObjectAlreadExistException("userId");

        CodeEntity codeEntity = codeRepository.findByCode(role.name()).orElseThrow(() -> new ObjectNotFoundException("role"));

        UserEntity userEntity = reqDto.toEntity();
        userEntity.setPassword(passwordEncoder.encode(reqDto.getPassword()));
        userRepository.save(userEntity);

        RoleEntity roleEntity = RoleEntity.builder()
                .user(userEntity)
                .role(codeEntity.getCode())
                .build();
        roleRepository.save(roleEntity);

        return userEntity.getId();
    }
}
