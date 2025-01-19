package com.example.identotyservice.service;

import com.example.identotyservice.entity.UserCredential;
import com.example.identotyservice.repository.UserCredentialRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class JWTService {

    private final String SECRET = "186125f2e960d5fdee9dbc9d5e15efff47010f75ef5a0a91a75252738d50a5000bea2e7493db00493ef364df7f770ff90ef68fde2cf626d884d2b3166eeba109bb089cb12f61f3f6330d2e1e43228ebf167db5401cad4ae72bb605de516c217406f87105d10b68d4458f6eec0c8a8d2915e147c30fd4d657c21b8df32e1922442ffb66ebd51c34a3cd2db94dbf43ec75374ec94cdb6721f9b4bd2c019d91f9e962bae52eb772734cfe5cbf45a7636a50a3b30be39bc2dbb98f5b6516483aa5d4176b4daed599bcd34605497f50772fb245acea996ffe199a417dcdee229a949ebeffa977c6a19675bbdcad9cc9defa1fb179e2ef6ed99cdc146cbacdca851200";

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    public String generateToken(String username) {
        // Fetch the user from the database
        Optional<UserCredential> userCredential = userCredentialRepository.findByName(username);
        if (userCredential.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // Remove the "ROLE_" prefix from the role
        String role = userCredential.get().getRole().replace("ROLE_", "");

        // Create a UserDetails object
        UserDetails userDetails = User.builder()
                .username(userCredential.get().getName())
                .password(userCredential.get().getPassword())
                .roles(role) // Set the role without the "ROLE_" prefix
                .build();

        // Generate the token
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority()); // Add role to the token
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 days
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
    // Extract the username from the token
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject(); // The subject is the username
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}