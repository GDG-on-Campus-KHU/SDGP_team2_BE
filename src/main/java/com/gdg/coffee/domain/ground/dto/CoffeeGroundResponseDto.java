package com.gdg.coffee.domain.ground.dto;

import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import lombok.*;

@Getter @Builder
@AllArgsConstructor
public class CoffeeGroundResponseDto {
    private Long groundId;
    private Long cafeId;
    private Float totalAmount;
    private Float remainingAmount;
    private String collectedDate;
    private String memo;

    public static CoffeeGroundResponseDto fromEntity(CoffeeGround g) {
        return CoffeeGroundResponseDto.builder()
                .groundId(g.getGroundId())
                .cafeId(g.getCafeId())
                .totalAmount(g.getTotalAmount())
                .remainingAmount(g.getRemainingAmount())
                .collectedDate(g.getCollectedDate())
                .memo(g.getMemo())
                .build();
    }
}