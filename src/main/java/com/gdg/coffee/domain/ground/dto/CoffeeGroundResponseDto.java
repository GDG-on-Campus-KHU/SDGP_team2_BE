package com.gdg.coffee.domain.ground.dto;

import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import com.gdg.coffee.domain.ground.domain.CoffeeGroundStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
public class CoffeeGroundResponseDto {
    private Long groundId;

    private String date;
    private Float totalAmount;
    private Float remainingAmount;

    private String note;
    private CoffeeGroundStatus status;

    private Long cafeId;
    private Long beanId;

    public static CoffeeGroundResponseDto fromEntity(CoffeeGround g) {
        return CoffeeGroundResponseDto.builder()
                .groundId(g.getGroundId())
                .date(g.getDate())
                .totalAmount(g.getTotalAmount())
                .remainingAmount(g.getRemainingAmount())
                .note(g.getNote())
                .status(g.getStatus())
                .cafeId(g.getCafe().getId())
                .beanId(g.getBean().getId())
                .build();
    }
}