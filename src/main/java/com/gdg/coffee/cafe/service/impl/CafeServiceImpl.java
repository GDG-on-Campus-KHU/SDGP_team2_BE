package com.gdg.coffee.cafe.service.impl;

import com.gdg.coffee.cafe.dto.CafeCreateResponseDto;
import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.response.cafe.*;
import com.gdg.coffee.global.common.response.member.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdg.coffee.cafe.dto.CafeRequestDto;
import com.gdg.coffee.cafe.dto.CafeResponseDto;
import com.gdg.coffee.cafe.domain.Cafe;
import com.gdg.coffee.cafe.repository.CafeRepository;
import com.gdg.coffee.cafe.service.CafeService;
import com.gdg.coffee.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    /**
     * 카페 등록
     */
    @Override
    @Transactional
    public CafeCreateResponseDto createCafe(CafeRequestDto request) {
        // Member 조회
        memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));

        Cafe cafe = request.toEntity();
        // DB에 저장 및 반환
        Cafe saved = cafeRepository.save(cafe);
        return CafeCreateResponseDto.fromEntity(saved);
    }

    /** 단건 조회 */
    @Override
    @Transactional(readOnly = true)
    public CafeResponseDto getCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BaseException(CafeErrorCode.CAFE_NOT_FOUND));

        return CafeResponseDto.fromEntity(cafe);
    }

    /** 회원이 보유한 카페 목록(페이지네이션) */
    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> getAllCafes(Pageable pageable) {
        Page<Cafe> cafes = cafeRepository.findAll(pageable);
        return cafes.map(CafeResponseDto::fromEntity);
    }

    /** 정보 수정 */
    @Override
    public CafeResponseDto updateCafe(Long cafeId, CafeRequestDto requestDto) {
        throw new UnsupportedOperationException("updateCafe is not implemented yet");
    }
}