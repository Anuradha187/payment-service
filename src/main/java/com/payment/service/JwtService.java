
package com.payment.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
 
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.payment.entity.User;
 
@Service
public class JwtService {
 
    private final String SECRET = "my-secret-key-my-secret-key-my-secret-key";
 
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
 
    // GENERATE TOKEN
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }
 
    // EXTRACT USERNAME
    public String extractUsername(String token) {
    	
        return extractAllClaims(token).getSubject();
    }
 
    // PARSE TOKEN (NEW 0.12 STYLE)
    private Claims extractAllClaims(String token) {
 
    	System.out.println("JWT RECEIVED = [" + token +"]");
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}