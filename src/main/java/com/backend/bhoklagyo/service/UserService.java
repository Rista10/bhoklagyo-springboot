package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;
import com.backend.bhoklagyo.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;
import com.backend.bhoklagyo.exception.NoSuchCustomerExistsException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    // private final Auth0ManagementService auth0ManagementService;

    public User findOrCreateOrUpdateUser(Jwt jwt) {

        final String auth0Id = jwt.getSubject();
        final String email = jwt.getClaim("https://bhoklagyo.app/email");
        final String name = jwt.getClaim("name");
        final List<String> roles = jwt.getClaimAsStringList("https://bhoklagyo.app/roles");

        final Role newRole;
        if (roles != null && roles.contains("RESTAURANT_OWNER")) {
            newRole = Role.RESTAURANT_OWNER;
        } else if (roles != null && roles.contains("DELIVERY_PERSON")) {
            newRole = Role.DELIVERY_PERSON;
        } else {
            newRole = Role.CUSTOMER;
        }

        return userRepository.findByAuth0Id(auth0Id)
                .map(u -> {
                    u.setEmail(email);
                    u.setName(name);

                    final Role oldRole = u.getRole();
                    u.setRole(newRole);

                    User saved = userRepository.save(u);
                    

                    return saved;
                })
                .orElseGet(() -> {
                    User u = new User();
                    u.setAuth0Id(auth0Id);
                    u.setEmail(email);
                    u.setName(name);
                    u.setRole(newRole);

                    User saved = userRepository.save(u);

                //     auth0ManagementService.storeDbUserId(
                //         auth0Id,
                //         saved.getId().toString()
                // );

                    return saved;
                });

    }

    public User getCustomer(UUID id) {
                return userRepository.findById(id)
                    .orElseThrow(() -> new NoSuchCustomerExistsException("Customer not found with id " + id));
            }
   
}


// package com.backend.bhoklagyo.service;

// import com.backend.bhoklagyo.model.User;
// import com.backend.bhoklagyo.repository.UserRepository;
// import com.backend.bhoklagyo.enums.Role;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.security.oauth2.jwt.Jwt;
// import com.backend.bhoklagyo.exception.NoSuchCustomerExistsException;

// import java.util.List;
// import java.util.UUID;

// @Service
// @RequiredArgsConstructor
// public class UserService {

//     private final UserRepository userRepository;

//     public User findOrUpdateUser(Jwt jwt) {

//         final String auth0Id = jwt.getSubject();
//         final String email = jwt.getClaim("https://bhoklagyo.app/email");
//         final String name = jwt.getClaim("name");
//         final List<String> roles = jwt.getClaimAsStringList("https://bhoklagyo.app/roles");

//         final Role newRole;
//         if (roles != null && roles.contains("RESTAURANT_OWNER")) {
//             newRole = Role.RESTAURANT_OWNER;
//         } else if (roles != null && roles.contains("DELIVERY_PERSON")) {
//             newRole = Role.DELIVERY_PERSON;
//         } else {
//             newRole = Role.CUSTOMER;
//         }

//         User user = userRepository.findByAuth0Id(auth0Id)
//                 .orElseThrow(() -> new NoSuchCustomerExistsException(
//                         "No user found for auth0Id: " + auth0Id
//                 ));

//         // Update fields from Auth0
//         user.setEmail(email);
//         user.setName(name);
//         user.setRole(newRole);

//         return userRepository.save(user);
//     }

//     public User getCustomer(UUID id) {
//         return userRepository.findById(id)
//                 .orElseThrow(() -> 
//                     new NoSuchCustomerExistsException("Customer not found with id " + id)
//                 );
//     }
// }
