package com.enefit.metering.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Utility class for generating and validating JSON Web Tokens (JWT).
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${token.validity}")
    private long TOKEN_VALIDITY;

    @Value("${secret}")
    private String jwtSecret;

    private SecretKey secretKey;

    /**
     * Initializes the secret key using the configured JWT secret.
     */
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Retrieves the token validity period.
     *
     * @return the token validity in milliseconds.
     */
    public long getTOKEN_VALIDITY() {
        return TOKEN_VALIDITY;
    }

    /**
     * Generates a JWT token using the provided user details.
     *
     * @param userDetails the authenticated user's details.
     * @return a JWT token as a String.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Generates a JWT refresh token with additional claims.
     *
     * @param claims      a map of claims to be added to the token.
     * @param userDetails the authenticated user's details.
     * @return a JWT refresh token as a String.
     */
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Validates a given JWT token against the provided user details.
     *
     * @param token       the JWT token.
     * @param userDetails the user details to validate against.
     * @return true if the token is valid; false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        if (username == null) {
            return false;
        }
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks whether the token is expired.
     *
     * @param token the JWT token.
     * @return true if the token is expired; false otherwise.
     */
    private boolean isTokenExpired(String token) {
        try {
            Date expiration = extractClaim(token, Claims::getExpiration);
            if (expiration == null) {
                logger.error("Expiration claim not found in token");
                return true;
            }
            return expiration.before(new Date());
        } catch (JwtException e) {
            logger.error("Error extracting expiration from token: {}", e.getMessage(), e);
            return true;
        }
    }

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using a resolver function.
     *
     * @param token          the JWT token.
     * @param claimsResolver a function to resolve the desired claim.
     * @param <T>            the type of the claim.
     * @return the resolved claim, or null if extraction fails.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            logger.error("Token is expired: {}", e.getMessage(), e);
        } catch (MalformedJwtException e) {
            logger.error("Token is malformed: {}", e.getMessage(), e);
        } catch (UnsupportedJwtException e) {
            logger.error("Token is unsupported: {}", e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error("Token argument is illegal or inappropriate: {}", e.getMessage(), e);
        } catch (JwtException e) {
            logger.error("JWT error: {}", e.getMessage(), e);
        }
        return null;
    }
}
