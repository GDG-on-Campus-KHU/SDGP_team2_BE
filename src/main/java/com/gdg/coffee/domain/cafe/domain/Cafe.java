package com.gdg.coffee.domain.cafe.domain;

import com.gdg.coffee.domain.bean.domain.Bean;
import com.gdg.coffee.domain.cafe.dto.CafeRequestDto;
import com.gdg.coffee.domain.common.BaseTime;
import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import com.gdg.coffee.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

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

    // 연관 관계
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoffeeGround> coffeeGrounds;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bean> beans;

    /**
     * 엔티티 업데이트 메서드
     * 전달된 DTO의 값이 null이 아닐 경우에만 필드를 덮어쓴다.
     */
    public void update(CafeRequestDto dto) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getAddress() != null) this.address = dto.getAddress();
        if (dto.getDetailAddress() != null) this.detailAddress = dto.getDetailAddress();
        if (dto.getPhone() != null) this.phone = dto.getPhone();
        if (dto.getOpenHours() != null) this.openHours = dto.getOpenHours();
        if (dto.getDescription() != null) this.description = dto.getDescription();
        if (dto.getCollectSchedule() != null) this.collectSchedule = dto.getCollectSchedule();
    }
}
