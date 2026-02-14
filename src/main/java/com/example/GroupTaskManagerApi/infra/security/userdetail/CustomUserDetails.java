package com.example.GroupTaskManagerApi.infra.security.userdetail;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final UUID userId;

    public CustomUserDetails (
            UUID userId
    ) {
        this.userId = userId;
    }

    public UUID getUserId () {
        return this.userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return List.of();
    }

    @Override
    public @Nullable String getPassword () {
        return "";
    }

    @Override
    public String getUsername () {
        return "";
    }
}
