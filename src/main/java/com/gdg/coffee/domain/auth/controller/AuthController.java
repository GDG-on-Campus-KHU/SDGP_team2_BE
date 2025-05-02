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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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

    @Operation(summary = "[구현완료] 구글 로그인 (프론트 연동)", description = """
    ## 구글 OAuth 인증 코드를 사용하여 로그인합니다.
    - 프론트엔드에서 받은 authorizationCode를 사용해 구글 로그인 절차를 수행합니다.
    - 구글 계정이 기존 회원 정보와 연동되어 있다면 로그인 처리됩니다.
    """)
    @PostMapping("/login/google")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> loginGoogle(@RequestBody GoogleOAuthRequestDto requestBody) {
        String authorizationCode = requestBody.getAuthorizationCode();
        MemberLoginResponseDto response = googleOAuthService.loginGoogle(authorizationCode);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response), HttpStatus.OK);
    }

    @Operation(summary = "[구현완료] 구글 콜백", description = """
    ## 구글 OAuth 로그인 콜백입니다.
    - 구글 로그인 승인 후 리디렉션되는 엔드포인트입니다.
    - Authorization Code를 통해 사용자 인증 및 로그인 처리를 수행합니다.
    """)
    @GetMapping("/google/callback")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> googleCallback(@RequestParam("code") String code) {
        MemberLoginResponseDto response = googleOAuthService.loginGoogle(code);
        return ResponseEntity.ok(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response));
    }
}
