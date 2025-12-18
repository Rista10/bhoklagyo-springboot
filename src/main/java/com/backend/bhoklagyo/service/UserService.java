package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;
import com.backend.bhoklagyo.enums.Role;
import com.backend.bhoklagyo.exception.NoSuchCustomerExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreateOrUpdateUser(Jwt jwt) {

        final String auth0Id = jwt.getSubject();
        final String email = jwt.getClaim("https://bhoklagyo.app/email");
        final String auth0Name = jwt.getClaim("name");
        final List<String> roles =
                jwt.getClaimAsStringList("https://bhoklagyo.app/roles");

        final Role newRole;
        if (roles != null && roles.contains("RESTAURANT_OWNER")) {
            newRole = Role.RESTAURANT_OWNER;
        } else if (roles != null && roles.contains("DELIVERY_PERSON")) {
            newRole = Role.DELIVERY_PERSON;
        } else {
            newRole = Role.CUSTOMER;
        }

        return userRepository.findByAuth0Id(auth0Id)
                .map(user -> {
                    // Always keep email in sync
                    user.setEmail(email);

                    // ❗ DO NOT overwrite name if user already exists
                    if (user.getName() == null || user.getName().isBlank()) {
                        user.setName(auth0Name);
                    }

                    // Role sync is OK
                    user.setRole(newRole);

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    // First login only
                    User user = new User();
                    user.setAuth0Id(auth0Id);
                    user.setEmail(email);
                    user.setName(auth0Name);
                    user.setRole(newRole);

                    return userRepository.save(user);
                });
    }

    public User getCustomer(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchCustomerExistsException(
                                "Customer not found with id " + id
                        ));
    }
}
