package com.gdg.coffee.domain.cafe.service;

import com.gdg.coffee.domain.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.cafe.dto.CafeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CafeService {

    /** 카페 신규 등록 */
    CafeResponseDto createCafe(Long memberId, CafeRequestDto requestDto);

    /** 단건 조회 */
    CafeResponseDto getCafe(Long cafeId);

    /** 내 카페 조회*/
    CafeResponseDto getCafeInfo(Long memberId);

    Boolean existsByMemberId(Long memberId);

    /** 회원이 보유한 카페 목록(페이지네이션) */
    Page<CafeResponseDto> getAllCafes(Pageable pageable);

    /** 정보 수정 */
    CafeResponseDto updateCafe(Long memberId, CafeRequestDto requestDto);

    /** 근처 카페 목록 조회 */
    List<CafeResponseDto> findCafesNear(double latitude, double longitude);
}
