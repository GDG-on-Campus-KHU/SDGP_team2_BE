package com.gdg.coffee.domain.auth.dto.request;

import com.gdg.coffee.domain.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegisterRequestDto {
    private String username;
    private String password;
    private String email;
    private MemberRole role;
    private String profileImage;
}
