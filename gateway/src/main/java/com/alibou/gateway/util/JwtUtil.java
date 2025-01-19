package com.alibou.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String SECRET = "186125f2e960d5fdee9dbc9d5e15efff47010f75ef5a0a91a75252738d50a5000bea2e7493db00493ef364df7f770ff90ef68fde2cf626d884d2b3166eeba109bb089cb12f61f3f6330d2e1e43228ebf167db5401cad4ae72bb605de516c217406f87105d10b68d4458f6eec0c8a8d2915e147c30fd4d657c21b8df32e1922442ffb66ebd51c34a3cd2db94dbf43ec75374ec94cdb6721f9b4bd2c019d91f9e962bae52eb772734cfe5cbf45a7636a50a3b30be39bc2dbb98f5b6516483aa5d4176b4daed599bcd34605497f50772fb245acea996ffe199a417dcdee229a949ebeffa977c6a19675bbdcad9cc9defa1fb179e2ef6ed99cdc146cbacdca851200";

    public void validateToken(final String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}