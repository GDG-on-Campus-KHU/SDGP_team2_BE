package com.gdg.coffee.domain.ground.dto;

import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CoffeeGroundRequestDto {

    @NotNull private Float amount;
    @NotBlank private String collectedDate;
    @Size(max = 1024) private String memo;

    public CoffeeGround toEntity(Long cafeId, Long beanId) {
        return CoffeeGround.builder()
                .cafeId(cafeId)
                .beanId(beanId)
                .totalAmount(amount)
                .remainingAmount(0F)
                .collectedDate(collectedDate)
                .memo(memo)
                .build();
    }
}