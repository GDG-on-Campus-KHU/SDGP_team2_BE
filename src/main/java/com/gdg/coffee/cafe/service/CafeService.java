package com.gdg.coffee.cafe.service;

import com.gdg.coffee.cafe.dto.CafeRequestDto;
import com.gdg.coffee.cafe.dto.CafeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CafeService {

    /** 카페 신규 등록 */
    CafeResponseDto createCafe(CafeRequestDto requestDto);

    /** 단건 조회 */
    CafeResponseDto getCafe(Long cafeId);

    /** 회원이 보유한 카페 목록(페이지네이션) */
    Page<CafeResponseDto> getAllCafes(Pageable pageable);

    /** 정보 수정 */
    CafeResponseDto updateCafe(Long cafeId, CafeRequestDto requestDto);
}
