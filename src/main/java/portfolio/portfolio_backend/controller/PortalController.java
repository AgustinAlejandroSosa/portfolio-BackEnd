package portfolio.portfolio_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portfolio.portfolio_backend.dto.JwtDto;
import portfolio.portfolio_backend.entity.Admin;
import portfolio.portfolio_backend.jwt.JwtProvider;

@RequestMapping("/")
@RestController
public class PortalController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtProvider jwtProvider;

  
  @PostMapping("/login")
  public ResponseEntity<JwtDto> login(@RequestBody Admin admin) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    JwtDto jwtDto = new JwtDto(userDetails.getUsername(), jwt, userDetails.getAuthorities());
    return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
  }

}
