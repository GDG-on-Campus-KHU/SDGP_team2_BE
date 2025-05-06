package com.gdg.coffee.domain.pickup.dto;

import com.gdg.coffee.domain.pickup.domain.Pickup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PickupUserSummaryDto {

    private Long pickupId;          // 수거 요청 ID
    private String cafeName;       // 요청자 이름
    private LocalDate pickupDate;       // 수거 희망일
    private LocalDateTime requestDate;      // 신청일
    private String beanName;           // 원두 종류 (ex: 에티오피아 예가체프)
    private float amount;               // 요청량 (ex: 3.5L)
    private PickupStatus status;        // 상태 (대기 중, 수락됨, 완료됨, 거절됨)

    public static PickupUserSummaryDto fromEntity(Pickup pickup) {
        return PickupUserSummaryDto.builder()
                .cafeName(pickup.getMember().getUsername())
                .requestDate(pickup.getCreatedDate())
                .pickupDate(pickup.getPickupDate())
                //.beanName(pickup.getGround().getBean().getName())
                .amount(pickup.getAmount())
                .status(pickup.getStatus())
                .build();
    }
}

