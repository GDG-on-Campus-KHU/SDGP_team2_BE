package com.gdg.coffee.bean.controller;

import com.gdg.coffee.bean.dto.BeanRequestDto;
import com.gdg.coffee.bean.dto.BeanResponseDto;
import com.gdg.coffee.bean.service.BeanService;
import com.gdg.coffee.global.common.response.ApiResponse;
import com.gdg.coffee.global.common.response.bean.BeanSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beans")
@RequiredArgsConstructor
@Validated
public class BeanController {

    private final BeanService beanService;

    /** 1. 원두 등록 (201 CREATED) */
    @PostMapping
    public ApiResponse<BeanResponseDto> createBean(@Valid @RequestBody BeanRequestDto requestDto) {
        BeanResponseDto created = beanService.createBean(requestDto);
        return ApiResponse.success(BeanSuccessCode.BEAN_CREATE_SUCCESS, created);
    }

    /** 2. 카페별 원두 목록 조회 (200 OK) */
    @GetMapping("/{cafeId}")
    public ApiResponse<List<BeanResponseDto>> getBeansByCafe(@PathVariable Long cafeId) {
        List<BeanResponseDto> list = beanService.getBeansByCafeId(cafeId);
        return ApiResponse.success(BeanSuccessCode.BEAN_LIST_SUCCESS, list);
    }

    /** 3. 원두 정보 수정 (200 OK) */
    @PutMapping("/{beanId}")
    public ApiResponse<BeanResponseDto> updateBean(
            @PathVariable Long beanId,
            @RequestBody BeanRequestDto requestDto) {
        BeanResponseDto updated = beanService.updateBean(beanId, requestDto);
        return ApiResponse.success(BeanSuccessCode.BEAN_UPDATE_SUCCESS, updated);
    }

    /** 4. 원두 삭제 (200 OK) */
    @DeleteMapping("/{beanId}")
    public ApiResponse<Void> deleteBean(@PathVariable Long beanId) {
        beanService.deleteBean(beanId);
        return ApiResponse.success(BeanSuccessCode.BEAN_DELETE_SUCCESS);
    }
}
