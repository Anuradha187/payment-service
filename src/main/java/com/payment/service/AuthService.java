package com.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payment.dto.request.RegisterRequest;
import com.payment.entity.User;
import com.payment.repository.UserRepository;

@Service
public class AuthService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
    private JwtService jwtService;
 
    public String login(String username, String password) {
 
        User user = userRepository.findByUsername(username)

                .orElseThrow(() -> new RuntimeException("User not found"));
 
        if (!passwordEncoder.matches(password, user.getPassword())) {

            throw new RuntimeException("Invalid password");

        }
 
        return jwtService.generateToken(user);
        //return jwtService.generateToken(username);

        

    }
    
    public void register(RegisterRequest request) {
    
    	if(userRepository.existsByUsername(request.getUsername())) {
    		throw new RuntimeException("Username already exists");
    	}
    	User user=new User();
    	user.setUsername(request.getUsername());
    	user.setPassword(passwordEncoder.encode(request.getPassword()));
    	user.setRole("ROLE_USER");
    	userRepository.save(user);
    }

}
 