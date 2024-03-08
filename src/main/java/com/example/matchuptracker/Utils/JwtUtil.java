package com.example.matchuptracker.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    private static final BCryptPasswordEncoder SECRET_KEY = new BCryptPasswordEncoder();
    public static final long EXPIRATION_TIME_IN_MS = 3600000 * 3; // 3 hour duration

    private static final String SECRET = generateHS256SecretKey(); // Replace with your secret key

    public static String generateHS256SecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256); // Initialize with 256 bits (32 bytes)
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Key getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    public static String createJwtToken(String subject) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + EXPIRATION_TIME_IN_MS);

        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static String getEmailFromJWT(Authentication authToken) {
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authToken;
        String email = jwtAuthentication.getTokenAttributes().get("email").toString();
        return email;
    }
}
