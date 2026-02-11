package com.example.GroupTaskManagerApi.domain.group.model;

import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class MemberTest {

    @Test
    @DisplayName("ロール指定新規作成")
    void createNew_withRole () {
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.ADMIN;

        Member member = Member.createNew(
                UserId.fromString(userId.toString()),
                role
        );

        assertInstanceOf(MemberId.class, member.getId());
        assertEquals(userId, member.getUserId().value());
        assertEquals(role, member.getRole());
        assertInstanceOf(LocalDateTime.class, member.getJoinedAt());
        assertEquals(MemberStatus.JOINED, member.getStatus());
    }

    @Test
    @DisplayName("ロール指定新規作成時、ユーザIDがnullで例外送出")
    void createNew_withRole_errorWhenUserIdIsNull () {
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.ADMIN;

        assertThrows(IllegalArgumentException.class, () ->
                Member.createNew(
                        null,
                        role
                )
        );
    }

    @Test
    @DisplayName("ロール指定新規作成時、ロールがnullで例外送出")
    void createNew_withRole_errorWhenRoleIsNull () {
        UUID userId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () ->
                Member.createNew(
                        UserId.fromString(userId.toString()),
                        null
                )
        );
    }

    @Test
    @DisplayName("既存データから復元")
    void reconstruct () {
        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.ADMIN;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberStatus status = MemberStatus.JOINED;

        Member member = Member.reconstruct(
                memberId.toString(),
                userId.toString(),
                role,
                joinedAt,
                status
        );

        assertEquals(memberId, member.getId().value());
        assertEquals(userId, member.getUserId().value());
        assertEquals(role, member.getRole());
        assertEquals(joinedAt, member.getJoinedAt());
        assertEquals(status, member.getStatus());
    }

    @Test
    @DisplayName("メンバロール新規作成")
    void createNew_withMemberRole () {
        UUID userId = UUID.randomUUID();

        Member member = Member.createNew(
                UserId.fromString(userId.toString())
        );

        assertInstanceOf(MemberId.class, member.getId());
        assertEquals(userId, member.getUserId().value());
        assertEquals(MemberRole.MEMBER, member.getRole());
    }

    @Test
    @DisplayName("メンバロール新規作成時、ユーザIDがnullで例外送出")
    void createNew_withMemberRole_errorWhenUserIdIsNull () {

        assertThrows(IllegalArgumentException.class, () ->
                Member.createNew(
                        null
                )
        );
    }

    @Test
    @DisplayName("ロール変更")
    void changeRole () {
        Member member = Member.createNew(UserId.createNew());
        assertEquals(MemberRole.MEMBER, member.getRole());

        member.changeRole(MemberRole.ADMIN);

        assertEquals(MemberRole.ADMIN, member.getRole());
    }

    @Test
    @DisplayName("ロール変更時、指定Nullで例外送出")
    void changeRole_errorWhenNewRoleIsNull () {
        Member member = Member.createNew(UserId.createNew());

        assertThrows(IllegalArgumentException.class, () ->
                member.changeRole(null)
        );
    }

    @Test
    @DisplayName("スナップショット作成")
    void toSnapshot () {
        UUID memberId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        MemberRole role = MemberRole.ADMIN;
        LocalDateTime joinedAt = LocalDateTime.now();
        MemberStatus status = MemberStatus.JOINED;

        Member member = Member.reconstruct(
                memberId.toString(),
                userId.toString(),
                role,
                joinedAt,
                status
        );

        MemberSnapshot snapshot = member.toSnapshot();

        assertEquals(memberId, snapshot.memberId().value());
        assertEquals(userId, snapshot.userId().value());
        assertEquals(role, snapshot.role());
        assertEquals(joinedAt, snapshot.joinedAt());
        assertEquals(status, snapshot.status());
    }
}