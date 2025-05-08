package com.gdg.coffee.domain.ground.service.impl;

import com.gdg.coffee.domain.bean.domain.Bean;
import com.gdg.coffee.domain.bean.exception.BeanErrorCode;
import com.gdg.coffee.domain.bean.repository.BeanRepository;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.cafe.exception.CafeErrorCode;
import com.gdg.coffee.domain.cafe.exception.CafeException;
import com.gdg.coffee.domain.cafe.repository.CafeRepository;
import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import com.gdg.coffee.domain.ground.domain.CoffeeGroundStatus;
import com.gdg.coffee.domain.ground.dto.CoffeeGroundRequestDto;
import com.gdg.coffee.domain.ground.dto.CoffeeGroundResponseDto;
import com.gdg.coffee.domain.ground.exception.CoffeeGroundErrorCode;
import com.gdg.coffee.domain.ground.exception.CoffeeGroundException;
import com.gdg.coffee.domain.ground.repository.CoffeeGroundRepository;
import com.gdg.coffee.domain.ground.service.CoffeeGroundService;
import com.gdg.coffee.domain.member.exception.MemberErrorCode;
import com.gdg.coffee.domain.member.exception.MemberException;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
@Transactional
public class CoffeeGroundServiceImpl implements CoffeeGroundService {

    private final CoffeeGroundRepository groundRepo;
    private final CafeRepository cafeRepo;
    private final BeanRepository beanRepo;
    private final MemberRepository memberRepository;

    @Override
    public CoffeeGroundResponseDto createGround(Long memberId,
                                                CoffeeGroundRequestDto dto) {
        // 카페 확인
        Cafe cafe = cafeRepo.findByMemberId(memberId)
                .orElseThrow(() -> new CoffeeGroundException(CafeErrorCode.CAFE_FORBIDDEN));

        // 원두 확인
        Bean bean = beanRepo.findById(dto.getBeanId())
                .orElseThrow(() -> new CoffeeGroundException(BeanErrorCode.BEAN_NOT_FOUND));

        // 본인 카페의 원두인지 확인
        if(!bean.getCafe().getId().equals(cafe.getId())) {
            throw new CoffeeGroundException(CoffeeGroundErrorCode.GROUND_FORBIDDEN);
        }

        CoffeeGround saved = groundRepo.save(dto.toEntity(cafe, bean));

        return CoffeeGroundResponseDto.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CoffeeGroundResponseDto getGround(Long groundId){
        CoffeeGround ground = groundRepo.findById(groundId)
                .orElseThrow(() -> new CoffeeGroundException(CoffeeGroundErrorCode.GROUND_NOT_FOUND));

        return CoffeeGroundResponseDto.fromEntity(ground);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoffeeGroundResponseDto> getGroundsOfCafe(Long memberId){
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Cafe cafe = cafeRepo.findByMemberId(memberId)
                .orElseThrow(() -> new CoffeeGroundException(CafeErrorCode.CAFE_NOT_FOUND));

        return groundRepo.findAllByCafeId(cafe.getId()).stream()
                .map(CoffeeGroundResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoffeeGroundResponseDto> getGroundsByCafeId(Long cafeId){
        cafeRepo.findById(cafeId).orElseThrow(() -> new CafeException(CafeErrorCode.CAFE_NOT_FOUND));

        return groundRepo.findAllByCafeId(cafeId).stream()
                .map(CoffeeGroundResponseDto::fromEntity) // 엔티티를 DTO로 매핑
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGround(Long groundId, Long memberId){
        CoffeeGround ground = groundRepo.findById(groundId)
                .orElseThrow(() -> new CoffeeGroundException(CoffeeGroundErrorCode.GROUND_NOT_FOUND));

        Cafe cafe = cafeRepo.findByMemberId(memberId)
                .orElseThrow(() -> new CoffeeGroundException(CoffeeGroundErrorCode.GROUND_FORBIDDEN));

        if(!ground.getCafe().getId().equals(cafe.getId())) {
            throw new CoffeeGroundException(CoffeeGroundErrorCode.GROUND_FORBIDDEN);
        }

        groundRepo.delete(ground);
    }
    /**
     * 원두 수거 요청 완료 시, 원두의 남은 양을 감소시키고 상태를 확인하는 메서드
     * @param groundId 원두 ID
     * @param amountDecreased 감소할 양
     */
    @Override
    @Transactional
    public void decreaseAmountAndCheckStatus(Long groundId, float amountDecreased){
        CoffeeGround ground = groundRepo.findById(groundId)
                .orElseThrow(() -> new CoffeeGroundException(CoffeeGroundErrorCode.GROUND_NOT_FOUND));

        // remainingAmount를 감소
        float newAmount = ground.getRemainingAmount() - amountDecreased;

        ground.setRemainingAmount(newAmount); // Setter 사용

        // 감소 후 잔여량에 따라 상태(status)를 업데이트
        if (newAmount == 0) {
            ground.setStatus(CoffeeGroundStatus.COMPLETED); // CoffeeGroundStatus enum 사용
        } else {
            ground.setStatus(CoffeeGroundStatus.IN_PROGRESS); // CoffeeGroundStatus enum 사용
        }
    }
}
