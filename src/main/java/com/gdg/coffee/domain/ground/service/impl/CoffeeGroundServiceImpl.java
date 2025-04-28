package com.gdg.coffee.domain.ground.service.impl;

import com.gdg.coffee.domain.ground.dto.CoffeeGroundRequestDto;
import com.gdg.coffee.domain.ground.dto.CoffeeGroundResponseDto;
import com.gdg.coffee.domain.ground.service.CoffeeGroundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Slf4j
@Transactional
public class CoffeeGroundServiceImpl implements CoffeeGroundService {

    @Override
    public CoffeeGroundResponseDto createGround(Long memberId, CoffeeGroundRequestDto dto){
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CoffeeGroundResponseDto getGround(Long groundId){
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CoffeeGroundResponseDto> getGroundsOfCafe(Long cafeId, Pageable pageable){
        return null;
    }

    @Override
    public void deleteGround(Long groundId, Long memberId){

    }
}
