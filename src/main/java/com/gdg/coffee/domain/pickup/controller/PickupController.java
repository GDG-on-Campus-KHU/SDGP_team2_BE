package com.gdg.coffee.domain.pickup.controller;

import com.gdg.coffee.domain.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.pickup.dto.*;
import com.gdg.coffee.domain.pickup.exception.PickupSuccessCode;
import com.gdg.coffee.domain.pickup.service.PickupService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.security.CustomUserDetails;
import com.gdg.coffee.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pickup", description = "커피 찌꺼기 수거 요청 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PickupController {

    private final PickupService pickupService;

    /** 수거 요청 생성 - 사용자 */
    @PostMapping("/pickups/{ground_id}")
    @Operation(summary = "수거 요청 생성", description = """
        ## 커피 찌꺼기에 대해 수거 요청을 생성합니다.
        * 인증된 사용자만 요청할 수 있습니다.
        * 등록 시 상태는 WAITING으로 시작합니다.
        """)
    public ApiResponse<PickupResponseDto> createPickup(@RequestBody @Valid PickupRequestDto requestDto, @PathVariable Long ground_id) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        PickupResponseDto pickup = pickupService.createPickup(memberId, ground_id, requestDto);
        return ApiResponse.success(PickupSuccessCode.PICKUP_CREATE_SUCCESS, pickup);
    }

    /** 수거 요청 목록 조회 - 카페 */
    @GetMapping("/cafe/pickups")
    @Operation(summary = "카페 수거 요청 목록 조회", description = """
        ## 현재 로그인된 카페의 수거 요청 목록을 조회합니다.
        * 상태(PickupStatus)에 따라 필터링할 수 있습니다.
        """)
    public ApiResponse<List<PickupCafeSummaryDto>> getCafePickupsByStatus(
            @RequestParam(required = false) PickupStatus status
    ) {
        Long cafeId = SecurityUtil.getCurrentMemberId();
        List<PickupCafeSummaryDto> response = (status != null)
                ? pickupService.getCafePickupListByStatus(cafeId, status)
                : pickupService.getCafePickupList(cafeId);

        return ApiResponse.success(PickupSuccessCode.PICKUP_GET_LIST_SUCCESS, response);
    }

    /** 수거 요청 목록 조회 - 사용자 */
    @GetMapping("/mypage/pickups")
    @Operation(summary = "사용자 수거 요청 목록 조회", description = """
        ## 현재 로그인된 사용자의 수거 요청 목록을 조회합니다.
        * 상태(PickupStatus)에 따라 필터링할 수 있습니다.
        """)
    public ApiResponse<List<PickupUserSummaryDto>> getUserPickupsByStatus(
            @RequestParam(required = false) PickupStatus status
    ) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<PickupUserSummaryDto> response = (status != null)
                ? pickupService.getUserPickupListByStatus(memberId, status)
                : pickupService.getUserPickupList(memberId);

        return ApiResponse.success(PickupSuccessCode.PICKUP_GET_LIST_SUCCESS, response);
    }

    /** 수거 요청 상세 보기 */
    @Operation(summary = "수거 요청 상세 조회", description = """
        ## 수거 요청 ID로 수거 요청의 상세 정보를 조회합니다.
        """)
    @GetMapping("/pickups/{pickupId}")
    public ApiResponse<PickupResponseDto> getPickupDetail(@PathVariable Long pickupId) {
        PickupResponseDto pickupDetail = pickupService.getPickupDetail(pickupId);
        return ApiResponse.success(PickupSuccessCode.PICKUP_SUCCESS_CODE, pickupDetail);
    }

    /** 수거 요청 상태 변경 */
    @PutMapping("/pickups/{pickupId}/status")
    @Operation(summary = "수거 요청 상태 변경", description = """
        ## 수거 요청의 상태를 변경합니다.
        * 예: WAITING → COMPLETED
        """)
    public ApiResponse<Void> updatePickupStatus(
            @PathVariable Long pickupId,
            @RequestBody PickupStatusUpdateRequestDto requestDto
    ) {
        pickupService.updatePickupStatus(pickupId, requestDto.getStatus());
        return ApiResponse.success(PickupSuccessCode.PICKUP_STATUS_UPDATE_SUCCESS);
    }

    /** 수거 요청 수정 */
    @PutMapping("/pickups/{pickupId}")
    @Operation(summary = "수거 요청 내용 변경", description = """
        ## 수거 요청의 내용을 변경합니다.
        """)
    public ApiResponse<Void> updatePickup(
            @PathVariable Long pickupId,
            @RequestBody PickupRequestDto requestDto
    ) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        pickupService.updatePickup(pickupId, memberId, requestDto);
        return ApiResponse.success(PickupSuccessCode.PICKUP_UPDATE_SUCCESS);
    }

    /** 수거 요청 삭제 */
    @DeleteMapping("/pickups/{pickupId}")
    @Operation(summary = "수거 요청 삭제", description = """
        ## 수거 요청을 삭제합니다.
        * 요청한 사용자 본인만 삭제할 수 있습니다.
        """)
    public ApiResponse<Void> deletePickup(
            @PathVariable Long pickupId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long memberId = userDetails.getMemberId();
        pickupService.deletePickup(pickupId, memberId);
        return ApiResponse.success(PickupSuccessCode.PICKUP_DELETE_SUCCESS);
    }
}
