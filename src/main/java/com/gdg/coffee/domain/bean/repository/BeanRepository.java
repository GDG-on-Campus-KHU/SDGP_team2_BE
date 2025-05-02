package com.gdg.coffee.domain.bean.repository;

import com.gdg.coffee.domain.bean.domain.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BeanRepository extends JpaRepository<Bean, Long> {
    List<Bean> findAllByCafeId(Long cafeId);
    Optional<Bean> findByBeanId(Long beanId);
}