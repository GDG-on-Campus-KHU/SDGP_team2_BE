package com.gdg.coffee.bean.domain;

import com.gdg.coffee.bean.dto.BeanRequestDto;
import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

/**
 * 원두(Bean) 엔티티 클래스
 */
@Entity
@Table(name = "bean")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bean extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bean_id")
    private Long beanId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String origin;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Cafe FK
    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    /**
     * 엔티티 업데이트 메서드
     * 전달된 DTO의 값이 null이 아닐 경우에만 필드를 덮어쓴다.
     */
    public void update(BeanRequestDto dto) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getOrigin() != null) this.origin = dto.getOrigin();
        if (dto.getDescription() != null) this.description = dto.getDescription();
        if (dto.getCafeId() != null) this.cafeId = dto.getCafeId();
    }
}