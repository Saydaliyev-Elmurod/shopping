package com.example.shopping.config;
import com.example.shopping.util.JwtDto;
import com.example.shopping.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
    public class TokenFilter extends OncePerRequestFilter {
        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            AntPathMatcher pathMatcher = new AntPathMatcher();
            return Arrays.asList(SecurityConfig.AUTH_WHITELIST).stream()
                    .anyMatch(p -> {
                        boolean match = pathMatcher.match(p, request.getServletPath());
                        return match;
                    });
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {
            System.out.println("before get header");
            final String authHeader = request.getHeader("Authorization");
            System.out.println("after get header");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("header null");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Message", "Token Not Found.");
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(7);
            JwtDto jwtDto ;
            try {
                jwtDto = JwtUtil.decode(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtDto.getMail());

                UsernamePasswordAuthenticationToken
                        authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Message", "Token Not Valid");

            }
        }
    }
