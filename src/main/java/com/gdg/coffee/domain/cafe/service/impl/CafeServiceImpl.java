package com.gdg.coffee.domain.cafe.service.impl;

import com.gdg.coffee.domain.cafe.exception.CafeException;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.member.exception.MemberErrorCode;
import com.gdg.coffee.domain.member.exception.MemberException;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import com.gdg.coffee.global.common.response.cafe.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdg.coffee.domain.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.cafe.dto.CafeResponseDto;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.cafe.repository.CafeRepository;
import com.gdg.coffee.domain.cafe.service.CafeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    /** 카페 등록*/
    @Override
    @Transactional
    public CafeResponseDto  createCafe(CafeRequestDto request) {
        // Member 조회
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Cafe cafe = request.toEntity(member);
        // DB에 저장 및 반환
        Cafe saved = cafeRepository.save(cafe);
        return CafeResponseDto.fromEntity(saved);
    }

    /** 단건 조회 */
    @Override
    @Transactional(readOnly = true)
    public CafeResponseDto getCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeException(CafeErrorCode.CAFE_NOT_FOUND));

        return CafeResponseDto.fromEntity(cafe);
    }

    /** 회원이 보유한 카페 목록(페이지네이션) */
    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> getAllCafes(Pageable pageable) {
        throw new UnsupportedOperationException("getCafesByMember is not implemented yet");
    }

    /** 정보 수정 */
    @Override
    public CafeResponseDto updateCafe(Long cafeId, CafeRequestDto requestDto) {
        throw new UnsupportedOperationException("updateCafe is not implemented yet");
    }
}