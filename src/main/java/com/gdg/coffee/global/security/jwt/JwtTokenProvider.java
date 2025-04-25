package com.gdg.coffee.global.security.jwt;

import com.gdg.coffee.domain.member.domain.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    private final long EXPIRATION_TIME = 86400000;
    @Value("${jwt.secret.key}")
    private String SECRETKEY;

    // JWT를 발급하는 메서드
    public String createToken(Long memberId, MemberRole memberRole) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .claim("role", "ROLE_"+memberRole.toString()) //사용자 권한을 클레임에 추가
                .setSubject(memberId.toString()) //토큰의 subject에 memberId를 저장
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,SECRETKEY.getBytes())
                .compact(); //JWT 문자열로 직렬화
    }

    // JWT로부터 사용자 인증 정보를 추출하는 메서드
    public Authentication getAuthentication(String token){
        Long memberId = getMemberIdFromToken(token); // 토큰에서 사용자 ID 추출
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberId.toString()); // 사용자 ID로 UserDetails 로드
        System.out.println(userDetails);
        System.out.println(userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // 시큐리티 인증 객체 생성
    }

    // JWT의 sub 필드(Subject)에 저장된 사용자 ID를 추출
    public Long getMemberIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }

    // JWT의 Claim 정보를 파싱해 반환
    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(SECRETKEY.getBytes()).build().parseClaimsJws(token).getBody();
    }

    public MemberRole getMemberRole(String token) {
        return MemberRole.valueOf(getClaims(token).get("role", String.class).replace("ROLE_", ""));
    }

    // 필터에서 사용하는 토큰 유효성 검사 메서드
    public boolean isValidToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRETKEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }

    }
}
