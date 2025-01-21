package com.example.emergencyapp.AAA;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtCore {

    @Value("${emergencyapp.app.secret}")
    private String secret;

    @Value("${emergencyapp.app.lifetime}")
    private int lifetime;

    /**
     * Генерує JWT токен для користувача.
     *
     * @param authentication об'єкт аутентифікації
     * @return згенерований JWT токен
     */
    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Витягує ім'я користувача з JWT токена.
     *
     * @param token JWT токен
     * @return ім'я користувача
     * @throws MalformedJwtException якщо токен пошкоджено
     * @throws ExpiredJwtException якщо токен протермінований
     * @throws UnsupportedJwtException якщо токен не підтримується
     * @throws IllegalArgumentException якщо токен некоректний
     */
    public String getNameFromJwt(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "JWT token is expired");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty");
        }
    }

    /**
     * Перевіряє, чи валідний токен.
     *
     * @param token JWT токен
     * @return true, якщо токен валідний, false — інакше
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
