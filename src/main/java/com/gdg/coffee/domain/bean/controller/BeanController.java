package com.gdg.coffee.domain.bean.controller;

import com.gdg.coffee.domain.bean.dto.BeanRequestDto;
import com.gdg.coffee.domain.bean.dto.BeanResponseDto;
import com.gdg.coffee.domain.bean.exception.BeanSuccessCode;
import com.gdg.coffee.domain.bean.service.BeanService;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.cafe.exception.CafeErrorCode;
import com.gdg.coffee.domain.cafe.exception.CafeException;
import com.gdg.coffee.domain.cafe.repository.CafeRepository;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Bean", description = "원두 관련 API")
@RestController
@RequestMapping("/api/beans")
@RequiredArgsConstructor
@Validated
public class BeanController {

    private final BeanService beanService;
    private final CafeRepository cafeRepository;

    /** 1. 원두 등록 (201 CREATED) */
    @PostMapping
    @Operation(summary = "[구현완료] 원두 등록", description = """
    ## 카페 관리자가 새로운 원두를 등록합니다.
    - 요청 본문에는 원두 이름, 원산지, 설명이 포함됩니다.
    - 로그인된 카페 사용자만 등록할 수 있습니다.
    - 등록된 원두 정보가 응답으로 반환됩니다.
    """)
    public ApiResponse<BeanResponseDto> createBean(@Valid @RequestBody BeanRequestDto requestDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        BeanResponseDto created = beanService.createBean(memberId, requestDto);
        return ApiResponse.success(BeanSuccessCode.BEAN_CREATE_SUCCESS, created);
    }

    /** 2. 카페별 원두 목록 조회 (200 OK) */
    @GetMapping
    @Operation(summary = "[구현완료] 카페별 원두 목록 조회", description = """
    ## 로그인한 카페 사용자의 원두 목록을 조회합니다.
    - 회원의 memberId를 통해 해당 카페의 원두를 가져옵니다.
    - 응답으로 원두 목록을 반환합니다.
    """)
    public ApiResponse<List<BeanResponseDto>> getBeansByCafe() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<BeanResponseDto> list = beanService.getBeansByCafe(memberId);
        return ApiResponse.success(BeanSuccessCode.BEAN_LIST_SUCCESS, list);
    }

    /** 3. 원두 정보 수정 (200 OK) */
    @PutMapping("/{beanId}")
    @Operation(summary = "[구현완료] 원두 정보 수정", description = """
    ## 등록된 원두의 정보를 수정합니다.
    - 수정 가능한 필드는 이름, 원산지, 설명입니다.
    - 요청자는 해당 원두를 등록한 카페의 사용자여야 합니다.
    """)
    public ApiResponse<BeanResponseDto> updateBean(
            @PathVariable Long beanId,
            @RequestBody BeanRequestDto requestDto) {

        Long memberId = SecurityUtil.getCurrentMemberId();
        BeanResponseDto updated = beanService.updateBean(beanId, memberId, requestDto);
        return ApiResponse.success(BeanSuccessCode.BEAN_UPDATE_SUCCESS, updated);
    }

    /** 4. 원두 삭제 (200 OK) */
    @DeleteMapping("/{beanId}")
    @Operation(summary = "[구현완료] 원두 삭제", description = """
    ## 원두 정보를 삭제합니다.
    - 삭제는 해당 원두를 등록한 카페의 사용자만 가능합니다.
    - 삭제 성공 시 응답 본문 없이 성공 코드만 반환됩니다.
    """)
    public ApiResponse<Void> deleteBean(@PathVariable Long beanId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        beanService.deleteBean(beanId, memberId);
        return ApiResponse.success(BeanSuccessCode.BEAN_DELETE_SUCCESS);
    }
}
