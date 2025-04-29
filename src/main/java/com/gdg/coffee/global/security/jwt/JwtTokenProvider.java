package com.gdg.coffee.global.security.jwt;

import com.gdg.coffee.domain.auth.exception.AuthErrorCode;
import com.gdg.coffee.domain.auth.exception.AuthException;
import com.gdg.coffee.domain.member.domain.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

   // private final long EXPIRATION_TIME = 86400000;
    @Value("${jwt.secret.key}")
    private String SECRETKEY;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Getter
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String createAccessToken(Long memberId, MemberRole memberRole){
        return createToken(memberId, memberRole,accessTokenExpiration);
    }

    public String createRefreshToken(Long memberId, MemberRole memberRole){
        return createToken(memberId, memberRole,refreshTokenExpiration);
    }

    // JWT를 발급하는 메서드
    public String createToken(Long memberId, MemberRole memberRole, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claim("role", "ROLE_"+memberRole.toString()) //사용자 권한을 클레임에 추가
                .setSubject(memberId.toString()) //토큰의 subject에 memberId를 저장
                .setIssuedAt(now)
                .setExpiration(expiryDate)
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
        String role = (String) getClaims(token).get("role");
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }
        return MemberRole.valueOf(role);
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

    public boolean validateRefreshToken(String refreshToken){
        return isValidToken(refreshToken);
    }
    public String renewAccessToken(String refreshToken){
        if (!validateRefreshToken(refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
        Long memberId = getMemberIdFromToken(refreshToken);
        MemberRole memberRole = getMemberRole(refreshToken);
        log.info("MemberRole" + memberRole);
        return createAccessToken(memberId, memberRole);
    }
}
