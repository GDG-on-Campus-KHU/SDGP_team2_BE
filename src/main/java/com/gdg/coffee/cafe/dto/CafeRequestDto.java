package com.gdg.coffee.cafe.dto;

import com.gdg.coffee.cafe.domain.Cafe;
import jakarta.validation.constraints.*;
import lombok.*;

//카페 생성·수정 요청 DTO
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CafeRequestDto {

    @NotNull(message = "소유 회원 ID는 필수입니다.")
    private Long memberId;

    @NotBlank @Size(max = 255)
    private String name;

    @NotBlank @Size(max = 255)
    private String address;

    @NotBlank @Size(max = 255)
    private String detailAddress;

    @NotBlank @Size(max = 50)
    private String phone;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    @NotBlank @Size(max = 50)
    private String openHours;

    @Size(max = 2048)
    private String description;

    @NotBlank @Size(max = 100)
    private String collectSchedule;

    /**
     * DTO → Entity 변환 메서드
     */
    public Cafe toEntity() {
        return Cafe.builder()
                .memberId(memberId)
                .name(name)
                .address(address)
                .detailAddress(detailAddress)
                .latitude(latitude)
                .longitude(longitude)
                .phone(phone)
                .collectSchedule(collectSchedule)
                .openHours(openHours)
                .description(description)
                .build();
    }
}
