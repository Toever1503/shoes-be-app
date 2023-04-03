package com.photoism.cms.domain.auth;

import com.photoism.cms.common.exception.UserNotFoundException;
import com.photoism.cms.domain.user.entity.RoleEntity;
import com.photoism.cms.domain.user.entity.UserEntity;
import com.photoism.cms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(Long.valueOf(username)).orElseThrow(()->new UserNotFoundException("user"));
        userEntity.setAuthorities(getRoles(userEntity.getRoles().stream().toList()));
        return userEntity;
    }

    private List<SimpleGrantedAuthority> getRoles(List<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
