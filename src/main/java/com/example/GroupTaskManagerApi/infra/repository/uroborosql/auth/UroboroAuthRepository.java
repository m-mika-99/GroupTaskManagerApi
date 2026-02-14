package com.example.GroupTaskManagerApi.infra.repository.uroborosql.auth;

import com.example.GroupTaskManagerApi.domain.auth.AuthRepository;
import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;
import com.example.GroupTaskManagerApi.infra.repository.uroborosql.auth.entity.AuthUserEntity;
import jp.co.future.uroborosql.SqlAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
@Primary // FIXME 後で消してOnMemoryのほうを削除
public class UroboroAuthRepository implements AuthRepository {

    private final SqlAgent sqlAgent;

    @Autowired
    public UroboroAuthRepository (SqlAgent sqlAgent) {
        this.sqlAgent = sqlAgent;
    }

    @Override
    public Optional<AuthUser> findByEmail (String email) {
        Optional<AuthUserEntity> authUser = sqlAgent
                .query("auth/select_by_email")
                .param("email", email)
                .findFirst(AuthUserEntity.class);
        if (authUser.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(AuthUser.reconstruct(
                        authUser.get().getId().toString(),
                        authUser.get().getEmail(),
                        authUser.get().getPasswordHash(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        authUser.get().getUserId().toString()
                )
        );
    }

    @Override
    public void save (AuthUser user) {

    }
}
