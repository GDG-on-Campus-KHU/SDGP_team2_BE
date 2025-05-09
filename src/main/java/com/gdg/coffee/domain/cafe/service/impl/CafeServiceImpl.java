package com.gdg.coffee.domain.cafe.service.impl;

import com.gdg.coffee.domain.cafe.exception.CafeErrorCode;
import com.gdg.coffee.domain.cafe.exception.CafeException;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.member.domain.MemberRole;
import com.gdg.coffee.domain.member.exception.MemberErrorCode;
import com.gdg.coffee.domain.member.exception.MemberException;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    /** 카페 등록*/
    @Override
    public CafeResponseDto createCafe(Long memberId, CafeRequestDto request) {
        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // MemberRole 체크
        if (member.getRole() != MemberRole.CAFE) {
            throw new CafeException(CafeErrorCode.CAFE_FORBIDDEN);
        }

        // 카페 중복 체크
        cafeRepository.findByMemberId(memberId)
                .ifPresent(cafe -> {
                    throw new CafeException(CafeErrorCode.CAFE_DUPLICATE);
                });

        Cafe cafe = request.toEntity(member);

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

    @Override
    public CafeResponseDto getCafeInfo(Long memberId) {
        Cafe cafe = cafeRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CafeException(CafeErrorCode.CAFE_NOT_FOUND));
        return CafeResponseDto.fromEntity(cafe);
    }

    @Override
    public Boolean existsByMemberId(Long memberId) {
        boolean exists = cafeRepository.existsByMemberId(memberId);
        if (!exists) {
            throw new CafeException(CafeErrorCode.CAFE_NOT_FOUND_BY_MEMBER);
        }
        return true;
    }


    /** 카페 목록(페이지네이션) */
    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> getAllCafes(Pageable pageable) {
        Page<Cafe> cafes = cafeRepository.findAll(pageable);
        return cafes.map(CafeResponseDto::fromEntity);
    }

    /** 정보 수정 */
    @Override
    public CafeResponseDto updateCafe(Long memberId, CafeRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if(member.getRole() != MemberRole.CAFE){
            throw new CafeException(CafeErrorCode.CAFE_FORBIDDEN);
        }
        Cafe cafe = cafeRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CafeException(CafeErrorCode.CAFE_NOT_FOUND));

        cafe.update(requestDto);          // dto 필드가 null 이면 유지
        return CafeResponseDto.fromEntity(cafe);
    }

    @Override
    public List<CafeResponseDto> findCafesNear(double latitude, double longitude){
        // 반경 5km 내 Cafe 엔티티 목록을 조회
        List<Cafe> cafes = cafeRepository.findCafesNear(latitude, longitude);

        return cafes.stream()
                .map(CafeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}