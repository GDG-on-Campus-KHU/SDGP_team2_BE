package com.gdg.coffee.domain.ground.controller;

import com.gdg.coffee.domain.ground.dto.*;
import com.gdg.coffee.domain.ground.exception.CoffeeGroundSuccessCode;
import com.gdg.coffee.domain.ground.service.CoffeeGroundService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "CoffeeGround", description = "커피 찌꺼기 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CoffeeGroundController {

    private final CoffeeGroundService groundService;

    /* 1. 등록 */
    @PostMapping("/coffee_grounds")
    @Operation(summary = "[구현완료] 커피 찌꺼기 등록", description = """
        ## 카페 사용자가 커피 찌꺼기를 등록합니다.
        - 로그인된 사용자의 memberId를 기반으로 소속된 카페를 식별합니다.
        - 등록 시 원두 ID, 총 배출량, 메모 등을 입력받습니다.
        """)
    public ApiResponse<CoffeeGroundResponseDto> createGround(@RequestBody @Valid CoffeeGroundRequestDto dto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        CoffeeGroundResponseDto created = groundService.createGround(memberId, dto);
        return ApiResponse.success(CoffeeGroundSuccessCode.GROUND_CREATE_SUCCESS, created);
    }

    /* 2. 단건 조회 */
    @GetMapping("/coffee_grounds/{groundId}")
    @Operation(summary = "[구현완료] 커피 찌꺼기 단건 조회", description = """
        ## 커피 찌꺼기 ID로 단건 상세 정보를 조회합니다.
        - 누구나 접근 가능하며, 카페/사용자 모두 확인 가능합니다.
        """)
    public ApiResponse<CoffeeGroundResponseDto> getGround(@PathVariable Long groundId) {
        CoffeeGroundResponseDto response = groundService.getGround(groundId);
        return ApiResponse.success(CoffeeGroundSuccessCode.GROUND_GET_SUCCESS, response);
    }

    /* 3. 목록 (내 카페의 찌꺼기 목록) */
    @GetMapping("/cafe/coffee_grounds")
    @Operation(summary = "[구현완료] 내 카페 커피 찌꺼기 목록 조회", description = """
        ## 로그인한 카페 사용자의 소속 카페에서 등록한 찌꺼기 목록을 조회합니다.
        - 인증된 사용자의 memberId를 기준으로 카페를 식별하여 찌꺼기를 불러옵니다.
        """)
    public ApiResponse<List<CoffeeGroundResponseDto>> getGroundsOfCafe() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<CoffeeGroundResponseDto> response = groundService.getGroundsOfCafe(memberId);
        return ApiResponse.success(CoffeeGroundSuccessCode.GROUND_LIST_SUCCESS, response);
    }

    /* 4. 목록 (특정 카페의 찌꺼기 목록, 전체 공개) */
    @GetMapping("/coffee_grounds/cafe/{cafeId}")
    @Operation(summary = "카페별 찌꺼기 목록 조회 (전체 공개)", description = """
        ## 특정 카페가 등록한 모든 커피 찌꺼기 목록을 상세 정보와 함께 조회합니다.
        - 카페 ID를 경로 변수로 전달해야 합니다.
        - 누구나 접근 가능하며, 카페/사용자 모두 확인 가능합니다.
        - 응답은 해당 카페가 등록한 찌꺼기의 상세 정보 목록입니다.
        """)
    public ApiResponse<List<CoffeeGroundResponseDto>> getGroundsByCafeId(@PathVariable("cafeId") Long cafeId) {
        List<CoffeeGroundResponseDto> coffeeGrounds = groundService.getGroundsByCafeId(cafeId);
        return ApiResponse.success(CoffeeGroundSuccessCode.GROUND_LIST_SUCCESS, coffeeGrounds); // 기존 SuccessCode 사용
    }

    /* 5. 삭제 */
    @DeleteMapping("/coffee_grounds/{groundId}")
    @Operation(summary = "[구현완료] 커피 찌꺼기 삭제", description = """
        ## 커피 찌꺼기 ID를 통해 삭제합니다.
        - 삭제는 해당 카페 소속 사용자만 수행할 수 있습니다.
        - 인증된 사용자 정보 기반으로 검증이 이루어집니다.
        """)
    public ApiResponse<Void> deleteGround(@PathVariable Long groundId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        groundService.deleteGround(groundId, memberId);
        return ApiResponse.success(CoffeeGroundSuccessCode.GROUND_DELETE_SUCCESS);
    }
}
