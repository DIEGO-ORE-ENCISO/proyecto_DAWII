package com.proyecto.daw.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("c3Ryb25nMjAyMzRzZWNyZXRrZXkwNDFzdHJvbmcxMjM0NTY3OA==")
    private String SECRET_KEY;


    public String getToken(UserDetails user){ return getToken(new HashMap<>(),user);}


    public String getToken(Map<String,Object> extraClaims, UserDetails user){
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000*60*24))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    public Claims getAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSecretKey()).build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public <T> T getClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String getUsernameFromToken(String token){ return getClaim(token,Claims::getSubject); }

    public boolean isTokenValid(String token, UserDetails user){
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Date getExpirationDate(String token){ return getClaim(token,Claims::getExpiration); }

    private boolean isTokenExpired(String token){ return  getExpirationDate(token).before(new Date()); }




}
