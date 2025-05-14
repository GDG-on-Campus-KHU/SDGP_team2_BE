package com.gdg.coffee.domain.solution.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdg.coffee.domain.solution.domain.Solution;
import com.gdg.coffee.domain.solution.dto.request.GeminiRequest;
import com.gdg.coffee.domain.solution.dto.request.SolutionRequestDto;
import com.gdg.coffee.domain.solution.dto.response.GeminiResponse;
import com.gdg.coffee.domain.solution.dto.response.SolutionResponseDto;
import com.gdg.coffee.domain.solution.exception.SolutionErrorCode;
import com.gdg.coffee.domain.solution.exception.SolutionException;
import com.gdg.coffee.domain.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SolutionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SolutionRepository solutionRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public List<SolutionResponseDto> generateSolutions(SolutionRequestDto requestDto) {
        // 1. 기존 데이터 삭제
        solutionRepository.deleteAll();

        // 2. Gemini 프롬프트 생성
        String prompt = buildPrompt(requestDto);
        GeminiRequest geminiRequest = GeminiRequest.of(prompt);

        // 3. Gemini API 호출
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GeminiRequest> entity = new HttpEntity<>(geminiRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GEMINI_URL + apiKey,
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            // 4. Gemini 응답에서 리스트 형태 JSON 텍스트 추출
            GeminiResponse geminiResponse = objectMapper.readValue(response.getBody(), GeminiResponse.class);
            String raw = extractResponseText(geminiResponse);

            // 5. JSON → List<SolutionResponseDto>
            List<SolutionResponseDto> solutions = objectMapper.readValue(
                    raw,
                    new TypeReference<List<SolutionResponseDto>>() {}
            );

            // 6. DB 저장
            List<Solution> savedSolutions = solutions.stream()
                    .map(dto -> Solution.builder()
                            .title(dto.getTitle())
                            .description(dto.getDescription())
                            .tags(dto.getTags())
                            .difficulty(dto.getDifficulty())
                            .duration(dto.getDuration())
                            .materials(dto.getMaterials())
                            .steps(dto.getSteps())
                            .purpose(requestDto.getPurpose())
                            .beanType(requestDto.getBeanType())
                            .build())
                    .map(solutionRepository::save)
                    .toList();

            return savedSolutions.stream()
                    .map(SolutionResponseDto::fromEntity)
                    .toList();

        } catch (Exception e) {
            throw new SolutionException(SolutionErrorCode.REQUEST_FAILED);
        }
    }

    public SolutionResponseDto getSolutionById(Long id) {
        Solution solution = solutionRepository.findById(id)
                .orElseThrow(() -> new SolutionException(SolutionErrorCode.SOLUTION_NOT_FOUND));
        return SolutionResponseDto.fromEntity(solution);
    }


    private String buildPrompt(SolutionRequestDto request) {
        String purpose = request.getPurpose() == null ? "다양한 용도" : request.getPurpose();
        String beanType = request.getBeanType() == null ? "모든 원두" : request.getBeanType();
        return String.format(
                "커피 찌꺼기를 활용한 업사이클링 솔루션을 JSON 리스트 형식으로 3개 추천해줘. 응답은 영어로 해줘" +
                        "조건은 활용 목적: %s, 원두 종류: %s야.\n" +
                        "응답 형식은 다음과 같아:\n" +
                        "[\n" +
                        "  {\n" +
                        "    \"title\": \"...\",\n" +
                        "    \"description\": \"...\",\n" +
                        "    \"tags\": [\"...\"],\n" +
                        "    \"difficulty\": \"쉬움/보통/어려움\",\n" +
                        "    \"duration\": \"...\",\n" +
                        "    \"materials\": [\"...\"],\n" +
                        "    \"steps\": [\"...\"]\n" +
                        "  }\n" +
                        "]", purpose, beanType
        );
    }

    private String extractResponseText(GeminiResponse response) {
        if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {
            throw new SolutionException(SolutionErrorCode.EMPTY_RESPONSE);
        }

        String raw = response.getCandidates().get(0).getContent().getParts().get(0).getText();

        // markdown ```json 제거
        if (raw.startsWith("```json")) {
            raw = raw.replaceFirst("(?s)^```json\\s*", "");  // ```json + 줄바꿈 제거
            raw = raw.replaceFirst("(?s)```\\s*$", "");       // 맨 마지막 ```
        }

        return raw.trim(); // 불필요한 공백 제거
    }
}
