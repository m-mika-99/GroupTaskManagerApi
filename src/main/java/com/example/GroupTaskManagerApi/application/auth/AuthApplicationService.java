package com.example.GroupTaskManagerApi.application.auth;

import com.example.GroupTaskManagerApi.domain.auth.AuthRepository;
import com.example.GroupTaskManagerApi.domain.auth.PasswordHashEncoder;
import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;
import com.example.GroupTaskManagerApi.infra.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthApplicationService {

    private final AuthRepository authRepository;
    private final PasswordHashEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthApplicationService (
            AuthRepository authRepository,
            PasswordHashEncoder encoder,
            JwtTokenProvider jwtTokenProvide
    ) {
        this.authRepository = authRepository;
        this.encoder = encoder;
        this.jwtTokenProvider = jwtTokenProvide;
    }

    public String login (String email, String rawPassword) {

        AuthUser user = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("invalid email or password"));

        user.verifyPassword(rawPassword, encoder);

        user.loginSucceeded();

        authRepository.save(user);

        return jwtTokenProvider.createToken(user.getUserId().value());
    }
}
