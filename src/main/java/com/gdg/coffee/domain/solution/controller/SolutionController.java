package com.gdg.coffee.domain.solution.controller;

import com.gdg.coffee.domain.solution.dto.request.SolutionRequestDto;
import com.gdg.coffee.domain.solution.dto.response.SolutionResponseDto;
import com.gdg.coffee.domain.solution.exception.SolutionSuccessCode;
import com.gdg.coffee.domain.solution.service.SolutionService;
import com.gdg.coffee.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping("/generate")
    @Operation(
            summary = "업사이클링 솔루션 생성",
            description = """
    페이지 진입 또는 사용자가 조건(목적/원두)을 선택할 때 호출되는 API입니다.
    
    📌 참고 사항:
    - 매 호출 시 기존 솔루션은 삭제되고, 새로운 솔루션 3개가 생성되어 DB에 저장됩니다.
    - 생성된 솔루션은 응답으로 함께 반환되며, 각각의 ID를 통해 상세 조회가 가능합니다.
    - 이 ID는 리프레시되면 유효하지 않을 수 있으므로, 응답 이후 바로 사용하는 구조를 권장합니다.
    
    ✔️ 사용 예시:
    1. 페이지 진입 시 바로 호출
    2. 목적/원두 선택 시 재호출
    """
    )
    public ResponseEntity<ApiResponse<List<SolutionResponseDto>>> generateSolutions(
            @RequestBody SolutionRequestDto request
    ) {
        List<SolutionResponseDto> result = solutionService.generateSolutions(request);
        return new ResponseEntity<>(ApiResponse.success(SolutionSuccessCode.GENERATED, result), HttpStatus.CREATED);
    }

    /**
     * 특정 솔루션 상세 조회
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "업사이클링 솔루션 상세 조회",
            description = """
    업사이클링 솔루션 상세 정보를 조회하는 API입니다.
    
    📌 참고 사항:
    - `POST /api/solutions/generate` 호출 시 받은 ID를 이용해 상세 정보를 조회합니다.
    - 이 ID는 새로 솔루션이 생성되면 무효화되므로, **항상 최신 생성 응답에 있는 ID만 사용**해야 합니다.
    
    ✔️ 사용 예시:
    - 카드 클릭 시 해당 ID로 상세 정보를 불러올 때
    """
    )
    public ResponseEntity<ApiResponse<SolutionResponseDto>> getSolutionById(@PathVariable Long id) {
        SolutionResponseDto solution = solutionService.getSolutionById(id);
        return new ResponseEntity<>(ApiResponse.success(SolutionSuccessCode.GET_DETAILS, solution), HttpStatus.OK);
    }
}
