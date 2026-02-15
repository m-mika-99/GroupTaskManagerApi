package com.example.GroupTaskManagerApi.infra.repository.uroborosql.user;

import com.example.GroupTaskManagerApi.domain.user.UserRepository;
import com.example.GroupTaskManagerApi.domain.user.model.User;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import com.example.GroupTaskManagerApi.infra.repository.uroborosql.user.entity.UserEntity;
import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.config.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary // FIXME 後で消してOnMemoryのほうを削除
public class UroboroUserRepository implements UserRepository {

    private final SqlConfig sqlConfig;

    @Autowired
    public UroboroUserRepository (SqlConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
    }

    @Override
    public Optional<User> findById (UserId userId) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
            Optional<UserEntity> authUser = sqlAgent
                    .query("user/select_by_id")
                    .param("id", userId.value())
                    .findFirst(UserEntity.class);
            return authUser.map(UserEntity::toDomain);
        }
    }

    @Override
    public void save (User user) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
            sqlAgent.update("user/upsert")
                    .param("id", user.getId().value())
                    .param("displayName", user.getDisplayName())
                    .count();
        }
    }
}
