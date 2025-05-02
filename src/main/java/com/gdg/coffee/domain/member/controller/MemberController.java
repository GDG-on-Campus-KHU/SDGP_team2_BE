package com.gdg.coffee.domain.member.controller;

import com.gdg.coffee.domain.member.dto.MemberInfoResponseDto;
import com.gdg.coffee.domain.member.exception.MemberSuccessCode;
import com.gdg.coffee.domain.member.service.MemberService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "회원 정보 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원정보 가져오는 메서드
    @GetMapping("/info")
    @Operation(summary = "[구현완료] 회원 정보 조회", description = """
        ## 로그인한 사용자의 정보를 조회합니다.
        - JWT 토큰을 통해 인증된 사용자만 접근 가능합니다.
        - 응답에는 닉네임, 역할, 이메일 등의 회원 정보가 포함됩니다.
        """)
    public ResponseEntity<ApiResponse<MemberInfoResponseDto>> getMemberInfo() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        log.info("MemberId : {}", memberId);
        MemberInfoResponseDto memberInfo = memberService.getMemberInfo(memberId);
        log.info("Member Info: {}", memberInfo);
        return ResponseEntity.ok(ApiResponse.success(MemberSuccessCode.INFO_SUCCESS, memberInfo));
    }
}
