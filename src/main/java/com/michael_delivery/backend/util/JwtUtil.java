package com.michael_delivery.backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    final private String SECRET_KEY = "secretsdadasdasdklasdlasldksdklfslkdfkldskfsoidfjosdfosdjgfosdjgoisdoijglkdvmndcviojgsngsdglidsnglsdgjlsdjgosdjglsdkgosdigdslkgnldsgjdsgjldskgldksgdklslkdsdslkdsldslfgdsjlsdgodsiewfwekaskdaskmlkasflk"; // Use a strong secret key in production
    final private String REFRESH_SECRET_KEY = "secretsdadasdasdklasdlasldksdklfslkdfkldskfsoidfjosdfosdjgfosdjgoisdoijglkdvmndcviojgsngsdglidsnglsdgjlsdjgosdjglsdkgosdigdslkgnldsgjdsgjldskgldksgdklslkdsdslkdsldslfgdsjlsdgodsiewfwekaskdaskmlkasflk"; // Use a strong secret key in production
    final private long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public String generateToken(long id,String username ,Set<String> permissions) {
        String token= Jwts.builder()
                .subject(username)
                .id(String.valueOf(id))
                .claims(Map.of("permissions", permissions))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(true))
                .compact();

        validateToken(token,true);

        return token;
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getPublicSigningKey(true))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        List<String> roles = claims.get("permissions", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    public String generateToken(long id,String username) {
        return Jwts.builder()
                .id(String.valueOf(id))
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(false))
                .compact();



    }
    private Key getSigningKey(Boolean isAccessToken) {
        byte[] keyBytes = isAccessToken ? SECRET_KEY.getBytes(StandardCharsets.UTF_8) : REFRESH_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getPublicSigningKey(Boolean isAccessToken){
        byte[] keyBytes = isAccessToken ? SECRET_KEY.getBytes(StandardCharsets.UTF_8) : REFRESH_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean validateToken(String token, boolean isAccessToken) {
        try {
         Jwts.parser().verifyWith(getPublicSigningKey(isAccessToken))
                 .build()
                 .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException e) {
            throw new AuthenticationServiceException("Invalid or expired token.", e);
        }
    }

    public String extractUsername(String token,boolean isAccessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(getPublicSigningKey(isAccessToken))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public String extractId(String token,boolean isAccessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(getPublicSigningKey(isAccessToken))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getId();
    }

    public Set<String> extractPermissions(String token,boolean isAccessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(getPublicSigningKey(isAccessToken))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("permissions", Set.class);
    }

    public Claims extractClaims(String token,boolean isAccessToken) {
        return Jwts.parser()
                .verifyWith(getPublicSigningKey(isAccessToken))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
