package com.gdg.coffee.domain.pickup.repository;


import com.gdg.coffee.domain.pickup.dto.PickupCafeSummaryDto;
import com.gdg.coffee.domain.pickup.dto.PickupStatus;
import com.gdg.coffee.domain.pickup.dto.PickupUserSummaryDto;

import java.util.List;

public interface PickupRepositoryCustom {
    /**
     * cafeId에 해당하는 픽업 목록을 조회합니다.
     * status가 null이면 전체, 아니면 해당 상태만 리턴합니다.
     */
    List<PickupCafeSummaryDto> findCafePickupListByStatusDsl(Long cafeId, PickupStatus status);

    /**
     * userId에 해당하는 픽업 목록을 조회합니다.
     * status가 null이면 전체, 아니면 해당 상태만 리턴합니다.
     */
    List<PickupUserSummaryDto> findUserPickupListByStatusDsl(Long userId, PickupStatus status);
}
