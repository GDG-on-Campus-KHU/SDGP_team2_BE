package com.gdg.coffee.domain.ground.dto;

import com.gdg.coffee.domain.bean.domain.Bean;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import com.gdg.coffee.domain.ground.domain.CoffeeGroundStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CoffeeGroundRequestDto {

    @NotNull @Positive private Float amount;
    @Size(max = 2028) private String note;
    @NotNull private Long beanId;

    public CoffeeGround toEntity(Cafe cafe, Bean bean) {
        return CoffeeGround.builder()
                .cafe(cafe)
                .bean(bean)
                .totalAmount(amount)
                .remainingAmount(0F)
                .note(note)
                .status(CoffeeGroundStatus.WAITING)
                .build();
    }
}