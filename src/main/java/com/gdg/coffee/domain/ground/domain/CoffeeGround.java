package com.gdg.coffee.domain.ground.domain;

import com.gdg.coffee.domain.bean.domain.Bean;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime startDateTime;

    @Column(nullable = false, columnDefinition = "FLOAT")
    private Float totalAmount;

    @Column(nullable = false, columnDefinition = "FLOAT")
    private Float remainingAmount;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CoffeeGroundStatus status;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bean_id", nullable = false)
    private Bean bean;

    public void update(Float totalAmount,
                       Float remainingAmount,
                       CoffeeGroundStatus status) {

        if (totalAmount != null)        this.totalAmount     = totalAmount;
        if (remainingAmount != null)    this.remainingAmount = remainingAmount;
        if (status != null)             this.status          = status;
    }
}
