package com.photoism.cms.domain.auth;

import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.exception.SigninFailedException;
import com.photoism.cms.common.exception.UserNotFoundException;
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

        if (userEntity.getPassword() != null) {
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
                    .setPassword(true)
                    .build();
        } else {
            // check tmpPassword
            if (!passwordEncoder.matches(reqDto.getPassword(), userEntity.getTmpPassword()))
                throw new SigninFailedException();

            return SignInResDto.builder()
                    .userId(userEntity.getUserId())
                    .roles(userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList()))
                    .setPassword(false)
                    .build();
        }
    }

    @Transactional
    public void logout(Long id) {
        AuthenticationEntity authenticationEntity = authenticationRepository.findFirstByUserIdAndLogoutOrderByCreateDateDesc(id, false).orElseThrow(ObjectNotFoundException::new);
        authenticationEntity.setLogout();
    }

    @Transactional
    public SignInResDto refreshToken(Long id, String accessToken, String refreshToken){
        if (jwtTokenProvider.validateToken(refreshToken)) {
            if (jwtTokenProvider.getUserPk(refreshToken).equals(Long.toString(id))) {
                AuthenticationEntity authenticationEntity = authenticationRepository.findByRefreshToken(refreshToken).orElseThrow(IllegalArgumentException::new);
                UserEntity userEntity = userRepository.findById(authenticationEntity.getUserId()).orElseThrow(UserNotFoundException::new);
                if (userEntity.getId().equals(id) && authenticationEntity.getAccessToken().equals(accessToken)){
                    StringBuilder expire = new StringBuilder();
                    String newAccessToken = jwtTokenProvider.createToken(String.valueOf(userEntity.getId()), userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList()), expire);
                    StringBuilder refreshExpire = new StringBuilder();
                    String newRefreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(userEntity.getId()), userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList()), refreshExpire);

                    // save authentication information
                    authenticationRepository.save(AuthenticationEntity.builder()
                            .id(UUID.randomUUID().toString())
                            .accessToken(newAccessToken)
                            .refreshToken(newRefreshToken)
                            .userId(userEntity.getId())
                            .logout(false)
                            .build());

                    return SignInResDto.builder()
                            .userId(userEntity.getUserId())
                            .accessToken(newAccessToken)
                            .refreshToken(newRefreshToken)
                            .accessExpireIn(expire.toString())
                            .refreshExpireIn(refreshExpire.toString())
                            .roles(userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList()))
                            .build();
                }
            }
        }
        throw new IllegalArgumentException();
    }
}
