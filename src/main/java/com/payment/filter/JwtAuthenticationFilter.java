package com.payment.filter;
 
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.payment.entity.User;
import com.payment.repository.UserRepository;
import com.payment.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 
    @Autowired
    private JwtService jwtService;
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    protected void doFilterInternal(HttpServletRequest request,

                                    HttpServletResponse response,

                                    FilterChain filterChain)

            throws ServletException, IOException {
 
        String authHeader = request.getHeader("Authorization");
 
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;

        }
 
        String token = authHeader.substring(7).trim();
        
        System.out.println("AUTH HEADER = [" + authHeader + "]");
        System.out.println("TOKEN = [" + token + "]");
        System.out.println("TOKEN LENGTH = " + token.length());
         
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if (Character.isWhitespace(c)) {
                System.out.println("Whitespace found at index " + i +
                        " char code=" + (int)c);
            }
        }

        String username = jwtService.extractUsername(token);
 
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
 
            User user = userRepository.findByUsername(username).orElse(null);
 
            if (user != null) {

            	System.out.println("USERNAME = "+username);
            	System.out.println("ROLE = ["+ user.getRole() + "]");
            	String role=user.getRole();
                UsernamePasswordAuthenticationToken authToken =

                        new UsernamePasswordAuthenticationToken(

                                username,

                                null,

                                List.of(new SimpleGrantedAuthority(role))

                        );
 
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }
 
        filterChain.doFilter(request, response);

    }

}
  