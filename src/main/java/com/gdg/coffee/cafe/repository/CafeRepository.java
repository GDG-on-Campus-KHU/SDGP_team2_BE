package com.gdg.coffee.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gdg.coffee.cafe.domain.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    // 필요 시 커스텀 조회 메서드 추가 가능
}
