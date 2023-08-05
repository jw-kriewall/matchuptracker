package com.example.matchuptracker.controller;

import com.example.matchuptracker.Utils.JwtUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/login/success")
    public String handleGoogleLoginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        if(principal.getAuthorities().contains("read")) {
            return JwtUtil.createJwtToken(principal.toString());
        }
        else {
            return "ERROR WITH LOGIN!";
        }
    }
}
