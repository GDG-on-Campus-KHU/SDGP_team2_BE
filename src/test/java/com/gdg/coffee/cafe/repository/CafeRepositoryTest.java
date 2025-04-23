package com.gdg.coffee.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import com.gdg.coffee.cafe.domain.Cafe;
import com.gdg.coffee.member.domain.Member;
import com.gdg.coffee.member.domain.Role;
import com.gdg.coffee.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//for test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")  // application‑test.yaml 적용
@AutoConfigureTestDatabase(replace = NONE)  // H2 설정 유지, Spring이 임의 교체 못 하게
class CafeRepositoryTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Cafe 저장 후 memberId 매핑 및 값 검증")
    void saveAndFindById_withOwner() {
        // 1) Member 저장
        Member member = Member.builder()
                .email("owner@example.com")
                .password("securePwd")
                .name("카페주인")
                .role(Role.CAFE)
                .build();
        Member savedMember = memberRepository.save(member);

        // 2) Cafe 저장
        Cafe cafe = Cafe.builder()
                .memberId(savedMember.getMemberId())
                .name("테스트 카페")
                .address("서울시 강남구")
                .detailAddress("2층")
                .phone("02-123-4567")
                .latitude(37.123456F)
                .longitude(127.123456F)
                .openHours("09:00 ~ 22:00")
                .description("연관관계 테스트 카페")
                .collectSchedule("매일 10~12시")
                .build();
        Cafe savedCafe = cafeRepository.save(cafe);

        // 3) 조회
        Cafe found = cafeRepository.findById(savedCafe.getCafeId())
                .orElseThrow();

        // 4) 검증
        assertThat(found.getName()).isEqualTo("테스트 카페");                 // 카페 이름
        assertThat(found.getMemberId()).isEqualTo(savedMember.getMemberId()); // FK 값
        assertThat(savedMember.getName()).isEqualTo("카페주인");             // 회원 이름
    }

}
