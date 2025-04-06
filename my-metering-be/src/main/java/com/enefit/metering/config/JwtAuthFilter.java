package com.enefit.metering.config;

import com.enefit.metering.service.CustomerDetailsService;
import com.enefit.metering.utils.JwtUtil;
import com.enefit.metering.utils.MaskingUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that intercepts HTTP requests to validate JWT tokens and set the authentication context.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final CustomerDetailsService customerDetailsService;
    private final JwtUtil jwtUtil;

    /**
     * Constructs a JwtAuthFilter with the required dependencies.
     *
     * @param customerDetailsService the service to load customer details.
     * @param jwtUtil                the utility for JWT operations.
     */
    public JwtAuthFilter(CustomerDetailsService customerDetailsService, JwtUtil jwtUtil) {
        this.customerDetailsService = customerDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Extracts and validates the JWT token from the Authorization header. If the token is valid,
     * the user's authentication is set in the SecurityContext.
     *
     * @param request     the incoming HTTP request.
     * @param response    the outgoing HTTP response.
     * @param filterChain the filter chain to pass the request and response to the next filter.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String token = authorizationHeader.substring(7);
            final String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities()
                                );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.info("Authenticated user: {}", MaskingUtil.mask(username));
                    } else {
                        logger.warn("Invalid JWT token for user: {}", MaskingUtil.mask(username));
                    }
                } catch (Exception ex) {
                    logger.error("Error during authentication for token: {}", token, ex);
                }
            } else if (username == null) {
                logger.warn("JWT token does not contain a username");
            }
        } else {
            logger.debug("No Bearer token found in the request header");
        }

        filterChain.doFilter(request, response);
    }
}
