package com.gdg.coffee.domain.solution.dto.response;

import com.gdg.coffee.domain.solution.domain.Solution;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SolutionResponseDto {
    private Long id;
    private String title;               // 예: "커피 방향제 만들기"
    private String description;         // 간단 소개
    private List<String> tags;          // 예: ["방향제", "탈취", "인테리어"]
    private String difficulty;          // 예: "쉬움"
    private String duration;            // 예: "15분"
    private List<String> materials;     // 예: ["밀린 커피 찌꺼기 1컵", "베이킹 소다 1/2컵", ...]
    private List<String> steps;         // 예: ["커피 찌꺼기를 완전히 말려...", "베이킹소다와 섞는다", ...]

    public static SolutionResponseDto fromEntity(Solution solution){
        return SolutionResponseDto.builder()
                .id(solution.getId())
                .title(solution.getTitle())
                .description(solution.getDescription())
                .tags(solution.getTags())
                .difficulty(solution.getDifficulty())
                .duration(solution.getDuration())
                .materials(solution.getMaterials())
                .steps(solution.getSteps())
                .build();
    }
}
