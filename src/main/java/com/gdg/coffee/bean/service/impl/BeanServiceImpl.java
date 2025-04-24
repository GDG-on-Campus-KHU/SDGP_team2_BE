package com.gdg.coffee.bean.service.impl;

import com.gdg.coffee.bean.domain.Bean;
import com.gdg.coffee.bean.dto.BeanRequestDto;
import com.gdg.coffee.bean.dto.BeanResponseDto;
import com.gdg.coffee.bean.repository.BeanRepository;
import com.gdg.coffee.bean.service.BeanService;
import com.gdg.coffee.cafe.repository.CafeRepository;
import com.gdg.coffee.global.common.exception.BaseException;
import com.gdg.coffee.global.common.response.bean.BeanErrorCode;
import com.gdg.coffee.global.common.response.cafe.CafeErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BeanServiceImpl implements BeanService {

    private final BeanRepository beanRepository;
    private final CafeRepository cafeRepository;

    /** 1. 원두 등록 */
    @Override
    public BeanResponseDto createBean(BeanRequestDto dto) {
        cafeRepository.findById(dto.getCafeId())
                .orElseThrow(() -> new BaseException(CafeErrorCode.CAFE_NOT_FOUND));

        // 추후 권한 검증 추가

        Bean saved = beanRepository.save(dto.toEntity());
        return BeanResponseDto.fromEntity(saved);
    }

    /** 2. 카페별 원두 목록 조회 */
    @Override
    @Transactional(readOnly = true)
    public List<BeanResponseDto> getBeansByCafeId(Long cafeId) {
        cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BaseException(CafeErrorCode.CAFE_NOT_FOUND));

        // 원두 조회 및 DTO 변환
        return beanRepository.findAllByCafeId(cafeId).stream()
                .map(BeanResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /** 3. 원두 정보 수정 */
    @Override
    public BeanResponseDto updateBean(Long beanId, BeanRequestDto requestDto) {
        Bean bean = beanRepository.findById(beanId)
                .orElseThrow(() -> new BaseException(BeanErrorCode.BEAN_NOT_FOUND));

        // 추후 권한 검증 로직 수정
        if (!bean.getCafeId().equals(requestDto.getCafeId())) {
            throw new BaseException(BeanErrorCode.BEAN_FORBIDDEN);
        }

        bean.update(requestDto);
        return BeanResponseDto.fromEntity(bean);
    }

    /** 4. 원두 삭제 */
    @Override
    public void deleteBean(Long beanId) {
        // just stub
        throw new UnsupportedOperationException("deleteBean not implemented yet");
    }
}
