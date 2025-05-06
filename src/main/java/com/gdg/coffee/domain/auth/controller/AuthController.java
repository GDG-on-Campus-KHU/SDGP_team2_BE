package com.gdg.coffee.domain.auth.controller;

import com.gdg.coffee.domain.auth.dto.request.GoogleOAuthRequestDto;
import com.gdg.coffee.domain.auth.dto.request.MemberLoginRequestDto;
import com.gdg.coffee.domain.auth.dto.request.MemberRegisterRequestDto;
import com.gdg.coffee.domain.auth.dto.request.RefreshTokenRequestDto;
import com.gdg.coffee.domain.auth.dto.response.MemberLoginResponseDto;
import com.gdg.coffee.domain.auth.dto.response.MemberRegisterResponseDto;
import com.gdg.coffee.domain.auth.exception.AuthSuccessCode;
import com.gdg.coffee.domain.auth.service.AuthService;
import com.gdg.coffee.domain.auth.service.GoogleOAuthService;
import com.gdg.coffee.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final GoogleOAuthService googleOAuthService;

    @Operation(summary = "[구현완료] 일반 회원가입", description = """
    ## 일반 회원 가입을 수행합니다.
    - 이메일, 비밀번호 등의 정보를 입력하여 회원가입합니다.
    - 회원가입 완료 시 사용자 정보와 함께 응답됩니다.
    """)
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<MemberRegisterResponseDto>> register(@RequestBody MemberRegisterRequestDto request) {
        MemberRegisterResponseDto response = authService.register(request);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.REGISTER_SUCCESS, response), HttpStatus.CREATED);
    }

    @Operation(summary = "[구현완료] 일반 로그인", description = """
    ## 일반 사용자 로그인을 수행합니다.
    - 이메일과 비밀번호를 기반으로 로그인합니다.
    - 성공 시 액세스 토큰과 리프레시 토큰을 응답합니다.
    """)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> login(@RequestBody MemberLoginRequestDto request) {
        MemberLoginResponseDto response = authService.login(request);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response), HttpStatus.OK);
    }

    @Operation(summary = "[구현완료] 액세스 토큰 재발급", description = """
    ## 리프레시 토큰을 사용하여 액세스 토큰을 재발급합니다.
    - 만료된 액세스 토큰을 갱신하기 위해 사용합니다.
    - 유효한 리프레시 토큰이 필요합니다.
    """)
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshAccessToken(@RequestBody RefreshTokenRequestDto request) {
        String newAccessToken = authService.refreshAccessToken(request.getRefreshToken());
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.TOKEN_REFRESH_SUCCESS, newAccessToken), HttpStatus.OK);
    }

    @Operation(
            summary     = "[구현완료] 구글 로그인 시작",
            description = """
        ## 구글 OAuth 로그인 화면으로 리다이렉트합니다.
        * 클라이언트가 이 엔드포인트를 호출하면 302 응답으로 구글 승인 페이지로 이동됩니다.
        """
    )
    @GetMapping("/login/google")
    public void loginGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect(googleOAuthService.buildAuthorizationUri());
    }

    @Operation(
            summary     = "[구현완료] 구글 콜백",
            description = """
        ## 구글 OAuth 승인 후 호출되는 콜백 엔드포인트입니다.
        * `code` 파라미터를 받아 구글 토큰 교환 및 사용자 정보 조회를 수행합니다.
        * 내부적으로 자체 JWT(access/refresh)를 발급하여 JSON으로 반환합니다.
        """
    )
    @GetMapping("/google/callback")
    public void googleCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        MemberLoginResponseDto dto = googleOAuthService.loginGoogle(code);

        // 프론트엔드 리다이렉트 URL (개발 시에는 localhost로)
        String redirectUrl = "http://localhost:8080/oauth/google/callback" +
                "?accessToken=" + dto.getAccessToken() +
                "&refreshToken=" + dto.getRefreshToken() +
                "&username=" + URLEncoder.encode(dto.getUsername(), StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }
}
