package com.example.GroupTaskManagerApi.domain.group.model;


import com.example.GroupTaskManagerApi.domain.user.model.UserId;

import java.time.LocalDateTime;

/**
 * ユーザのグループ所属情報
 * <p>
 * Groupから操作される前提においてこのクラスはpackage-privateとする。
 * </p>
 * <p>
 * 「メンバ」はグループ内における一意性を担保する。グループにおけるタスクの作成やステータスの変更については
 * すべてメンバによって行われる。<br>
 * メンバはグループ内においてロールを持ち、ロールによってグループ内での操作可能範囲を制限する。<br>
 * 離脱した場合においても過去所属していた情報として残すことで、離脱後の情報参照に不都合がないようにする。
 * </p>
 */
class Member {

    private final MemberId id;
    private final UserId userId;
    private final LocalDateTime joinedAt;
    private MemberRole role;
    private MemberStatus status;

    private Member (
            MemberId id,
            UserId userId,
            MemberRole role,
            LocalDateTime joinedAt,
            MemberStatus status
    ) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
        this.status = status;
    }

    /**
     * 新たな所属情報を指定ロールで作成
     * <p>所属日時は現在時刻。ステータスは参加済み</p>
     *
     * @param userId ユーザID
     * @param role   ロール指定
     * @return 所属情報
     */
    public static Member createNew (UserId userId, MemberRole role) {
        return new Member(
                MemberId.createNew(),
                userId,
                role,
                LocalDateTime.now(),
                MemberStatus.JOINED
        );
    }

    /**
     * 新たな所属情報をメンバロールで作成
     *
     * @param userId ユーザID
     * @return メンバロールで作成された所属情報
     */
    public static Member createNew (UserId userId) {
        return createNew(userId, MemberRole.MEMBER);
    }

    public UserId getUserId () {
        return userId;
    }

    public MemberId getId () {
        return id;
    }

    public MemberStatus getStatus () {
        return status;
    }

    public MemberRole getRole () {
        return role;
    }

    public LocalDateTime getJoinedAt () {
        return joinedAt;
    }

    /**
     * メンバロールを変更
     *
     * @param newRole 変更先ロール
     */
    public void changeRole (MemberRole newRole) {
        role = newRole;
    }

    /**
     * メンバステータスを変更する
     *
     * @param newStatus 変更先ステータス
     */
    public void changeStatus (MemberStatus newStatus) {
        status = newStatus;
    }

    /**
     * スナップショット変換
     *
     * @return スナップショット
     */
    public MemberSnapshot toSnapshot () {
        return new MemberSnapshot(
                id,
                userId,
                role,
                status,
                joinedAt
        );
    }
}