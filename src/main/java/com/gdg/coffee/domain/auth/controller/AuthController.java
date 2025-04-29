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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<MemberRegisterResponseDto>> register(@RequestBody MemberRegisterRequestDto request) {
        MemberRegisterResponseDto response = authService.register(request);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.REGISTER_SUCCESS, response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> login(@RequestBody MemberLoginRequestDto request) {
        MemberLoginResponseDto response = authService.login(request);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshAccessToken(@RequestBody RefreshTokenRequestDto request) {
        String newAccessToken = authService.refreshAccessToken(request.getRefreshToken());
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.TOKEN_REFRESH_SUCCESS, newAccessToken), HttpStatus.OK);
    }

    @PostMapping("/login/google")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> loginGoogle(@RequestBody GoogleOAuthRequestDto requestBody) {
        String authorizationCode = requestBody.getAuthorizationCode();
        MemberLoginResponseDto response = googleOAuthService.loginGoogle(authorizationCode);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response), HttpStatus.OK);
    }

    @GetMapping("/google/callback")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> googleCallback(@RequestParam("code") String code) {
        MemberLoginResponseDto response = googleOAuthService.loginGoogle(code);
        return ResponseEntity.ok(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response));
    }
}
