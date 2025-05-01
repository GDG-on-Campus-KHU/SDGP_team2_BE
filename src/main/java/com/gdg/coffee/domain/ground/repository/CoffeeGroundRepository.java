package com.gdg.coffee.domain.ground.repository;

import com.gdg.coffee.domain.ground.domain.CoffeeGround;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeGroundRepository extends JpaRepository<CoffeeGround, Long> {
    List<CoffeeGround> findAllByCafeId(Long cafeId);
}