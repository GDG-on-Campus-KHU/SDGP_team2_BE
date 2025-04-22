package com.gdg.coffee.cafe.service.impl;

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
     * 새로운 카페를 등록하고, 등록된 정보를 DTO로 반환한다.
     */
    @Override
    @Transactional
    public CafeResponseDto  createCafe(CafeRequestDto request) {
        Long memberId = request.getMemberId();
        // 회원 ID로 회원 존재 여부 확인
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("Member not found: " + memberId);
        }

        // request body 정보로 Cafe 객체 생성
        Cafe cafe = Cafe.builder()
                .memberId(request.getMemberId())
                .name(request.getName())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .phone(request.getPhone())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .openHours(request.getOpenHours())
                .description(request.getDescription())
                .collectSchedule(request.getCollectSchedule())
                .build();

        // DB에 저장
        Cafe saved = cafeRepository.save(cafe);

        // 카페 정보 DTO로 변환 후 반환
        return CafeResponseDto.fromEntity(saved);
    }

    /**
     * 단건 조회
     * 카페 ID로 카페를 찾아 응답 DTO로 변환하여 반환합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public CafeResponseDto getCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("Cafe not found: " + cafeId));

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