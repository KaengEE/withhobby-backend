package com.kaengee.withhobby.security.jwt;

import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;


    //토큰 생성
    @Override
    public String generateToken(UserPrinciple auth){

        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //key 암호화
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        //토큰객체생성
        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", authorities)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        //토큰에서 유저 정보부분을 가져옴
        Claims claims = extractClaims(request);

        if(claims == null) return null; //유저정보가 없으면 종료

        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrinciple.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if(username == null) return  null;

        //스프링 시큐리티 인증
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    //토큰의 유효성 체크 (유저정보와 날짜 체크)
    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if(claims==null) return false;

        if(claims.getExpiration().before(new Date())) return false;

        return true;
    }

    //토큰에서 유저정보 claim 부분만 가져옴
    @Override
    public Claims extractClaims(HttpServletRequest request){
        //리퀘스트 헤더에서 토큰만 가져옴
        String token = SecurityUtils.extractAuthTokenFromRequest (request);

        if(token == null) return null; //종료및 널값 리턴

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        //토큰의 유저 정보부분을 리턴
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
