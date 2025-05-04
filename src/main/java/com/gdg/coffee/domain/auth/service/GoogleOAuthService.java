package com.gdg.coffee.domain.auth.service;

import com.gdg.coffee.domain.auth.dto.response.GoogleTokenResponseDto;
import com.gdg.coffee.domain.auth.dto.response.GoogleUserInfoDto;
import com.gdg.coffee.domain.auth.dto.response.MemberLoginResponseDto;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import com.gdg.coffee.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.client-secret}")
    private String clientSecret;

    @Value("${oauth.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.google.token-uri}")
    private String tokenUri;

    @Value("${oauth.google.user-info-uri}")
    private String userInfoUri;

    /**
     * 1) 구글 OAuth 승인 URL 생성
     */
    public String buildAuthorizationUri() {
        return UriComponentsBuilder
                .fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email profile")
                .build()
                .toUriString();
    }


    public MemberLoginResponseDto loginGoogle(String authorizationCode) {
        // 1. authorizationCode로 accessToken 얻기
        String accessToken = getAccessToken(authorizationCode);

        // 2. accessToken으로 사용자 정보 얻기
        GoogleUserInfoDto googleUserInfo = getUserInfo(accessToken);

        // 3. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(googleUserInfo.getEmail())
                .orElseGet(() -> {
                    // 없으면 새로 생성
                    Member newMember = authService.createMemberByGoogle(googleUserInfo);
                    return memberRepository.save(newMember);
                });

        // 4. 서버 access/refresh 토큰 발급
        String serverAccessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId(), member.getRole());

        // 5. 클라이언트에게 반환
        return new MemberLoginResponseDto(
                serverAccessToken,
                refreshToken,
                member.getId(),
                member.getUsername(),
                member.getRole(),
                member.getProfileImage()
        );
    }

    public String getAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<GoogleTokenResponseDto> response = restTemplate.postForEntity(
                tokenUri,
                request,
                GoogleTokenResponseDto.class
        );

        GoogleTokenResponseDto tokenResponse = response.getBody();
        if (tokenResponse == null) {
            throw new RuntimeException("구글 토큰 응답이 null입니다.");
        }

        return tokenResponse.getAccessToken();
    }


    public GoogleUserInfoDto getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseBody = response.getBody();
        String name = (String) responseBody.get("name");
        String email = (String) responseBody.get("email");

        return new GoogleUserInfoDto(name, email);
    }
}
