package portfolio.portfolio_backend.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtDto {

  public JwtDto(String username, String jwt, Collection<? extends GrantedAuthority> authorities2) {
  }

  private String username;

  private String token;

  private String bearer = "Bearer";

  private Collection<? extends GrantedAuthority> authorities;
  
}
