package com.gdg.coffee.domain.ground.domain;

import com.gdg.coffee.domain.ground.dto.CoffeeGroundRequestDto;
import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coffee_ground")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
public class CoffeeGround extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ground_id")
    private Long groundId;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "bean_id", nullable = false)
    private Long beanId;

    @Column(nullable = false, columnDefinition = "FLOAT")
    private Float totalAmount;

    @Column(nullable = false, columnDefinition = "FLOAT")
    private Float remainingAmount;

    @Column(nullable = false)
    private String collectedDate;

    @Column(length = 1024)
    private String memo;

    public void update(CoffeeGroundRequestDto dto) {
        if (dto.getAmount()        != null) this.totalAmount        = dto.getAmount();
        if (dto.getCollectedDate() != null) this.collectedDate = dto.getCollectedDate();
        if (dto.getMemo()          != null) this.memo          = dto.getMemo();
    }
}
