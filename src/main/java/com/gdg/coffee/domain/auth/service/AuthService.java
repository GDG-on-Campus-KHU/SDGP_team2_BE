package com.gdg.coffee.domain.auth.service;

import com.gdg.coffee.domain.auth.dto.request.MemberLoginRequestDto;
import com.gdg.coffee.domain.auth.dto.request.MemberRegisterRequestDto;
import com.gdg.coffee.domain.auth.dto.response.GoogleUserInfoDto;
import com.gdg.coffee.domain.auth.dto.response.MemberLoginResponseDto;
import com.gdg.coffee.domain.auth.dto.response.MemberRegisterResponseDto;
import com.gdg.coffee.domain.auth.exception.AuthErrorCode;
import com.gdg.coffee.domain.auth.exception.AuthException;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.member.domain.MemberRole;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import com.gdg.coffee.global.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public MemberRegisterResponseDto register(MemberRegisterRequestDto request) {
        if (existsByEmail(request.getEmail())) {
            throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (existsByUsername(request.getUsername())) {
            throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
        }
        Member member = Member.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .profileImage(request.getProfileImage())
                .build();
        Member savedMember = memberRepository.save(member);
        return new MemberRegisterResponseDto(savedMember.getId(),savedMember.getCreatedDate());
    }

    public MemberLoginResponseDto login(MemberLoginRequestDto request) {
        Optional<Member> member = memberRepository.findByUsername(request.getUsername());

        if (member.isPresent() && member.get().getPassword().equals(request.getPassword())) {
            String accessToken = jwtTokenProvider.createAccessToken(member.get().getId(),member.get().getRole());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.get().getId(),member.get().getRole());

            refreshTokenService.saveRefreshToken(member.get().getId(),refreshToken,jwtTokenProvider.getRefreshTokenExpiration());
            return new MemberLoginResponseDto(accessToken,refreshToken,member.get().getId(),member.get().getUsername(),member.get().getRole(),member.get().getProfileImage());
            }
        else {
            throw new AuthException(AuthErrorCode.MEMBER_NOT_FOUND);

        }
    }
    public String refreshAccessToken(String refreshToken){
        // 1. refreshToken을 파싱해서 memberId 추출
        Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);

        // 2. memberId + refreshToken을 함께 검증
        if (!refreshTokenService.isRefreshTokenValid(memberId, refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 3. accessToken 새로 발급
        return jwtTokenProvider.renewAccessToken(refreshToken);
    }

    public Member createMemberByGoogle(GoogleUserInfoDto googleUserInfo) {

        return Member.builder()
                .username(googleUserInfo.getName())
                .email(googleUserInfo.getEmail())
                .password("SOCIAL_LOGIN_USER")
                .role(MemberRole.USER)
                .profileImage(null)
                .build();
    }
}
