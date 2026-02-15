package com.example.GroupTaskManagerApi.infra.security;

import com.example.GroupTaskManagerApi.domain.auth.PasswordHashEncoder;
import com.example.GroupTaskManagerApi.domain.auth.model.PasswordHash;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHashEncoder implements PasswordHashEncoder {

    private final PasswordEncoder passwordEncoder;

    public BcryptPasswordHashEncoder (PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean matches (String raw, PasswordHash hash) {
        return passwordEncoder.matches(raw, hash.value());
    }

    @Override
    public String encode (String raw) {
        return passwordEncoder.encode(raw);
    }
}
