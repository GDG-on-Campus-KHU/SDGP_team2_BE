package com.gdg.coffee.domain.bean.service;

import com.gdg.coffee.domain.bean.dto.BeanRequestDto;
import com.gdg.coffee.domain.bean.dto.BeanResponseDto;
import com.gdg.coffee.domain.member.domain.Member;

import java.util.List;

public interface BeanService {
    /** 원두 등록 */
    BeanResponseDto createBean(Long memberId, BeanRequestDto requestDto);

    /** 카페별 원두 목록 조회 */
    List<BeanResponseDto> getBeansByCafe(Long memberId);


    /** 원두 정보 수정 */
    BeanResponseDto updateBean(Long beanId, Long memberId, BeanRequestDto requestDto);

    /** 원두 삭제 */
    void deleteBean(Long beanId, Long memberId);
}