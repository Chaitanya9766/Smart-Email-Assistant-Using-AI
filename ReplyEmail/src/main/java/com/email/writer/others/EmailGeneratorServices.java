package com.email.writer.others;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
@Service
public class EmailGeneratorServices {
//	String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=AIzaSyAhVVrQYqcT5A_TW5ZrGPZAKnSQmWL5Fd0\n";
	 
    private final WebClient webClient;

    public EmailGeneratorServices(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String generatorEmailReply(EmailRequest er) {

        String prompt = buildPrompt(er);

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );

        String response = webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest er) {
        StringBuilder sb = new StringBuilder();
        sb.append("Generate a professional email reply. Do not include a subject line.");

        if (er.getTone() != null && !er.getTone().isBlank()) {
            sb.append(" Use a ").append(er.getTone()).append(" tone.");
        }

        sb.append("\n\nOriginal Email:\n").append(er.getEmailContent());
        return sb.toString();
    }
}
