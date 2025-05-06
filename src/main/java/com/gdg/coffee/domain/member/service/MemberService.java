package com.gdg.coffee.domain.member.service;

import java.util.List;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.cafe.exception.CafeErrorCode;
import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import com.gdg.coffee.domain.ground.domain.CoffeeGroundStatus;
import com.gdg.coffee.domain.pickup.domain.Pickup;
import com.gdg.coffee.domain.cafe.repository.CafeRepository;
import com.gdg.coffee.domain.ground.repository.CoffeeGroundRepository;
import com.gdg.coffee.domain.member.domain.MemberRole;
import com.gdg.coffee.domain.member.dto.EnvReportResponseDto;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.member.dto.MemberInfoResponseDto;
import com.gdg.coffee.domain.member.exception.MemberErrorCode;
import com.gdg.coffee.domain.member.exception.MemberException;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import com.gdg.coffee.domain.pickup.dto.PickupStatus;
import com.gdg.coffee.domain.pickup.repository.PickupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PickupRepository pickupRepository;
    private final CoffeeGroundRepository coffeeGroundRepository;
    private final CafeRepository cafeRepository;

    // CO2 변환 계수: 커피 찌꺼기 1L당 약 0.6kg CO2 절감 효과
    private static final float CO2_KG_PER_LITER_GROUND = 0.6f;

    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));  // 임시

        return new MemberInfoResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getRole(),
                member.getProfileImage()
        );
    }

    /**
     * 현재 인증된 사용자의 환경 기여도 리포트를 조회합니다.
     * 사용자의 역할(USER/CAFE)에 따라 다른 기준으로 기여도를 계산합니다.
     * @param memberId 현재 인증된 회원의 ID
     * @return 환경 기여도 정보를 담은 EnvReportResponseDto
     */
    public EnvReportResponseDto getEnvironmentalReport(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        float totalAmount = 0; // 총량
        int reportCount = 0; // 관련 항목 개수

        // User는 Pickup을 통해 수거한 커피 찌꺼기 양을 기준으로 환경 기여도 계산
        if (member.getRole() == MemberRole.USER) {
            // Completed 상태의 Pickup을 조회
            List<Pickup> completedPickups = pickupRepository.findByMemberIdAndStatus(memberId, PickupStatus.COMPLETED);

            // 총량과 수거 완료 횟수
            totalAmount = (float) completedPickups.stream().mapToDouble(Pickup::getAmount).sum();
            reportCount = completedPickups.size();
        }
        // Cafe는 CoffeeGround를 통해 배출한 커피 찌꺼기 양을 기준으로 환경 기여도 계산
        else if (member.getRole() == MemberRole.CAFE) {
            Cafe cafe = cafeRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new MemberException(CafeErrorCode.CAFE_NOT_FOUND_BY_MEMBER));
            Long cafeId = cafe.getId();

            // Completed 상태의 CoffeeGround를 조회
            List<CoffeeGround> completedGrounds = coffeeGroundRepository.findByCafeIdAndStatus(cafeId, CoffeeGroundStatus.COMPLETED);

            // 총량과 배출 완료 횟수
            totalAmount = (float) completedGrounds.stream().mapToDouble(CoffeeGround::getTotalAmount).sum();
            reportCount = completedGrounds.size();
        }

        float co2ImpactKg = totalAmount * CO2_KG_PER_LITER_GROUND;

        return EnvReportResponseDto.builder()
                .totalCollected(totalAmount)
                .carbonSaved(String.format("%.1f", co2ImpactKg)) // CO2 감소량 (소수점 1자리까지)
                .reportCount(reportCount)
                .build();
    }
}
