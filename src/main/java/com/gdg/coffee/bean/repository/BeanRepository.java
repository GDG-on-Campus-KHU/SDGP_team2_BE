package com.gdg.coffee.bean.repository;

import com.gdg.coffee.bean.domain.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeanRepository extends JpaRepository<Bean, Long> {
    List<Bean> findAllByCafeId(Long cafeId);
}