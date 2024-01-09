package com.kaengee.withhobby.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = jwtProvider.getAuthentication(request);
        //토큰이 있을경우 바로 인증됨
        if(authentication != null && jwtProvider.isTokenValid(request)){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 원래 요청으로 이동
        filterChain.doFilter(request, response);
    }
}
