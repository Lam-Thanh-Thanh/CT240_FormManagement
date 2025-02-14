package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "X2p1bXVzIHRlc3Qgc2VjcmV0IGtleSBnZW5lcmF0ZWQ=";
    private static final long EXPIRATION_TIME = 86400000; // 1 ngày

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // Trả về kiểu SecretKey
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), Jwts.SIG.HS256) // Dùng đúng kiểu SecretKey + HS256
                .compact();
    }

    public String extractUsername(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build(); // Đúng kiểu SecretKey
        return parser.parseSignedClaims(token).getPayload().getSubject(); // Đổi parseClaimsJws thành parseSignedClaims
    }

    public boolean validateToken(String token, String username) {
        try {
            JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build();
            Jws<Claims> claimsJws = parser.parseSignedClaims(token); // Sử dụng parseSignedClaims thay vì parseClaimsJws
            return claimsJws.getPayload().getSubject().equals(username) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build();
        return parser.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}
