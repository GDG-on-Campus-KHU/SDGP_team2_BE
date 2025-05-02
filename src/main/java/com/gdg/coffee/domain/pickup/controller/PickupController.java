package com.gdg.coffee.domain.pickup.controller;

import com.gdg.coffee.domain.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.pickup.dto.*;
import com.gdg.coffee.domain.pickup.exception.PickupSuccessCode;
import com.gdg.coffee.domain.pickup.service.PickupService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.security.CustomUserDetails;
import com.gdg.coffee.global.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pickups")
public class PickupController {

    private final PickupService pickupService;

    // 수거 요청 생성 - 사용자
    @PostMapping("/{ground_id}")
    public ApiResponse<PickupResponseDto> createPickup(@RequestBody @Valid PickupRequestDto requestDto, @PathVariable Long ground_id) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        PickupResponseDto pickup = pickupService.createPickup(memberId, ground_id, requestDto);
        return ApiResponse.success(PickupSuccessCode.PICKUP_SUCCESS_CODE, pickup);
    }

    // 수거 요청 목록 조회 - 카페
    @GetMapping("/cafes/{cafeId}")
    public ApiResponse<List<PickupCafeSummaryDto>> getCafePickupsByStatus(
            @PathVariable Long cafeId,
            @RequestParam(required = false) PickupStatus status // status가 null일 수도 있으므로 optional 처리
    ) {
        List<PickupCafeSummaryDto> response;

        if (status != null) {
            response = pickupService.getCafePickupListByStatus(cafeId, status);
        } else {
            response = pickupService.getCafePickupList(cafeId);
        }

        return ApiResponse.success(PickupSuccessCode.PICKUP_GET_LIST_SUCCESS, response);
    }

    // 수거 요청 목록 조회 - 사용자
    @GetMapping("/users/{userId}")
    public ApiResponse<List<PickupUserSummaryDto>> getUserPickupsByStatus(
            @PathVariable Long memberId,
            @RequestParam(required = false) PickupStatus status
    ) {
        List<PickupUserSummaryDto> response;

        if (status != null) {
            response = pickupService.getUserPickupListByStatus(memberId, status);
        } else {
            response = pickupService.getUserPickupList(memberId);
        }

        return ApiResponse.success(PickupSuccessCode.PICKUP_GET_LIST_SUCCESS, response);
    }

    // 수거 요청 상세 보기
    @GetMapping("/{pickupId}")
    public ApiResponse<PickupResponseDto> getPickupDetail(@PathVariable Long pickupId) {
        PickupResponseDto pickupDetail = pickupService.getPickupDetail(pickupId);
        return ApiResponse.success(PickupSuccessCode.PICKUP_SUCCESS_CODE, pickupDetail);
    }

    // 수거 요청 상태 변경
    @PutMapping("/{pickupId}/status")
    public ApiResponse<Void> updatePickupStatus(
            @PathVariable Long pickupId,
            @RequestBody PickupStatusUpdateRequestDto requestDto
    ) {
        pickupService.updatePickupStatus(pickupId, requestDto.getStatus());
        return ApiResponse.success(PickupSuccessCode.PICKUP_STATUS_UPDATE_SUCCESS);
    }

    // 수거 요청 삭제
    @DeleteMapping("/{pickupId}")
    public ApiResponse<Void> deletePickup(
            @PathVariable Long pickupId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long memberId = userDetails.getMemberId();
        pickupService.deletePickup(pickupId, memberId);
        return ApiResponse.success(PickupSuccessCode.PICKUP_DELETE_SUCCESS);
    }
}
