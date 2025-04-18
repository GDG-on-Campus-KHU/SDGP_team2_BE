package com.gdg.coffee.cafe.domain;

import com.gdg.coffee.domain.common.BaseTime;
import com.gdg.coffee.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cafe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cafe extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long cafeId;

    // member fk
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

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
}
