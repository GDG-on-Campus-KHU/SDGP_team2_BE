package com.gdg.coffee.domain.cafe.repository;

import com.gdg.coffee.domain.cafe.domain.Cafe;
import java.util.List;

public interface CafeRepositoryCustom {
     // 위도/경도 좌표 기준, 5km 내부에 존재하는 카페 목록 조회
    List<Cafe> findCafesNear(double latitude, double longitude);
}