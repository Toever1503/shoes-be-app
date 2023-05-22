package com.photoism.cms.domain.auth;

import com.photoism.cms.common.exception.UserNotFoundException;
import com.photoism.cms.domain.auth.entity.PrivilegeEntity;
import com.photoism.cms.domain.auth.entity.RoleEntity;
import com.photoism.cms.domain.auth.repository.PrivilegeRepository;
import com.photoism.cms.domain.user.entity.UserEntity;
import com.photoism.cms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(Long.valueOf(username)).orElseThrow(()->new UserNotFoundException("user"));
        userEntity.setAuthorities(
                Stream.of(
                        getRoles(userEntity.getRoles().stream().toList()),
                        getPrivileges(userEntity.getRoles().stream().toList())
                ).flatMap(Collection::stream).toList()
        );
        return userEntity;
    }

    private List<SimpleGrantedAuthority> getRoles(List<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getRoleCd)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private List<SimpleGrantedAuthority> getPrivileges(List<RoleEntity> roles) {
        return roles.stream()
                .flatMap(roleEntity -> {
                    List<PrivilegeEntity> privileges = privilegeRepository.findByRoleCd(roleEntity.getRoleCd());
                    return privileges.stream();
                }).map(privilegeEntity -> new SimpleGrantedAuthority((privilegeEntity.getPrivilegeCd())))
                .collect(Collectors.toList());
    }
}
