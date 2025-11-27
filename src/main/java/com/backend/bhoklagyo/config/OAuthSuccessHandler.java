package com.backend.bhoklagyo.config;

import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repo.UserRepository;
import com.backend.bhoklagyo.service.CustomOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public OAuthSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        var oauthUser = (org.springframework.security.oauth2.core.user.OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");


        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        new User(null, name, email,null, User.Role.CUSTOMER)
                ));

        String token = jwtUtil.generateToken(email);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(),
                java.util.Map.of(
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "token", token
                )
        );
    }
}
