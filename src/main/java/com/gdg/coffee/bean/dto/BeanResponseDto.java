package com.gdg.coffee.bean.dto;

import com.gdg.coffee.bean.domain.Bean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 원두(Bean) 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BeanResponseDto {

    private Long beanId;
    private String name;
    private String origin;
    private String description;
    private Long cafeId;

    /**
     * Entity -> DTO 변환
     */
    public static BeanResponseDto fromEntity(Bean bean) {
        return new BeanResponseDto(
                bean.getBeanId(),
                bean.getName(),
                bean.getOrigin(),
                bean.getDescription(),
                bean.getCafeId()
        );
    }
}