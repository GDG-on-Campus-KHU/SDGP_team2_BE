package com.gdg.coffee.domain.auth.controller;

import com.gdg.coffee.domain.auth.dto.request.MemberLoginRequestDto;
import com.gdg.coffee.domain.auth.dto.request.MemberRegisterRequestDto;
import com.gdg.coffee.domain.auth.dto.response.MemberLoginResponseDto;
import com.gdg.coffee.domain.auth.dto.response.MemberRegisterResponseDto;
import com.gdg.coffee.domain.auth.exception.AuthSuccessCode;
import com.gdg.coffee.domain.auth.service.AuthService;
import com.gdg.coffee.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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

}
