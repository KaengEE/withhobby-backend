package com.kaengee.withhobby.security;

import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.service.UserService;
import com.kaengee.withhobby.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //DB에서 유저 검색
        User user = userService.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException(username));

        //user의 role을 스프링시큐리티의 GrantedAuthority로 바꿔준다. 여러개의 role을 가질수 있으므로 Set
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));

        return UserPrinciple.builder()
                .user(user)
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }

}
