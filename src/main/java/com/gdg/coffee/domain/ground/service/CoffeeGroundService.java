package com.gdg.coffee.domain.ground.service;

import com.gdg.coffee.domain.ground.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoffeeGroundService {
    /** 원두 등록 */
    CoffeeGroundResponseDto createGround(Long memberId, CoffeeGroundRequestDto dto);

    /** 원두 정보 수정 */
    CoffeeGroundResponseDto getGround(Long groundId);

    /** 카페별 원두 목록 조회 */
    Page<CoffeeGroundResponseDto> getGroundsOfCafe(Long cafeId, Pageable pageable);

    /** 원두 삭제 */
    void deleteGround(Long groundId, Long memberId);
}