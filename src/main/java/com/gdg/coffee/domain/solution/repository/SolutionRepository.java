package com.gdg.coffee.domain.solution.repository;

import com.gdg.coffee.domain.solution.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
