package com.gdg.coffee.domain.ground.domain;

import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coffee_ground")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
public class CoffeeGround extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ground_id")
    private Long groundId;

    @Column(nullable = false, columnDefinition = "FLOAT")
    private Float totalAmount;

    @Column(nullable = false, columnDefinition = "FLOAT")
    private Float remainingAmount;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CoffeeGroundStatus status;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "bean_id", nullable = false)
    private Long beanId;

    public void update(Float totalAmount,
                       Float remainingAmount,
                       CoffeeGroundStatus status) {

        if (totalAmount != null)        this.totalAmount     = totalAmount;
        if (remainingAmount != null)    this.remainingAmount = remainingAmount;
        if (status != null)             this.status          = status;
    }
}
