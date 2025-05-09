package com.gdg.coffee.domain.bean.dto;

import com.gdg.coffee.domain.bean.domain.Bean;
import com.gdg.coffee.domain.cafe.domain.Cafe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 원두(Bean) 생성 및 수정 요청 DTO
 */
@Getter
@NoArgsConstructor
public class BeanRequestDto {

    @NotBlank @Size(max = 50)
    private String name;

    @NotBlank @Size(max = 50)
    private String origin;

    private String description;

    /**
     * DTO -> Entity 변환
     */
    public Bean toEntity(Cafe cafe) {
        return Bean.builder()
                .name(name)
                .origin(origin)
                .description(description)
                .cafe(cafe)
                .build();
    }
}
