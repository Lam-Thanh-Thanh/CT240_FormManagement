package backend.form_management.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "X2p1bXVzIHRlc3Qgc2VjcmV0IGtleSBnZW5lcmF0ZWQ=";
    private static final long EXPIRATION_TIME = 86400000; // 1 ngày

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // Trả về kiểu SecretKey
    }
    //get role
    public String generateToken(String userId, Set<String> roles) {
        return Jwts.builder()
                .subject(userId)
                .claim("role", roles) // 👉 Thêm role vào token
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build(); // Đúng kiểu SecretKey
        return parser.parseSignedClaims(token).getPayload().getSubject(); // Đổi parseClaimsJws thành parseSignedClaims
    }
    public Set<String> extractRoles(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();
        
        // Chuyển đổi danh sách roles từ List<Object> sang Set<String>
        List<?> rawRoles = claims.get("roles", List.class);
        Set<String> roles = new HashSet<>();
        for (Object role : rawRoles) {
            roles.add(role.toString());
        }
        
        return roles;
    }
    

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build();
            Jws<Claims> claimsJws = parser.parseSignedClaims(token); // Sử dụng parseSignedClaims thay vì parseClaimsJws
            String username = claimsJws.getPayload().getSubject();
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build();
        return parser.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}



