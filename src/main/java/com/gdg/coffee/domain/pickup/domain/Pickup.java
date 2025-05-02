package com.gdg.coffee.domain.pickup.domain;

import com.gdg.coffee.domain.coffeeground.domain.CoffeeGround;
import com.gdg.coffee.domain.common.BaseTime;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.pickup.dto.PickupStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pickup")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Pickup extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pickup_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PickupStatus status; // 대기중, 수락됨, 완료됨, 거절됨

    @Column(nullable = false)
    private float amount;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "pickup_date")
    private LocalDate pickupDate; // 희망 수거일

    // 관계: 수거 신청한 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 관계: 수거 요청 대상 원두 찌꺼기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ground_id", nullable = false)
    private CoffeeGround ground;

    public void update(LocalDate pickupDate, String message, float amount) {
        this.pickupDate = pickupDate;
        this.message = message;
        this.amount = amount;
    }

    public void updatePickupStatus(PickupStatus status){
        this.status = status;
    }
}

