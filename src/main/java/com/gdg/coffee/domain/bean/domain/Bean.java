package com.gdg.coffee.domain.bean.domain;

import com.gdg.coffee.domain.bean.dto.BeanRequestDto;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.common.BaseTime;
import jakarta.persistence.*;
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
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String origin;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    /**
     * 엔티티 업데이트 메서드
     * 전달된 DTO의 값이 null이 아닐 경우에만 필드를 덮어쓴다.
     */
    public void update(BeanRequestDto dto) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getOrigin() != null) this.origin = dto.getOrigin();
        if (dto.getDescription() != null) this.description = dto.getDescription();
    }
}