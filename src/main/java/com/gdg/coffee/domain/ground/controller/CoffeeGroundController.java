package com.gdg.coffee.domain.ground.controller;

import com.gdg.coffee.domain.ground.dto.*;
import com.gdg.coffee.domain.ground.exception.CoffeeGroundSuccessCode;
import com.gdg.coffee.domain.ground.service.CoffeeGroundService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/coffee_grounds")
@RequiredArgsConstructor
public class CoffeeGroundController {

    private final CoffeeGroundService groundService;

    /* 1. 등록 */
    @PostMapping
    public ApiResponse<CoffeeGroundResponseDto> createGround(@RequestBody @Valid CoffeeGroundRequestDto dto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        CoffeeGroundResponseDto created = groundService.createGround(memberId, dto);
        return ApiResponse.success(CoffeeGroundSuccessCode.GROUND_CREATE_SUCCESS, created);
    }

    /* 2. 단건 조회 */
    @GetMapping("/{groundId}")
    public ApiResponse<CoffeeGroundResponseDto> getGround(@PathVariable Long groundId) {
        CoffeeGroundResponseDto response = groundService.getGround(groundId);
        return ApiResponse.success(CoffeeGroundSuccessCode.GROUND_GET_SUCCESS, response);
    }

    /* 3. 목록 (카페별) */
    @GetMapping("/cafe/{cafeId}")
    public ApiResponse<Page<CoffeeGroundResponseDto>>
    getGroundsOfCafe(@PathVariable Long cafeId, @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        return null;
    }

    /* 4. 삭제 */
    @DeleteMapping("/{groundId}")
    public ApiResponse<Void> deleteGround(@PathVariable Long groundId) {
        return null;
    }
}
