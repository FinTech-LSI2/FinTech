package com.example.identotyservice.controller;

import com.example.identotyservice.dto.AuthRequest;
import com.example.identotyservice.entity.UserCredential;
import com.example.identotyservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControler {

    @Autowired
    private AuthService authService;
    // allow  user   to auth  before generate  him a token

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){

        return  authService.saveUser(user);
    }
    // Generate token
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        System.out.println("Attempting to authenticate user: " + authRequest.getUsername()); // Add logging
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid access");
        }
    }
    // Validate token
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return  "Token is Valid!";
    }
    @PostMapping("/refresh")
    public String refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }


}
