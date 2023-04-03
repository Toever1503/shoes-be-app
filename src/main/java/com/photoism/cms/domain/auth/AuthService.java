package com.photoism.cms.domain.auth;

import com.photoism.cms.common.exception.SigninFailedException;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.auth.dto.SignInReqDto;
import com.photoism.cms.domain.auth.dto.SignInResDto;
import com.photoism.cms.domain.auth.entity.AuthenticationEntity;
import com.photoism.cms.domain.auth.repository.AuthenticationRepository;
import com.photoism.cms.domain.user.entity.RoleEntity;
import com.photoism.cms.domain.user.entity.UserEntity;
import com.photoism.cms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationRepository authenticationRepository;

    @Transactional
    public SignInResDto signIn(SignInReqDto reqDto) {
        UserEntity userEntity = userRepository.findByUserIdAndDel(reqDto.getUserId(), false).orElseThrow(SigninFailedException::new);

        // check password
        if (!passwordEncoder.matches(reqDto.getPassword(), userEntity.getPassword()))
            throw new SigninFailedException();

        // create token
        StringBuilder expire = new StringBuilder();
        String accessToken = jwtTokenProvider.createToken(String.valueOf(userEntity.getId()), userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList()), expire);
        StringBuilder refreshExpire = new StringBuilder();
        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(userEntity.getId()), userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList()), refreshExpire);

        // save authentication information
        authenticationRepository.save(AuthenticationEntity.builder()
                .id(UUID.randomUUID().toString())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userEntity.getId())
                .logout(false)
                .build());

        return SignInResDto.builder()
                .userId(userEntity.getUserId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpireIn(expire.toString())
                .refreshExpireIn(refreshExpire.toString())
                .roles(userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList()))
                .build();
    }
}
