// src/main/java/com/gdg/coffee/cafe/controller/CafeController.java
package com.gdg.coffee.cafe.controller;

import com.gdg.coffee.cafe.dto.CafeRequestDto;
import com.gdg.coffee.cafe.dto.CafeResponseDto;
import com.gdg.coffee.cafe.service.CafeService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.common.response.cafe.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafes")
public class CafeController {

    private final CafeService cafeService;

    /** 1. 카페 생성 (201 Created) */
    @PostMapping
    public ApiResponse<CafeResponseDto> createCafe(@RequestBody @Valid CafeRequestDto requestDto) {
        CafeResponseDto created = cafeService.createCafe(requestDto);
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
    public ApiResponse<Page<CafeResponseDto>> getAllCafes(Pageable pageable) {
        Page<CafeResponseDto> page = cafeService.getAllCafes(pageable);
        return ApiResponse.success(CafeSuccessCode.CAFE_GET_LIST_SUCCESS, page);
    }

    /** 4. 정보 수정 (200 OK) */
    @PutMapping("/{cafeId}")
    public ApiResponse<CafeResponseDto> updateCafe(
            @PathVariable Long cafeId,
            @RequestBody @Valid CafeRequestDto requestDto) {
        CafeResponseDto updated = cafeService.updateCafe(cafeId, requestDto);
        return ApiResponse.success(CafeSuccessCode.CAFE_UPDATE_SUCCESS, updated);
    }
}
