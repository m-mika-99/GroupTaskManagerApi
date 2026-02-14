package com.example.GroupTaskManagerApi.infra.repository.uroborosql.auth.entity;

import java.util.UUID;

public class AuthUserEntity {
    UUID userId;
    private UUID id;
    private String email;
    private String passwordHash;

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
}
