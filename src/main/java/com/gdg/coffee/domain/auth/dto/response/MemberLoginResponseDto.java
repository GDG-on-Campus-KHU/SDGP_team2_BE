package com.gdg.coffee.domain.auth.dto.response;

import com.gdg.coffee.domain.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberLoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String username;
    private MemberRole role;
    private String profileImage;
}
