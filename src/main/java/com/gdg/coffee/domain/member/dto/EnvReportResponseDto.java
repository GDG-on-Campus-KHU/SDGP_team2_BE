package com.gdg.coffee.domain.member.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EnvReportResponseDto {
    private float totalCollected;
    private String carbonSaved;
    private int reportCount;
}
