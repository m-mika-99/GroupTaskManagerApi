package com.example.GroupTaskManagerApi.domain.auth;

import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;

import java.util.Optional;

/**
 * 認証情報永続化
 */
public interface AuthRepository {
    Optional<AuthUser> findByEmail (String email);

    void save (AuthUser user);
}
