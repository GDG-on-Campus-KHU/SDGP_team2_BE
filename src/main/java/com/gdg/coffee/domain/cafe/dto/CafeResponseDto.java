package com.gdg.coffee.domain.cafe.dto;

import com.gdg.coffee.domain.cafe.domain.Cafe;
import lombok.*;
import java.time.LocalDateTime;

// 카페 정보 응답 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CafeResponseDto {

    private Long cafeId;
    private Long memberId;

    private String name;
    private String address;
    private String detailAddress;

    private Float latitude;
    private Float longitude;

    private String phone;
    private String collectSchedule;
    private String openHours;
    private String description;

    /**
     * Entity → DTO 변환 메서드
     */
    public static CafeResponseDto fromEntity(Cafe cafe) {
        return CafeResponseDto.builder()
                .cafeId(cafe.getCafeId())
                .memberId(cafe.getMemberId())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .detailAddress(cafe.getDetailAddress())
                .latitude(cafe.getLatitude())
                .longitude(cafe.getLongitude())
                .phone(cafe.getPhone())
                .collectSchedule(cafe.getCollectSchedule())
                .openHours(cafe.getOpenHours())
                .description(cafe.getDescription())
                .build();
    }
}
