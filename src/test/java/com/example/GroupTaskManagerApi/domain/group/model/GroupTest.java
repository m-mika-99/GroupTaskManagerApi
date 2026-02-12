package com.example.GroupTaskManagerApi.domain.group.model;

import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    @DisplayName("新規作成でグループ所有者として作成")
    void createNew () {
        String name = "groupName";
        String description = "groupDescription";
        UserId ownerId = UserId.createNew();

        Group group = Group.createNew(
                name,
                description,
                ownerId
        );

        assertInstanceOf(GroupId.class, group.getId());
        assertEquals(name, group.getName());
        assertEquals(description, group.getDescription());
        assertEquals(1, group.getMemberSnapshots().size());
        MemberSnapshot member = group.getMemberSnapshots().getFirst();
        assertEquals(ownerId, member.userId());
    }

    @Test
    @DisplayName("新規作成時、グループ名Nullで例外送出")
    void createNew_errorWhenNameIsNull () {
        String description = "groupDescription";
        UserId ownerId = UserId.createNew();

        assertThrows(IllegalArgumentException.class, () ->
                Group.createNew(
                        null,
                        description,
                        ownerId
                )
        );
    }

    @Test
    @DisplayName("新規作成時、グループ名空文字列で例外送出")
    void createNew_errorWhenNameIsBlank () {
        String description = "groupDescription";
        UserId ownerId = UserId.createNew();

        assertThrows(IllegalArgumentException.class, () ->
                Group.createNew(
                        "",
                        description,
                        ownerId
                )
        );
    }

    @Test
    @DisplayName("新規作成時、グループ名空白文字で例外送出")
    void createNew_errorWhenNameIsWhiteSpace () {
        String description = "groupDescription";
        UserId ownerId = UserId.createNew();

        assertThrows(IllegalArgumentException.class, () ->
                Group.createNew(
                        " 　 ", // 半角スペース + 全角スペース + タブ
                        description,
                        ownerId
                )
        );
    }

    @Test
    @DisplayName("新規作成時、所有者IDがNullで例外送出")
    void createNew_errorWhenOwnerIdIsNull () {
        String name = "groupName";
        String description = "groupDescription";

        assertThrows(IllegalArgumentException.class, () ->
                Group.createNew(
                        name,
                        description,
                        null
                )
        );
    }

    @Test
    @DisplayName("復元")
    void reconstruction () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );

        assertEquals(groupId, group.getId().value());
        assertEquals(name, group.getName());
        assertEquals(description, group.getDescription());
        assertEquals(memberId, group.getMemberSnapshots().getFirst().memberId().value());
        assertEquals(1, group.getMemberSnapshots().size());
        assertEquals(userId, group.getMemberSnapshots().getFirst().userId().value());
        assertEquals(joinedAt, group.getMemberSnapshots().getFirst().joinedAt());
        assertEquals(status, group.getMemberSnapshots().getFirst().status());
        assertEquals(role, group.getMemberSnapshots().getFirst().role());
    }

    @Test
    @DisplayName("メンバのロール取得")
    void getRoleOf () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );

        assertEquals(MemberRole.OWNER, group.getRoleOf(MemberId.fromString(memberId.toString())));
    }

    @Test
    @DisplayName("メンバのロール取得時、Null指定で例外送出")
    void getRoleOf_errorWhenMemberIdIsNull () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );
        assertThrows(IllegalArgumentException.class, () ->
                group.getRoleOf(null)
        );
    }

    @Test
    @DisplayName("メンバのロール取得時、参加していないID指定で例外送出")
    void getRoleOf_errorWhenMemberNotAssigned () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );
        assertThrows(IllegalArgumentException.class, () ->
                group.getRoleOf(MemberId.createNew())
        );
    }

    @Test
    @DisplayName("ロール指定メンバ追加")
    void addMember_withRole () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );

        UserId additionalUserId = UserId.createNew();
        MemberRole additionalMemberRole = MemberRole.ADMIN;

        group.addMember(additionalUserId, additionalMemberRole);

        assertEquals(2, group.getMemberSnapshots().size());
        assertTrue(group.hasUserInMember(additionalUserId));
    }

    @Test
    @DisplayName("ロール指定メンバ追加時、User未指定で例外送出")
    void addMember_withRole_errorWhenUserIdIsNull () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );

        UserId additionalUserId = UserId.createNew();
        MemberRole additionalMemberRole = MemberRole.ADMIN;

        assertThrows(IllegalArgumentException.class, () ->
                group.addMember(null, additionalMemberRole)
        );
    }

    @Test
    @DisplayName("ロール指定メンバ追加時、Role未指定で例外送出")
    void addMember_withRole_errorWhenRoleIsNull () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );

        UserId additionalUserId = UserId.createNew();
        MemberRole additionalMemberRole = MemberRole.ADMIN;

        assertThrows(IllegalArgumentException.class, () ->
                group.addMember(additionalUserId, null)
        );
    }

    @Test
    @DisplayName("ロール指定メンバ追加時、Role未指定で例外送出")
    void addMember_withRole_errorWhenAlreadyAdded () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );

        UserId additionalUserId = UserId.createNew();
        MemberRole additionalMemberRole = MemberRole.ADMIN;
        group.addMember(additionalUserId, additionalMemberRole);

        assertThrows(IllegalStateException.class, () ->
                group.addMember(additionalUserId, additionalMemberRole)
        );
    }

    @Test
    @DisplayName("ロール変更")
    void changeRole () {
        UUID groupId = UUID.randomUUID();
        String name = "groupName";
        String description = "groupDescription";

        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.OWNER;
        MemberStatus status = MemberStatus.JOINED;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberSnapshot memberSnapshot = new MemberSnapshot(
                MemberId.fromString(memberId.toString()),
                UserId.fromString(userId.toString()),
                role,
                status,
                joinedAt
        );

        Group group = Group.reconstruct(
                groupId.toString(),
                name,
                description,
                List.of(memberSnapshot)
        );

        UserId additionalUserId = UserId.createNew();
        MemberRole additionalMemberRole = MemberRole.ADMIN;
        group.addMember(additionalUserId, additionalMemberRole);

    }

    @Test
    void transferOwnershipTo () {
    }

    @Test
    void hasMember () {
    }

    @Test
    void hasUserInMember () {
    }

    @Test
    void getMemberSnapshotOf () {
    }

    @Test
    void getMemberSnapshots () {
    }
}