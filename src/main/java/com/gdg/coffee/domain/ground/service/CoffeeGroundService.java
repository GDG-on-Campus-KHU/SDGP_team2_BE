package com.gdg.coffee.domain.ground.service;

import com.gdg.coffee.domain.ground.dto.*;
import java.util.List;

public interface CoffeeGroundService {
    /** 원두 등록 */
    CoffeeGroundResponseDto createGround(Long memberId, CoffeeGroundRequestDto dto);

    /** 원두 단건 조회 */
    CoffeeGroundResponseDto getGround(Long groundId);

    /** 카페별 원두 목록 조회 */
    List<CoffeeGroundResponseDto> getGroundsOfCafe(Long memberId);

    /** 원두 삭제 */
    void deleteGround(Long groundId, Long memberId);
}