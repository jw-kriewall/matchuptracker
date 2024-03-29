//package com.example.matchuptracker.service.oauth;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class GoogleAuthService {
//
//    @Value("${GOOGLE_CLIENT_ID}")
//    private String clientId;
//
//    @Value("${GOOGLE_CLIENT_SECRET}")
//    private String clientSecret;
//
//    @Value("${GOOGLE_REDIRECT_URI}")
//    private String redirectUri;
//
//    public Map<String, Object> exchangeCodeForTokens(String authorizationCode) {
//        RestTemplate restTemplate = new RestTemplate();
//        String tokenEndpoint = "https://oauth2.googleapis.com/token";
//
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("client_id", clientId);
//        requestBody.put("client_secret", clientSecret);
////        requestBody.put("code", authorizationCode);
//        requestBody.put("refresh_token", authorizationCode);
//        requestBody.put("grant_type", "refresh_token");
//
//        try {
//            @SuppressWarnings("unchecked")
//            Map<String, Object> response = restTemplate.postForObject(tokenEndpoint, requestBody, Map.class);
//            return response;
//        } catch (HttpClientErrorException e) {
//            // Log or process the error response
//            System.out.println(e.getResponseBodyAsString());
//            // Return an appropriate error response or rethrow as a custom exception
//            throw e;
//        }
//    }
//}
