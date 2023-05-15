package portfolio.portfolio_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import portfolio.portfolio_backend.jwt.JwtEntryPoint;
import portfolio.portfolio_backend.jwt.JwtTokenfilter;
import portfolio.portfolio_backend.service.AdminService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  AdminService adminService;

  @Autowired
  JwtEntryPoint jwtEntryPoint;

  @Autowired
  JwtTokenfilter jwtTokenfilter;

  @Autowired
  PasswordEncoder passwordEncoder;

  AuthenticationManager authenticationManager;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(adminService).passwordEncoder(passwordEncoder);
    authenticationManager = builder.build();

    http.authenticationManager(authenticationManager);

    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/login", "/banner/get", "/experience/get", "/profile/get", "/education/get", "/skill/get/hard",
            "/skill/get/**", "/skill/get/soft","/proyect/get/**", "/image/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .cors()
        .and()
        .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
        .and()
        .addFilterBefore(jwtTokenfilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}