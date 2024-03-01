package com.synex.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ChatGptController {

    private final String chatGPTApiKey = "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";
    private final String chatGPTApiUrl = "https://api.openai.com/v1/chat/completions";

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String question) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("question" + question);
        
       
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + chatGPTApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        
        String requestBody = "{\"model\": \"gpt-3.5-turbo-1106\", \"messages\": [{\"role\": \"user\", \"content\": " + question + "}], \"max_tokens\": 150}";        
        System.out.println("requestBody" + requestBody);
        
        ResponseEntity<String> response = restTemplate.exchange(
                chatGPTApiUrl,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class);
        System.out.println("response" + response);
        
        return extractContentFromResponse(response.getBody());
    }
    
    private String extractContentFromResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");
            return contentNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting content from response";
        }
    }
}

