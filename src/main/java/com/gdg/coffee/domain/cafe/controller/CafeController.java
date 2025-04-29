package com.gdg.coffee.domain.cafe.controller;

import com.gdg.coffee.domain.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.cafe.dto.CafeResponseDto;
import com.gdg.coffee.domain.cafe.exception.CafeSuccessCode;
import com.gdg.coffee.domain.cafe.service.CafeService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes")
public class CafeController {

    private final CafeService cafeService;

    /** 1. 카페 생성 (201 Created) */
    @PostMapping
    public ApiResponse<CafeResponseDto> createCafe(@RequestBody @Valid CafeRequestDto requestDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        CafeResponseDto created = cafeService.createCafe(memberId, requestDto);
        return ApiResponse.success(CafeSuccessCode.CAFE_CREATE_SUCCESS, created);
    }

    /** 2. 단건 조회 (200 OK) */
    @GetMapping("/{cafeId}")
    public ApiResponse<CafeResponseDto> getCafe(@PathVariable Long cafeId) {
        CafeResponseDto dto = cafeService.getCafe(cafeId);
        return ApiResponse.success(CafeSuccessCode.CAFE_GET_SUCCESS, dto);
    }

    /** 3. 전체 카페 목록 조회 (200 OK)*/
    @GetMapping
    public ApiResponse<Page<CafeResponseDto>> getAllCafes(
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        Page<CafeResponseDto> page = cafeService.getAllCafes(pageable);
        return ApiResponse.success(CafeSuccessCode.CAFE_GET_LIST_SUCCESS, page);
    }

    /** 4. 정보 수정 (200 OK) */
    @PutMapping("/{cafeId}")
    public ApiResponse<CafeResponseDto> updateCafe(
            @PathVariable Long cafeId,
            @RequestBody CafeRequestDto requestDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        CafeResponseDto updated = cafeService.updateCafe(cafeId, memberId, requestDto);
        return ApiResponse.success(CafeSuccessCode.CAFE_UPDATE_SUCCESS, updated);
    }
}
