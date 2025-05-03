package com.gdg.coffee.domain.pickup.repository;

import com.gdg.coffee.domain.pickup.domain.Pickup;
import com.gdg.coffee.domain.pickup.dto.PickupCafeSummaryDto;
import com.gdg.coffee.domain.pickup.dto.PickupStatus;
import com.gdg.coffee.domain.pickup.dto.PickupUserSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PickupRepository extends JpaRepository<Pickup, Long>, PickupRepositoryCustom {
    // 카페 기준 전체 수거 요청 조회
    List<Pickup> findByGroundCafeId(Long cafeId);
    // 사용자 기준 전체 수거 요청 조회
    List<Pickup> findByMemberId(Long memberId);

}
