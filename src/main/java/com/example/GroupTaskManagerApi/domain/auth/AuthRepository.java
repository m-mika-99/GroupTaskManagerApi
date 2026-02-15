package com.example.GroupTaskManagerApi.domain.auth;

import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;
import com.example.GroupTaskManagerApi.domain.group.model.Group;
import com.example.GroupTaskManagerApi.domain.user.model.User;

import java.util.Optional;

/**
 * 認証情報永続化
 */
public interface AuthRepository {
    Optional<AuthUser> findByEmail (String email);

    void save (AuthUser user);

    void saveNewUser (AuthUser authUser, User user, Group group);
}
