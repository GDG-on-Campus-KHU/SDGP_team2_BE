package com.gdg.coffee.domain.bean.service.impl;

import com.gdg.coffee.domain.bean.domain.Bean;
import com.gdg.coffee.domain.bean.dto.BeanRequestDto;
import com.gdg.coffee.domain.bean.dto.BeanResponseDto;
import com.gdg.coffee.domain.bean.exception.BeanErrorCode;
import com.gdg.coffee.domain.bean.exception.BeanException;
import com.gdg.coffee.domain.bean.repository.BeanRepository;
import com.gdg.coffee.domain.bean.service.BeanService;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.cafe.exception.CafeErrorCode;
import com.gdg.coffee.domain.cafe.exception.CafeException;
import com.gdg.coffee.domain.cafe.repository.CafeRepository;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.member.domain.MemberRole;
import com.gdg.coffee.domain.member.exception.MemberErrorCode;
import com.gdg.coffee.domain.member.exception.MemberException;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BeanServiceImpl implements BeanService {

    private final BeanRepository beanRepository;
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    /** 1. 원두 등록 */
    @Override
    public BeanResponseDto createBean(Long memberId, BeanRequestDto dto) {
        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // MemberRole 체크
        if (member.getRole() != MemberRole.CAFE){
            throw new BeanException(BeanErrorCode.BEAN_FORBIDDEN);
        }

        // 카페 조회
        Cafe cafe = cafeRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CafeException(CafeErrorCode.CAFE_NOT_FOUND));

        Bean bean = dto.toEntity(cafe);
        Bean saved = beanRepository.save(bean);
        return BeanResponseDto.fromEntity(saved);
    }

    /** 2. 카페별 원두 목록 조회 */
    @Override
    @Transactional(readOnly = true)
    public List<BeanResponseDto> getBeansByCafeId(Long cafeId) {
        cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeException(CafeErrorCode.CAFE_NOT_FOUND));

        // 원두 조회 및 DTO 변환
        return beanRepository.findAllByCafeId(cafeId).stream()
                .map(BeanResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /** 3. 원두 정보 수정 */
    @Override
    public BeanResponseDto updateBean(Long beanId, BeanRequestDto requestDto) {
        Bean bean = beanRepository.findById(beanId)
                .orElseThrow(() -> new BeanException(BeanErrorCode.BEAN_NOT_FOUND));

        // 추후 권한 검증 로직 수정


        bean.update(requestDto);
        return BeanResponseDto.fromEntity(bean);
    }

    /** 4. 원두 삭제 */
    @Override
    public void deleteBean(Long beanId) {
        Bean bean = beanRepository.findById(beanId)
                .orElseThrow(() -> new BeanException(BeanErrorCode.BEAN_NOT_FOUND));

        //추후 권한 검증 추가

        beanRepository.delete(bean);
    }
}
