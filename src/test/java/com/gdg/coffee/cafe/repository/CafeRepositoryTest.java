package com.gdg.coffee.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import com.gdg.coffee.cafe.entity.Cafe;
import com.gdg.coffee.member.entity.Member;
import com.gdg.coffee.member.entity.Role;
import com.gdg.coffee.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CafeRepositoryTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Cafe 엔티티 저장 후 owner(Member) 매핑이 정상 동작해야 한다")
    void saveAndFindById_withOwner() {
        // Member 객체 생성
        Member member = Member.builder()
                .email("owner@example.com")
                .password("securePwd")
                .name("카페주인")
                .role(Role.CAFE)
                .avatar(null)
                .phone("010-0000-0000")
                .socialType(null)
                .socialId(null)
                .build();
        Member savedMember = memberRepository.save(member);

        // Cafe 객체 생성
        Cafe cafe = Cafe.builder()
                .memberId(savedMember)
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

        // 저장 및 조회
        Cafe savedCafe = cafeRepository.save(cafe);
        Optional<Cafe> foundOpt = cafeRepository.findById(savedCafe.getCafeId());

        // 조회 결과 검증
        assertThat(foundOpt).isPresent();
        Cafe found = foundOpt.get();
        assertThat(found.getName()).isEqualTo("테스트 카페");
        // 연관관계로 저장된 MemberId(Member) 검증
        assertThat(found.getMemberId().getMemberId()).isEqualTo(savedMember.getMemberId());
        assertThat(found.getMemberId().getName()).isEqualTo("카페주인");
    }
}
