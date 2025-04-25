package com.gdg.coffee.domain.member.dto;

import com.gdg.coffee.domain.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponseDto {
    private Long id;
    private String username;
    private String email;
    private MemberRole role;
    private String profileImage;
}
