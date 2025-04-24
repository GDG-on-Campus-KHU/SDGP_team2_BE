package com.gdg.coffee.cafe.domain;

import com.gdg.coffee.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cafe")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cafe extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long cafeId;

    // member fk
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(nullable = false, columnDefinition="FLOAT")
    private Float latitude;

    @Column(nullable = false, columnDefinition="FLOAT")
    private Float longitude;

    @Column(name = "open_hours", nullable = false, length = 50)
    private String openHours;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "collect_schedule", nullable = false, length = 100)
    private String collectSchedule;

    public void update(CafeRequestDto dto) {
        if (dto.getName() != null)            this.name            = dto.getName();
        if (dto.getAddress() != null)         this.address         = dto.getAddress();
        if (dto.getDetailAddress() != null)   this.detailAddress   = dto.getDetailAddress();
        if (dto.getPhone() != null)           this.phone           = dto.getPhone();
        if (dto.getOpenHours() != null)       this.openHours       = dto.getOpenHours();
        if (dto.getDescription() != null)     this.description     = dto.getDescription();
        if (dto.getCollectSchedule() != null) this.collectSchedule = dto.getCollectSchedule();
    }
}
