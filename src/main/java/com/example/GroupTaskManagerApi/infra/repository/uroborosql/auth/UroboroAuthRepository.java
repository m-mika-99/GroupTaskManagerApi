package com.example.GroupTaskManagerApi.infra.repository.uroborosql.auth;

import com.example.GroupTaskManagerApi.domain.auth.AuthRepository;
import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;
import com.example.GroupTaskManagerApi.domain.group.model.Group;
import com.example.GroupTaskManagerApi.domain.group.model.MemberSnapshot;
import com.example.GroupTaskManagerApi.domain.user.model.User;
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
    public void save (AuthUser authUser) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
            sqlAgent.update("auth/upsert")
                    .param("id", authUser.getId().value())
                    .param("email", authUser.getEmail().value())
                    .param("passwordHash", authUser.getPasswordHash().value())
                    .param("userId", authUser.getUserId().value())
                    .param("lastLoginAt", authUser.getLastLoginAt())
                    .param("lastPwChangedAt", authUser.getLastPasswordChangedAt())
                    .count();
        }
    }

    @Override
    public void saveNewUser (AuthUser authUser, User user, Group group) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
            sqlAgent.update("user/upsert")
                    .param("id", user.getId().value())
                    .param("displayName", user.getDisplayName())
                    .count();
            sqlAgent.update("auth/upsert")
                    .param("id", authUser.getId().value())
                    .param("email", authUser.getEmail().value())
                    .param("passwordHash", authUser.getPasswordHash().value())
                    .param("userId", authUser.getUserId().value())
                    .param("lastLoginAt", authUser.getLastLoginAt())
                    .param("lastPwChangedAt", authUser.getLastPasswordChangedAt())
                    .count();
            sqlAgent.update("group/upsert")
                    .param("id", group.getId().value())
                    .param("name", group.getName())
                    .param("description", group.getDescription())
                    .count();

            // FIXME: メンバは差分更新したい・・・
            for (MemberSnapshot memberSnapshot : group.getMemberSnapshots()) {
                sqlAgent.update("group/upsert_member")
                        .param("id", memberSnapshot.memberId().value())
                        .param("userId", memberSnapshot.userId().value())
                        .param("groupId", group.getId().value())
                        .param("joinedAt", memberSnapshot.joinedAt())
                        .param("roleCode", memberSnapshot.role().roleCode)
                        .param("displayNameOverride", "めんば") // TODO: memberに上書名持たせる
                        .param("statusCode", memberSnapshot.status().statusCode)
                        .count();
            }
        }

    }
}
