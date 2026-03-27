package com.edipsico.Edipsico.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Archivos estáticos públicos
                        .requestMatchers("/", "/index.html", "/dashboard.html", "/*.css", "/*.js", "/assets/**").permitAll()
                        .requestMatchers("/manifest.json", "/sw.js", "/icons/**").permitAll()
                        // Login público
                        .requestMatchers("/api/auth/**").permitAll()
                        // Solo SUPERVISOR puede gestionar profesionales
                        .requestMatchers(HttpMethod.POST, "/api/profesionales").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.PUT, "/api/profesionales/**").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/profesionales/**").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**").hasRole("SUPERVISOR")
                        // Todo lo demás requiere estar autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}