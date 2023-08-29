package com.example.matchuptracker.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final BCryptPasswordEncoder SECRET_KEY = new BCryptPasswordEncoder();
    public static final long EXPIRATION_TIME_IN_MS = 3600000;

    public static String createJwtToken(String subject) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + EXPIRATION_TIME_IN_MS);

        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith((Key) SECRET_KEY, SignatureAlgorithm.HS256 )
                .compact();

        return token;
    }
}
