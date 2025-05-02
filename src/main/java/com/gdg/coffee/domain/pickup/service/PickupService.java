package com.gdg.coffee.domain.pickup.service;

import com.gdg.coffee.domain.cafe.repository.CafeRepository;
import com.gdg.coffee.domain.member.domain.Member;
import com.gdg.coffee.domain.member.domain.MemberRole;
import com.gdg.coffee.domain.member.exception.MemberErrorCode;
import com.gdg.coffee.domain.member.exception.MemberException;
import com.gdg.coffee.domain.member.repository.MemberRepository;
import com.gdg.coffee.domain.pickup.domain.Pickup;
import com.gdg.coffee.domain.pickup.dto.*;
import com.gdg.coffee.domain.pickup.exception.PickupErrorCode;
import com.gdg.coffee.domain.pickup.exception.PickupException;
import com.gdg.coffee.domain.pickup.repository.PickupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickupService {

    private final PickupRepository pickupRepository;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    // 1. 수거 요청 생성 (일반 사용자)
    public PickupResponseDto createPickup(Long memberId, Long groundId, PickupRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if(member.getRole() != MemberRole.USER) {
            throw  new PickupException(PickupErrorCode.UNAUTHORIZED_ACCESS);
        }

        if (requestDto.getAmount() <= 0) {
            throw new PickupException(PickupErrorCode.INVALID_INPUT);
        }

        Pickup pickup = PickupRequestDto.toEntity(requestDto, member);
        pickupRepository.save(pickup);

        return PickupResponseDto.fromEntity(pickup);
    }

    // 2. 수거 요청 수정 (사용자가 수거 희망일, 메시지 등 변경 가능)
    public void updatePickup(Long pickupId, Long memberId, PickupRequestDto requestDto) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new PickupException(PickupErrorCode.PICKUP_NOT_FOUND));

        if (!pickup.getMember().getId().equals(memberId)) {
            throw new PickupException(PickupErrorCode.UNAUTHORIZED_ACCESS);
        }

        pickup.update(
                requestDto.getPickupDate(),
                requestDto.getMessage(),
                requestDto.getAmount()
        );
    }

    // 3. 수거 요청 상태 변경 (카페 측에서 상태 수락/거절/완료 등)
    public void updatePickupStatus(Long pickupId, PickupStatus status) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new PickupException(PickupErrorCode.PICKUP_NOT_FOUND));

        pickup.updatePickupStatus(status);
    }

    // 4. 수거 요청 상세 조회 (카페/사용자 공통)
    public PickupResponseDto getPickupDetail(Long pickupId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new PickupException(PickupErrorCode.PICKUP_NOT_FOUND));

        return PickupResponseDto.fromEntity(pickup);
    }

    // 5. 수거 요청 목록 조회
    // (1) 카페가 등록한 찌꺼기 기준 전체 요청 조회
    public List<PickupCafeSummaryDto> getCafePickupList(Long cafeId) {
        List<Pickup> pickupList = pickupRepository.findByCafeId(cafeId);
        return pickupList.stream()
                .map(PickupCafeSummaryDto::fromEntity)
                .toList();
    }

    // (2) 카페 + 상태 필터링 조회
    public List<PickupCafeSummaryDto> getCafePickupListByStatus(Long cafeId, PickupStatus status){
        return pickupRepository.findCafePickupListByStatus(cafeId, status);
    }

    // (3) 사용자 기준 상태별 요청 조회
    public List<PickupUserSummaryDto> getUserPickupListByStatus(Long userId, PickupStatus status) {
        return pickupRepository.findUserPickupListByStatus(userId, status);
    }

    public List<PickupUserSummaryDto> getUserPickupList(Long userId){
        List<Pickup> pickupList = pickupRepository.findByMemberId(userId);
        return pickupList.stream()
                .map(PickupUserSummaryDto::fromEntity)
                .toList();
    }

    // 6. 수거 요청 삭제 (일반 사용자)
    public void deletePickup(Long pickupId, Long memberId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new PickupException(PickupErrorCode.PICKUP_NOT_FOUND));

        if (pickup.getStatus() == PickupStatus.COMPLETED){
            throw new PickupException(PickupErrorCode.ALREADY_COMPLETED);
        }

        if (pickup.getStatus() == PickupStatus.ACCEPTED){
            throw new PickupException(PickupErrorCode.CANNOT_CANCEL);
        }

        if (!pickup.getMember().getId().equals(memberId)) {
            throw new PickupException(PickupErrorCode.UNAUTHORIZED_ACCESS);
        }

        pickupRepository.delete(pickup);
    }
}
