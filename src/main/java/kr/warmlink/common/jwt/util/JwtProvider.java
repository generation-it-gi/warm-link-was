package kr.warmlink.common.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import kr.warmlink.common.jwt.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private final JwtProperties jwtProperties;

    public String generateToken(String email, long expiration) {
        Claims claims = Jwts.claims();
        claims.setSubject(email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret().getBytes())
                .compact();
    }

    public String generateAccessToken(String email) {
        return generateToken(email, jwtProperties.getAccessExpiration());
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, jwtProperties.getRefreshExpiration());
    }

    public boolean validateToken(String token) {
        try {
            if (!token.startsWith(BEARER)) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(jwtProperties.getSecret().getBytes()).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}