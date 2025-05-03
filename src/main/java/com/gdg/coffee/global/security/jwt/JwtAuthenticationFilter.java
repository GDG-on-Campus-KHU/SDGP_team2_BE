package com.gdg.coffee.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdg.coffee.domain.auth.exception.AuthErrorCode;
import com.gdg.coffee.domain.auth.exception.AuthException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // JWT 검증을 제외할 경로
    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/swagger-ui",
            "/v3/api-docs",
            "/h2",
            "/api/auth/register",
            "/api/auth/login","/login",
            "/api/auth/refresh",
            "/oauth2/authorization",    // 인가 요청 진입점
            "/login/oauth2/code"        // 콜백 처리 엔드포인트
    );


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();

        // 필터 예외 경로 체크
        if (EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 토큰 추출
        String token = resolveToken(httpServletRequest);
        log.info("BearerToken: {}", token);

        try {
            if (token != null) {
                // 토큰이 있을 때만 검증하고 인증 객체 설정
                if (jwtTokenProvider.isValidToken(token)) {
                    var authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Authentication set in SecurityContext.");
                } else {
                    throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
                }
            }
            // token == null이면 그냥 넘어감

        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.SERVER_ERROR);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value()); // HTTP 상태 코드 설정
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);

        String jsonResponse = objectMapper.writeValueAsString(errorResponse); // JSON 형식으로 변환
        response.getWriter().write(jsonResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("BearerToken: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
