package com.gdg.coffee.domain.pickup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PickupStatusUpdateRequestDto {
    private PickupStatus status;
}
