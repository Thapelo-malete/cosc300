package com.cosc300.suicidal.security;

import com.cosc300.suicidal.model.enums.UserRole;
import com.cosc300.suicidal.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                    "/resources/static/**","/styles/**", 
                    "/scripts/**", "/images/**"
                ).permitAll()
                .requestMatchers(
                        "/",
                        "/user/password/reset/confirm/**",
                        "/user/password/reset/**",
                        "/user/token/resend/**",
                        "/user/confirm/**",
                        "/user/password/update/**",
                        "/user/password/update**",
                        "/user/reset/password/**",
                        "/user/**"
                ).anonymous()
                .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.toString())
                .requestMatchers("/app/**").hasRole(UserRole.USER.toString())
                .requestMatchers("/psychologist/**").hasRole(UserRole.PSYCHOLOGIST.toString())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .successForwardUrl("/app")
                .and()
                .oauth2Login();

        return httpSecurity.build();
    }

    private final MyUserDetailsService myUserDetailsService;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }
}
