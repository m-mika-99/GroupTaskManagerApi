package com.example.GroupTaskManagerApi.infra.repository.uroborosql.auth.entity;

import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthUserEntity {
    UUID userId;
    private UUID id;
    private String email;
    private String passwordHash;
    private LocalDateTime lastLoginAt;
    private LocalDateTime lastPwChangedAt;

    public LocalDateTime getLastLoginAt () {
        return lastLoginAt;
    }

    public void setLastLoginAt (LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getLastPwChangedAt () {
        return lastPwChangedAt;
    }

    public void setLastPwChangedAt (LocalDateTime lastPwChangedAt) {
        this.lastPwChangedAt = lastPwChangedAt;
    }

    public UUID getUserId () {
        return userId;
    }

    public void setUserId (UUID userId) {
        this.userId = userId;
    }

    public UUID getId () {
        return id;
    }

    public void setId (UUID id) {
        this.id = id;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPasswordHash () {
        return passwordHash;
    }

    public void setPasswordHash (String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public AuthUser toDomain () {
        return AuthUser.reconstruct(
                getId().toString(),
                getEmail(),
                getPasswordHash(),
                getLastLoginAt(),
                getLastPwChangedAt(),
                getUserId().toString()
        );
    }
}
