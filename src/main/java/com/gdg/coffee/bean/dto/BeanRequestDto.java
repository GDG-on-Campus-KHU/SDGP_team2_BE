package com.gdg.coffee.bean.dto;

import com.gdg.coffee.bean.domain.Bean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 원두(Bean) 생성 및 수정 요청 DTO
 */
@Getter
@NoArgsConstructor
public class BeanRequestDto {

    @NotBlank(message = "원두 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "원산지는 필수입니다.")
    private String origin;

    private String description;

    @NotNull(message = "카페 ID는 필수입니다.")
    private Long cafeId;

    /**
     * DTO -> Entity 변환
     */
    public Bean toEntity() {
        return Bean.builder()
                .name(name)
                .origin(origin)
                .description(description)
                .cafeId(cafeId)
                .build();
    }
}
