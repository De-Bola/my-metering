package com.enefit.metering.config;

import com.enefit.metering.service.CustomerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomerDetailsService customerDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Constructs a SecurityConfig instance.
     *
     * @param customerDetailsService the service for loading user details.
     * @param jwtAuthFilter          the JWT authentication filter.
     */
    public SecurityConfig(CustomerDetailsService customerDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.customerDetailsService = customerDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Configures the security filter chain, including CORS, CSRF, session management, and request authorization.
     *
     * @param http the HttpSecurity instance.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/customers/**").hasRole("USER")
                        .anyRequest().authenticated())
                .sessionManagement(
                        session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Creates the DAO-based authentication provider.
     *
     * @return the configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customerDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Creates a BCryptPasswordEncoder for password encoding.
     *
     * @return the PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the AuthenticationManager from the authentication configuration.
     *
     * @param authConfig the AuthenticationConfiguration instance.
     * @return the AuthenticationManager.
     * @throws Exception if unable to create the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
