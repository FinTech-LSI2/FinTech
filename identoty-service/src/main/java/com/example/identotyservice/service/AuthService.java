package com.example.identotyservice.service;

import com.example.identotyservice.entity.UserCredential;
import com.example.identotyservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   @Autowired
    private JWTService jwtService;


    public String saveUser(UserCredential credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "User  added to the system";
    }

    public String  generateToken(String username){
        return jwtService.generateToken(username);

    }



    public String refreshToken(String refreshToken) {
        // Validate the refresh token
        jwtService.validateToken(refreshToken);

        // Extract the username from the refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Check if the user exists in the database
        UserCredential user = repository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a new access token
        return jwtService.generateToken(user.getName());
    }
    public void validateToken(String token){
         jwtService.validateToken(token);
    }


}
