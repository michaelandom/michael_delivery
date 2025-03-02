package com.michael_delivery.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtil {

    private String SECRET_KEY = "secretsdadasdasdklasdlasldksdklfslkdfkldskfsoidfjosdfosdjgfosdjgoisdoijglkdvmndcviojgsngsdglidsnglsdgjlsdjgosdjglsdkgosdigdslkgnldsgjdsgjldskgldksgdklslkdsdslkdsldslfgdsjlsdgodsiewfwekaskdaskmlkasflk"; // Use a strong secret key in production
    private long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public String generateToken(String username, Set<String> permissions) {
        return Jwts.builder()
                .setSubject(username)
                .setClaims(Map.of("permissions", permissions))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String generateToken(long id) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
// \           Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        return "";
    }
}
