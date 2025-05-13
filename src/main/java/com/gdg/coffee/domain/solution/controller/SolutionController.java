package com.gdg.coffee.domain.solution.controller;

import com.gdg.coffee.domain.solution.dto.request.SolutionRequestDto;
import com.gdg.coffee.domain.solution.dto.response.SolutionResponseDto;
import com.gdg.coffee.domain.solution.exception.SolutionSuccessCode;
import com.gdg.coffee.domain.solution.service.SolutionService;
import com.gdg.coffee.global.common.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<SolutionResponseDto>> getSolutionById(@PathVariable Long id) {
        SolutionResponseDto solution = solutionService.getSolutionById(id);
        return new ResponseEntity<>(ApiResponse.success(SolutionSuccessCode.GET_DETAILS, solution), HttpStatus.OK);
    }
}
