package com.gdg.coffee.cafe.dto;

import com.gdg.coffee.cafe.domain.Cafe;
import lombok.*;

import java.time.LocalDateTime;


//카페 생성 응답용 DTO (Data 부분만 간소화)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CafeCreateResponseDto {

    private final Long cafeId;
    private final LocalDateTime createdAt;

    /**
     * 엔티티 → 최소 정보 DTO 변환
     */
    public static CafeCreateResponseDto fromEntity(Cafe cafe) {
        return CafeCreateResponseDto.builder()
                .cafeId(cafe.getCafeId())
                .createdAt(cafe.getCreatedDate())
                .build();
    }
}

