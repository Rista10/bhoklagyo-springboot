package com.backend.bhoklagyo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import java.util.stream.Collectors;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                
                // Public endpoints (restaurant browsing)
                .requestMatchers(
                        "/restaurants/**",
                        "/menu-items/**",
                        "/reviews/**"
                ).permitAll()

                // CUSTOMER
                .requestMatchers(
                        "/orders/**",
                        "/users/me/**",
                        "/role-requests/**"
                ).authenticated()

                // // RESTAURANT OWNER
                .requestMatchers(
                    "/restaurants",
                    "/restaurants/*/menu-items",
                    "/restaurants/*/menu-items/**",
                    "/menu-items/**"
                ).hasAuthority("RESTAURANT_OWNER")


                // // DELIVERY PERSON
                // .requestMatchers(
                //         "/deliveries/**",
                //         "/my/deliveries"
                // ).hasRole("DELIVERY PERSON")

                // .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt()
        );

        return http.build();
    }

}
