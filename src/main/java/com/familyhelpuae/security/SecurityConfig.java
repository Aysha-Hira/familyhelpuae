package com.familyhelpuae.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    .csrf(csrf -> csrf.disable()) // Disable for API (enable for forms if needed)
    .authorizeHttpRequests(auth -> auth
    // Public endpoints
    .requestMatchers("/", "/home", "/login", "/register", "/css/**",
    "/js/**").permitAll()
    .requestMatchers("/api/auth/**").permitAll() // Your auth API endpoints
    // Protected endpoints
    .requestMatchers("/user/**").hasAnyRole("MEMBER", "ADMIN")
    .requestMatchers("/admin/**").hasRole("ADMIN")
    .anyRequest().authenticated())
    .formLogin(form -> form
    .loginPage("/login")
    .usernameParameter("email")
    .passwordParameter("password")
    .defaultSuccessUrl("/profile", true)
    .permitAll())
    .logout(logout -> logout
    .logoutSuccessUrl("/login?logout")
    .permitAll());

    return http.build();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //             .csrf(csrf -> csrf.disable())
    //             .authorizeHttpRequests(auth -> auth
    //                     .anyRequest().permitAll() // Allow all requests without authentication
    //             )
    //             .formLogin(form -> form.disable()) // Disable form login
    //             .logout(logout -> logout.disable()); // Disable logout

    //     return http.build();
    // }
}