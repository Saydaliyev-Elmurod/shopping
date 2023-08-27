package com.example.shopping.config;

import com.example.shopping.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Method;

@EnableWebSecurity
@Component
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenFilter tokenFilter;


    /* @Bean
     public AuthenticationProvider authenticationProvider() {
         // authentication
         // login,password ACTIVE,
         String password = UUID.randomUUID().toString();
         System.out.println("User Pasword mazgi: " + password);

         UserDetails user = User.builder()
                 .username("user")
                 .password("{noop}" + password)
                 .roles("USER")
                 .build();

         final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
         authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user));
         return authenticationProvider;
     }
 */
    public static String[] AUTH_WHITELIST = {"/api/v1/*/public/**",
            "/api/v1/auth/**",
            "/api/v1/auth",
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
    };

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors(cors -> cors.disable());
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("auth/**").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("auth/login").permitAll()
                .requestMatchers("admin/**").hasAnyRole("ADMIN")
                .requestMatchers("attach/open/**").permitAll()
                .requestMatchers("category/**").permitAll()
                .requestMatchers("product/**").permitAll()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .authenticated();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (MD5Util.getMd5Hash(rawPassword.toString()).equals(encodedPassword)) {
                    return true;
                }
                return false;
            }
        };
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**");
//            }
//        };
//    }


}
