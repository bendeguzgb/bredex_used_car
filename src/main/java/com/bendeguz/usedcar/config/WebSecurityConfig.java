package com.bendeguz.usedcar.config;

import com.bendeguz.usedcar.model.AuthAndRefreshToken;
import com.bendeguz.usedcar.model.ErrorMessage;
import com.bendeguz.usedcar.service.AdvertiserLogoutHandler;
import com.bendeguz.usedcar.service.JwtTokenService;
import com.bendeguz.usedcar.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AdvertiserLogoutHandler logoutHandler;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenService jwtTokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().disable()
                .authorizeHttpRequests()
                .antMatchers("/auth/signup", "/auth/login", "/auth/refreshToken", "/auth/logout").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/auth/login")
                .usernameParameter("email")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .permitAll();

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .userDetailsService(userDetailsService)
                .logout(logout -> logout.logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutHandler));

        return http.build();
    }

    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            log.debug("Successful login!");

            String email = authentication.getName();

            String authToken = jwtTokenService.generateAuthToken(email);
            String refreshToken = refreshTokenService.createRefreshToken(email).getToken();

            AuthAndRefreshToken authAndRefreshToken = new AuthAndRefreshToken(authToken, refreshToken);
            String body = new ObjectMapper().writer().writeValueAsString(authAndRefreshToken);

            response.getWriter().write(body);
        };
    }

    AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            ErrorMessage errorMessage = ErrorMessage.of("Login failed! Login data with the provided email and password combination was not found!");

            String body = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .writer()
                    .writeValueAsString(errorMessage);
            response.getWriter().write(body);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        };
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
