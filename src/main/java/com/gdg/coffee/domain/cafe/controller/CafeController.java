package com.gdg.coffee.domain.cafe.controller;

import com.gdg.coffee.domain.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.cafe.dto.CafeResponseDto;
import com.gdg.coffee.domain.cafe.exception.CafeSuccessCode;
import com.gdg.coffee.domain.cafe.service.CafeService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Cafe", description = "카페 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes")
public class CafeController {

    private final CafeService cafeService;

    /** 1. 카페 생성 (201 Created) */
    @PostMapping
    @Operation(summary = "[구현완료] 카페 등록", description = """
        ## 카페 사용자로 로그인한 회원이 카페를 등록합니다.
        - 요청 본문에는 카페 이름, 주소, 상세주소, 연락처, 영업시간, 설명, 수거 일정 등이 포함됩니다.
        - 로그인한 회원의 memberId를 기반으로 카페가 등록됩니다.
        - 한 명의 회원은 하나의 카페만 등록할 수 있습니다.
        """)
    public ApiResponse<CafeResponseDto> createCafe(@RequestBody @Valid CafeRequestDto requestDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        CafeResponseDto created = cafeService.createCafe(memberId, requestDto);
        return ApiResponse.success(CafeSuccessCode.CAFE_CREATE_SUCCESS, created);
    }

    /** 2. 단건 조회 (200 OK) */
    @GetMapping("/{cafeId}")
    @Operation(summary = "[구현완료] 카페 단건 조회", description = """
        ## 특정 ID에 해당하는 카페 정보를 조회합니다.
        - 누구나 접근 가능하며, 카페 상세 정보를 확인할 수 있습니다.
        - 카페 ID는 URL 경로 변수로 전달됩니다.
        """)
    public ApiResponse<CafeResponseDto> getCafe(@PathVariable Long cafeId) {
        CafeResponseDto dto = cafeService.getCafe(cafeId);
        return ApiResponse.success(CafeSuccessCode.CAFE_GET_SUCCESS, dto);
    }

    /** 3. 전체 카페 목록 조회 (200 OK)*/
    @GetMapping
    @Operation(summary = "[구현완료] 전체 카페 목록 조회", description = """
        ## 등록된 모든 카페 목록을 조회합니다.
        - 페이징 처리를 지원하며, size 기본값은 전체 목록입니다.
        - 누구나 접근 가능합니다.
        """)
    public ApiResponse<Page<CafeResponseDto>> getAllCafes(
            @ParameterObject Pageable pageable) {
        Page<CafeResponseDto> page = cafeService.getAllCafes(pageable);
        return ApiResponse.success(CafeSuccessCode.CAFE_GET_LIST_SUCCESS, page);
    }

    /** 4. 정보 수정 (200 OK) */
    @PutMapping
    @Operation(summary = "[구현완료] 카페 정보 수정", description = """
        ## 로그인한 카페 사용자가 자신의 카페 정보를 수정합니다.
        - 로그인된 사용자 정보(memberId)를 기준으로 본인의 카페를 조회 후 수정합니다.
        - 입력된 필드 중 null이 아닌 값만 수정됩니다.
        """)
    public ApiResponse<CafeResponseDto> updateCafe(@RequestBody CafeRequestDto requestDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        CafeResponseDto updated = cafeService.updateCafe(memberId, requestDto);
        return ApiResponse.success(CafeSuccessCode.CAFE_UPDATE_SUCCESS, updated);
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 카페 정보 조회",
            description = """
            현재 로그인한 사용자의 카페 정보를 반환합니다.
            
            - JWT 토큰을 통해 인증된 사용자만 호출할 수 있습니다.
            - 사용자가 카페를 보유하지 않은 경우, 404 예외(CAFE_NOT_FOUND)가 발생합니다.
            - 응답 데이터에는 카페 이름, 주소, 설명, 연락처 등의 상세 정보가 포함됩니다.
            """
    )
    public ApiResponse<CafeResponseDto> getCafeInfo() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        CafeResponseDto cafeInfo = cafeService.getCafeInfo(memberId);
        return ApiResponse.success(CafeSuccessCode.CAFE_GET_SUCCESS, cafeInfo);
    }

    @GetMapping("/me/exists")
    @Operation(
            summary = "내 카페 존재 여부 확인",
            description = """
            현재 로그인한 사용자가 카페를 등록했는지 여부를 반환합니다.
        
            - true: 카페가 있음
            - false: 카페가 없음
            - JWT 인증이 필요합니다.
            """
    )
    public ApiResponse<Boolean> hasCafe() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Boolean hasCafe = cafeService.existsByMemberId(memberId);
        return ApiResponse.success(CafeSuccessCode.CAFE_EXIST_CHECK_SUCCESS, hasCafe);
    }

    @GetMapping("/near")
    @Operation(summary = "반경 내 카페 목록 조회 (5km)", description = """
        ## 현재 위치 반경 5km 이내의 카페 목록을 조회합니다.
        - 누구나 접근 가능합니다.
        - 요청 파라미터로 사용자의 현재 위치(위도, 경도)를 전달해야 합니다.
        - 유효한 범위(-90~90, -180~180)
        - 결과는 거리가 가까운 순서로 정렬되지 않을 수 있으며, 이 기능은 현재 페이징을 지원하지 않습니다.
        """)
    public ApiResponse<List<CafeResponseDto>> getNearbyCafes(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    ) {
        List<CafeResponseDto> nearbyCafes = cafeService.findCafesNear(latitude, longitude);
        return ApiResponse.success(CafeSuccessCode.CAFE_GET_NEARBY_LIST_SUCCESS, nearbyCafes);
    }
}
