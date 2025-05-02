package com.gdg.coffee.domain.bean.dto;

import com.gdg.coffee.domain.bean.domain.Bean;
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

    /**
     * Entity -> DTO 변환
     */
    public static BeanResponseDto fromEntity(Bean bean) {
        return new BeanResponseDto(
                bean.getId(),
                bean.getName(),
                bean.getOrigin(),
                bean.getDescription()
        );
    }
}