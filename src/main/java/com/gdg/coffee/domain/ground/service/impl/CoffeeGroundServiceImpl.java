package com.gdg.coffee.domain.ground.service.impl;

import com.gdg.coffee.domain.bean.domain.Bean;
import com.gdg.coffee.domain.bean.exception.BeanErrorCode;
import com.gdg.coffee.domain.bean.repository.BeanRepository;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.cafe.exception.CafeErrorCode;
import com.gdg.coffee.domain.cafe.repository.CafeRepository;
import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import com.gdg.coffee.domain.ground.dto.CoffeeGroundRequestDto;
import com.gdg.coffee.domain.ground.dto.CoffeeGroundResponseDto;
import com.gdg.coffee.domain.ground.exception.CoffeeGroundErrorCode;
import com.gdg.coffee.domain.ground.exception.CoffeeGroundException;
import com.gdg.coffee.domain.ground.repository.CoffeeGroundRepository;
import com.gdg.coffee.domain.ground.service.CoffeeGroundService;
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

    @Override
    public CoffeeGroundResponseDto createGround(Long memberId,
                                                CoffeeGroundRequestDto dto) {
        // 카페 확인
        Cafe cafe = cafeRepo.findByMemberId(memberId)
                .orElseThrow(() -> new CoffeeGroundException(CafeErrorCode.CAFE_FORBIDDEN));
        Long cafeId = cafe.getCafeId();

        // 원두 확인
        Bean bean = beanRepo.findByBeanId(dto.getBeanId())
                .orElseThrow(() -> new CoffeeGroundException(BeanErrorCode.BEAN_NOT_FOUND));

        // 본인 카페의 원두인지 확인
        if(!bean.getCafeId().equals(cafeId)) {
            throw new CoffeeGroundException(CoffeeGroundErrorCode.GROUND_FORBIDDEN);
        }

        CoffeeGround saved = groundRepo.save(dto.toEntity(cafeId));
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
    public List<CoffeeGroundResponseDto> getGroundsOfCafe(Long cafeId){
        cafeRepo.findById(cafeId)
                .orElseThrow(() -> new CoffeeGroundException(CafeErrorCode.CAFE_NOT_FOUND));

        return groundRepo.findAllByCafeId(cafeId).stream()
                .map(CoffeeGroundResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGround(Long groundId, Long memberId){

    }
}
