package com.example.GroupTaskManagerApi.infra.repository.uroborosql.user.entity;

import com.example.GroupTaskManagerApi.domain.user.model.User;

import java.util.UUID;

public class UserEntity {

    private UUID id;
    private String displayName;

    public UUID getId () {
        return id;
    }

    public void setId (UUID id) {
        this.id = id;
    }

    public String getDisplayName () {
        return displayName;
    }

    public void setDisplayName (String displayName) {
        this.displayName = displayName;
    }

    public User toDomain () {
        return User.reconstruct(
                id.toString(),
                displayName
        );
    }
}
