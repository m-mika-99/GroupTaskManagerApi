package com.example.GroupTaskManagerApi.infra.repository.uroborosql.auth;

import com.example.GroupTaskManagerApi.domain.auth.AuthRepository;
import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;
import com.example.GroupTaskManagerApi.infra.repository.uroborosql.auth.entity.AuthUserEntity;
import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.config.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Primary // FIXME 後で消してOnMemoryのほうを削除
public class UroboroAuthRepository implements AuthRepository {

    private final SqlConfig sqlConfig;

    @Autowired
    public UroboroAuthRepository (SqlConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
    }

    @Override
    public Optional<AuthUser> findByEmail (String email) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
            Optional<AuthUserEntity> authUser = sqlAgent
                    .query("auth/select_by_email")
                    .param("email", email)
                    .findFirst(AuthUserEntity.class);
            return authUser.map(AuthUserEntity::toDomain);
        }
    }

    @Override
    public void save (AuthUser user) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
            sqlAgent.update("auth/upsert")
                    .param("id", user.getId().value())
                    .param("email", user.getEmail().value())
                    .param("passwordHash", user.getPasswordHash().value())
                    .param("userId", user.getUserId().value())
                    .param("lastLoginAt", user.getLastLoginAt())
                    .param("lastPwChangedAt", user.getLastPasswordChangedAt())
                    .count();
        }
    }
}
