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

public interface PickupRepository extends JpaRepository<Pickup, Long> {
    // ✅ 카페 기준 전체 수거 요청 조회
    @Query("SELECT p FROM Pickup p WHERE p.ground.cafe.id = :cafeId")
    List<Pickup> findByCafeId(@Param("cafeId") Long cafeId);

    // ✅ 카페 + 상태 기준 요약 조회
    @Query("SELECT new com.gdg.coffee.domain.pickup.dto.PickupCafeSummaryDto(" +
            "p.id, m.username, p.pickupDate, p.createdDate, b.name, p.amount, p.status) " +
            "FROM Pickup p " +
            "JOIN p.member m " +
            "JOIN p.ground g " +
            "JOIN g.bean b " +
            "WHERE g.cafe.id = :cafeId AND p.status = :status")
    List<PickupCafeSummaryDto> findCafePickupListByStatus(@Param("cafeId") Long cafeId,
                                                          @Param("status") PickupStatus status);

    // ✅ 사용자 + 상태 기준 요약 조회
    @Query("SELECT new com.gdg.coffee.domain.pickup.dto.PickupUserSummaryDto(" +
            "p.id, c.name, p.pickupDate, p.createdDate, b.name, p.amount, p.status) " +
            "FROM Pickup p " +
            "JOIN p.ground g " +
            "JOIN g.cafe c " +
            "JOIN g.bean b " +
            "WHERE p.member.id = :userId AND p.status = :status")
    List<PickupUserSummaryDto> findUserPickupListByStatus(@Param("userId") Long userId,
                                                          @Param("status") PickupStatus status);

    // ✅ 사용자 기준 전체 수거 요청 조회
    List<Pickup> findByMemberId(Long memberId);

}
