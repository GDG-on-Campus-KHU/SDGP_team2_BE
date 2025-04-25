package com.gdg.coffee.global.util;

import com.gdg.coffee.global.security.CustomUserDetails;
import com.gdg.coffee.global.security.jwt.JwtErrorCode;
import com.gdg.coffee.global.security.exception.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    public static Long getCurrentMemberId() {

        // Spring Security가 현재 요청을 처리하는 스레드의 인증 정보를 담고 있는 컨텍스트
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new SecurityException(JwtErrorCode.AUTHENTICATION_NOT_FOUND);
        }

        // principal은 로그인한 사용자의 정보 객체
        //JWT 기반 인증에서는 일반적으로 CustomUserDetails 혹은 String(ID 값) 형태로 존재
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String memberId = ((CustomUserDetails) principal).getUsername();
            log.info("memberId: {}", memberId);
            return Long.valueOf(memberId);
        } else if (principal instanceof String) {
            log.info(((String) principal));
            return Long.valueOf((String) principal);
        } else {
            throw new SecurityException(JwtErrorCode.UNKNOWN_PRINCIPAL_TYPE);
        }
    }
}
