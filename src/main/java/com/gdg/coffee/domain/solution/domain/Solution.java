package com.gdg.coffee.domain.solution.domain;

import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solution extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    private List<String> tags;

    private String difficulty;

    private String duration;

    @ElementCollection
    private List<String> materials;

    @ElementCollection
    private List<String> steps;

    private String purpose;

    private String beanType;
}
