package com.eprana.backend.service;

import com.eprana.backend.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    //Secret key
    private static final String SECRET_KEY="mysecretkeymysecretkeymysecretkey12345";
    //Convert secret string into secure key
    private final Key key=
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    //Generate token
    public String generateToken(String email, Role role)
    {
        Map<String, Object> claims=new HashMap<>();
        claims.put("role",role);
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())

                //Token Expiry(1day)
                .setExpiration(
                        new Date(System.currentTimeMillis()
                        +1000*60*60*24)
                )
                .signWith(
                       key,SignatureAlgorithm.HS256
                )
                .compact();
    }
    //Extract email from token
    public String extractEmail(String token)
    {
        return getClaims(token)
                .getBody()
                .getSubject();
    }
    //extract role
    public String extractRole(String token)
    {
        return getClaims(token)
                .getBody()
                .get("role").toString();
    }
    //token validating
    public boolean isValidToken(String token)
    {
        try{
            getClaims(token);
            return  true;
        } catch (Exception e) {
            return  false;
        }
    }
    //PArse token
    private Jws<Claims> getClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
