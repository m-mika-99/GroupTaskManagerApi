package com.example.GroupTaskManagerApi.infra.repository.uroborosql.group;

import com.example.GroupTaskManagerApi.domain.group.GroupRepository;
import com.example.GroupTaskManagerApi.domain.group.model.Group;
import com.example.GroupTaskManagerApi.domain.group.model.GroupId;
import com.example.GroupTaskManagerApi.domain.group.model.MemberSnapshot;
import com.example.GroupTaskManagerApi.infra.repository.uroborosql.group.entity.GroupEntity;
import com.example.GroupTaskManagerApi.infra.repository.uroborosql.group.entity.MemberEntity;
import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.config.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary // FIXME 後で消してOnMemoryのほうを削除
public class UroboroGroupRepository implements GroupRepository {

    private final SqlConfig sqlConfig;

    @Autowired
    public UroboroGroupRepository (SqlConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
    }

    @Override
    public Optional<Group> findById (GroupId groupId) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
            Optional<GroupEntity> groupRecord = sqlAgent
                    .query("group/select_by_id")
                    .param("id", groupId.value())
                    .findFirst(GroupEntity.class);

            if (groupRecord.isEmpty()) {
                return Optional.empty();
            }

            List<MemberEntity> members = sqlAgent
                    .query("group/select_members_by_group_id")
                    .param("groupId", groupId.value())
                    .collect(MemberEntity.class);

            List<MemberSnapshot> memberSnapshots = members.stream()
                    .map(MemberEntity::toSnapshot)
                    .toList();

            return Optional.of(groupRecord.get().toDomain(memberSnapshots));
        }
    }

    @Override
    public void save (Group group) {
        try (SqlAgent sqlAgent = sqlConfig.agent()) {
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
