package com.example.GroupTaskManagerApi.domain.auth;

import com.example.GroupTaskManagerApi.domain.auth.model.PasswordHash;

/**
 * PWハッシュルール
 */
public interface PasswordHashEncoder {
    boolean matches (String raw, PasswordHash hash);
}
