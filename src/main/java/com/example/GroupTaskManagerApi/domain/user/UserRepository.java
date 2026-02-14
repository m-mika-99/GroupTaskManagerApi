package com.example.GroupTaskManagerApi.domain.user;

import com.example.GroupTaskManagerApi.domain.user.model.User;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;

import java.util.Optional;

/**
 * ユーザ永続化
 */
public interface UserRepository {
    /**
     * ID検索
     *
     * @param userId 検索対象ID
     * @return 見つからなければNULL
     */
    Optional<User> findById (UserId userId);
}
