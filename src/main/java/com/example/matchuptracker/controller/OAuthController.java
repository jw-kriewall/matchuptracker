//package com.example.matchuptracker.controller;
//
//import com.example.matchuptracker.service.oauth.GoogleAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//public class OAuthController {
//
//    @Autowired
//    private GoogleAuthService googleAuthService;
//
//    @PostMapping("/api/v1/exchange-code")
//    public Map<String, Object> exchangeCode(@RequestBody Map<String, String> payload) {
//        String authorizationCode = payload.get("code");
//        return googleAuthService.exchangeCodeForTokens(authorizationCode);
//    }
//}
