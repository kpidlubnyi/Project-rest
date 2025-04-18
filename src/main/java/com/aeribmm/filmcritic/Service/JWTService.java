package com.aeribmm.filmcritic.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWTService {
    private static final String SECRET_KEY = "c7cb75cfb15d4484ff7d823e2800e919081adc904347c90f74855823db20656b5879829ff0364d331ebc43a4576fd65da30773ba847378cebadc216094f87ae9120ae9946706fc2ea7052a831d726350ab6bd47bb1554a7a4df74eadd72088ac4f49c4fa236b436778ca3aa129bfa19b135b2f8a07341e7080b17a7c71c0f35daa6d7367d043a6d46c0d202e03b00e0f4c76c05bb415047545e8ec8fb1c1303faf3ed1275d1457b4dde698618cc61005097419922a85d8e4c06f25a0467fd275f3e9e49c50008f5aff1ccc595ce7597c67fe77eb531a215339c8f448a6a44b3009ee20f8c9b0abbb80074f959cc71442876b93f29d75dd1a7f2ff78561ea4d48";
    public String extractUsername(String token) {
        return exctractClaim(token,Claims::getSubject);
    }

    public <T> T exctractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = exctractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenNonExpired(token);
    }
    public boolean isTokenNonExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return exctractClaim(token,Claims::getExpiration);
    }

    public String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Claims exctractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
