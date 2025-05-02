package com.gdg.coffee.domain.pickup.dto;

import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.pickup.domain.Pickup;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PickupRequestDto {
    private float amount;               // 수거 요청할 양
    private String message;             // 수거 요청 메시지 (선택)
    private LocalDate pickupDate;       // 희망 수거일

    public static Pickup toEntity(PickupRequestDto requestDto, Member member) {
        return Pickup.builder()
                .member(member)
                .pickupDate(requestDto.getPickupDate())
                .amount(requestDto.getAmount())
                .message(requestDto.getMessage())
                .build();
    }

}
