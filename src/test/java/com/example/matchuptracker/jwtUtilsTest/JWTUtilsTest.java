package com.example.matchuptracker.jwtUtilsTest;

import com.example.matchuptracker.Utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JWTUtilsTest {
    public static Key generateHMACKey() {
        try {
            // Create a KeyGenerator instance for HMAC-SHA256
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");

            // Generate a SecretKey
            SecretKey secretKey = keyGen.generateKey();

            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while generating HMAC key", e);
        }
    }


    @Test
    public void testCreateJwtToken() {
        // Need to create an apporpriate key:
        // https://chat.openai.com/c/da0f0286-4198-4696-8f61-d0e003779da5
        String testSubject = "testUser";
        String token = JwtUtil.createJwtToken(testSubject);

        // Assuming SECRET_KEY is the same key used in JwtUtil
        Key key = generateHMACKey(); // Adjust this to access the key

        // Decode the JWT Token
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey((Key)key)
                .build()
                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        // Verify the subject
        assertEquals(testSubject, claims.getSubject());

        // Verify the issued date and expiration date
        Date now = new Date();
        assertTrue(claims.getIssuedAt().before(now));
        assertTrue(claims.getExpiration().after(now));

        // Optionally verify the time difference between now and expiration is as expected
        long expectedExpirationTimeInMillis = JwtUtil.EXPIRATION_TIME_IN_MS;
        long actualExpirationTimeInMillis = claims.getExpiration().getTime() - now.getTime();
        assertEquals(expectedExpirationTimeInMillis, actualExpirationTimeInMillis, 1000); // Allowing a 1-second margin for test execution time
    }



}
