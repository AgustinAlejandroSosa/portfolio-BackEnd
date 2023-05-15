package portfolio.portfolio_backend.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

  private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private int expiration;

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    return Jwts.builder().setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + expiration * 1000))
        .signWith(getSecret(secret))
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject();
  }

  public Key getSecret(String secret) {
    byte[] secretBytes = Decoders.BASE64URL.decode(secret);
    return Keys.hmacShaKeyFor(secretBytes);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Malformed token");
    } catch (UnsupportedJwtException e) {
      logger.error("UnsupportedToken");
    } catch (ExpiredJwtException e) {
      logger.error("Expired token");
    } catch (IllegalArgumentException e) {
      logger.error("Empty token");
    } catch (SignatureException e) {
      logger.error("Fail on Signature token");
    }
    return false;
  }
}
