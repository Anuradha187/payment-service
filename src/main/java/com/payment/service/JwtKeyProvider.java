package com.payment.service;

import java.security.Key;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import jakarta.annotation.PostConstruct;

@Component
public class JwtKeyProvider {

	private Key key;
	
	@PostConstruct
	public void init() {
		
		// Generate secure HS256 key once at startup
		SecretKey secretKey=Jwts.SIG.HS256.key().build();
		this.key=secretKey;
		
		
		System.out.println(" JWT SECRET (BASE64): "+ Encoders.BASE64.encode(secretKey.getEncoded()));
		
	}
	
	public Key getKey() {
		return key;
	}
	
}
