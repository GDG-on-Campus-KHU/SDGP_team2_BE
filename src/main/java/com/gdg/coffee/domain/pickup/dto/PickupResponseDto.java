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
public class PickupResponseDto {

    private Long pickupId;
    private Long groundId;
    private Long memberId;
    private float amount;               // 수거 양
    private String message;             // 수거 요청 메시지 (선택)
    private LocalDate pickupDate;       // 희망 수거일
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PickupResponseDto fromEntity(Pickup pickup) {
        return PickupResponseDto.builder()
                .pickupId(pickup.getId())
                .groundId(pickup.getGround().getGroundId())
                .memberId(pickup.getMember().getId())
                .amount(pickup.getAmount())
                .message(pickup.getMessage())
                .pickupDate(pickup.getPickupDate())
                .createdAt(pickup.getCreatedDate())
                .updatedAt(pickup.getModifiedDate())
                .build();
    }
}
