package com.sortech.sortech.config;

import com.sortech.sortech.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/notice/write",
                                "/notice/*/edit",
                                "/recruitment/write",
                                "/recruitment/*/edit"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/",
                                "/login",
                                "/signup",
                                "/notice",
                                "/notice/*",
                                "/recruitment",
                                "/recruitment/*",
                                "/free-board",
                                "/free-board/*",
                                "/assets/**",
                                "/api/signup",
                                "/api/login"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/notices/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/notices")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/notices/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/notices/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/recruitments/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/recruitments")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/recruitments/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/recruitments/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/admin")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {

                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(401);
                            } else {
                                response.sendRedirect("/login");
                            }
                        })
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}