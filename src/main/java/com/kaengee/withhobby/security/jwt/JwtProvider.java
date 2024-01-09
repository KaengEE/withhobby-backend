package com.kaengee.withhobby.security.jwt;

import com.kaengee.withhobby.security.UserPrinciple;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String generateToken(UserPrinciple auth);

    Authentication getAuthentication(HttpServletRequest request);

    //토큰의 유효성 체크 (유저정보와 날짜 체크)
    boolean isTokenValid(HttpServletRequest request);

    //유저정보
    //토큰에서 유저정보 claim 부분만 가져옴
    Claims extractClaims(HttpServletRequest request);
}
