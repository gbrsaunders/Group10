package com.hackathon.hertrack.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AIController {

    private final RestTemplate restTemplate = new RestTemplate();
    // Gets the api key from teh application properties
    @Value("${spring.ai.huggingface.chat.api-key}")
    private String apiKey;
    // To get the response
    @GetMapping("/ai/generate")
    public String generate(@RequestParam String message) {
        String url = "https://router.huggingface.co/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Currently using the llama model
        String body = "{\"model\": \"meta-llama/Llama-3.1-8B-Instruct\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a menstrual health assistant. Answer questions about menstrual health clearly and helpfully.Keep it under 100 words. Do not over explain, but explain thoroughly.\"}, {\"role\": \"user\", \"content\": \"" + message + "\"}]}";

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        // To get the string from the json
        org.json.JSONObject json = new org.json.JSONObject(response.getBody());
        return json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}