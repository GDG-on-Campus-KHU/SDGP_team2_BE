package com.gdg.coffee.domain.cafe.dto;

import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.member.domain.Member;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CafeRequestDto {

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
    public Cafe toEntity(Member member) {
        return Cafe.builder()
                .member(member)  // memberId가 아닌 member 객체 사용
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
