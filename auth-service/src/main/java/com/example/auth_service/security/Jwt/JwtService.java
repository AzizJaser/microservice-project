package com.example.auth_service.security.Jwt;

import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    @Autowired
    private UserRepository userRepository;


    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        // Convert roles to a list of strings (Spring expects "authorities" claim)
        claims.put("authorities", user.getRoles().stream()
                .map(role -> "ROLE_" + role.name()) // ensure prefix is "ROLE_"
                .collect(Collectors.toList()));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusHours(1);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public LocalDateTime extractExpiration(String token){
        Date date = extractClaim(token, Claims::getExpiration);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public boolean isValidToken(String token){
        String email = extractEmail(token);
        LocalDateTime expiry = extractExpiration(token);

        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email does not exist!");
        }

        if (expiry.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired!");
        }

        return true;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private Key key(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
