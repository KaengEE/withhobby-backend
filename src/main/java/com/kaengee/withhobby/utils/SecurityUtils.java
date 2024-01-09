package com.kaengee.withhobby.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUtils {

    public static final String ROLE_PREFIX = "ROLE_";

    //시큐리티에서 "ROLE_ADMIN","ROLE_USER"로 설정하기 때문에 앞에 "ROLE"을 붙여줌
    public static SimpleGrantedAuthority convertToAuthority(String role) {
        String formattedRole = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role;
        return new SimpleGrantedAuthority(formattedRole);
    }
}