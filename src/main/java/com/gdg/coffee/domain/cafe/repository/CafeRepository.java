package com.gdg.coffee.domain.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gdg.coffee.domain.cafe.domain.Cafe;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByMemberId(Long memberId);
}
