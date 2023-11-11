package com.example.quan_ly_kho.security;

import com.example.quan_ly_kho.exception.APIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpiration;

    //Generate JWT token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expritionDate = new Date(currentDate.getTime() + jwtExpiration);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expritionDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //get username from jwt token
    public String extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    //validate Jet token
    public Boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch(MalformedJwtException ex){
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch(ExpiredJwtException ex){
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch(UnsupportedJwtException ex){
            throw new APIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch(IllegalArgumentException ex){
            throw new APIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }
}
