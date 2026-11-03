package io.shopkart.shopkart.auth.service;

import io.shopkart.shopkart.auth.dto.RegisterRequestDto;
import io.shopkart.shopkart.auth.dto.RegisterResponseDto;
import io.shopkart.shopkart.common.exception.DuplicateResourceException;
import io.shopkart.shopkart.common.exception.DuplicateUserException;
import io.shopkart.shopkart.user.Entity.Role;
import io.shopkart.shopkart.user.Entity.User;
import io.shopkart.shopkart.user.enums.RoleName;
import io.shopkart.shopkart.user.repository.RoleRepository;
import io.shopkart.shopkart.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {
        String email = request.email();
        String phone = request.phone();

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateUserException("Email already registered");
        }

        if (phone != null && userRepository.existsByPhone(phone)) {
            throw new DuplicateUserException("Phone number already registered");
        }

        Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("ROLE_CUSTOMER is missing"));

        User user = new User();
        user.setName(request.name());
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.addRole(customerRole);
        user = userRepository.save(user);

        return RegisterResponseDto.from(user, "User created successfully");
    }

}
