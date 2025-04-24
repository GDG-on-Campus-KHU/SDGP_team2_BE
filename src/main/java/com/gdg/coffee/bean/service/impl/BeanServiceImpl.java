package com.gdg.coffee.bean.service.impl;

import com.gdg.coffee.bean.dto.BeanRequestDto;
import com.gdg.coffee.bean.dto.BeanResponseDto;
import com.gdg.coffee.bean.repository.BeanRepository;
import com.gdg.coffee.bean.service.BeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BeanServiceImpl implements BeanService {

    private final BeanRepository beanRepository;

    /** 1. 원두 등록 */
    @Override
    public BeanResponseDto createBean(BeanRequestDto requestDto) {
        // just stub
        throw new UnsupportedOperationException("createBean not implemented yet");
    }

    /** 2. 카페별 원두 목록 조회 */
    @Override
    @Transactional(readOnly = true)
    public List<BeanResponseDto> getBeansByCafeId(Long cafeId) {
        // just stub
        throw new UnsupportedOperationException("getBeansByCafeId not implemented yet");
    }

    /** 3. 원두 정보 수정 */
    @Override
    public BeanResponseDto updateBean(Long beanId, BeanRequestDto requestDto) {
        // just stub
        throw new UnsupportedOperationException("updateBean not implemented yet");
    }

    /** 4. 원두 삭제 */
    @Override
    public void deleteBean(Long beanId) {
        // just stub
        throw new UnsupportedOperationException("deleteBean not implemented yet");
    }
}
