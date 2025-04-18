package com.gdg.coffee.member.entity;

import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true, nullable = true, length = 255)
    private String email;

    @Column(nullable = true, length = 255)
    private String password;

    @Column(nullable = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = true, length = 255)
    private String avatar;

    @Column(nullable = true, length = 50)
    private String phone;

    @Column(name = "social_type", nullable = true, length = 50)
    private String socialType;

    @Column(name = "social_id", nullable = true, length = 255)
    private String socialId;
}
