package com.example.GroupTaskManagerApi.domain.group.model;

import com.example.GroupTaskManagerApi.domain.user.model.UserId;

import java.time.LocalDateTime;

/**
 * メンバ状態スナップショット
 * <p>
 * package-privateなメンバの情報を編集可能で外に出さないようにするため、<br>
 * 変更不可のレコードにてある断面の状態を保持する。
 * </p>
 *
 * @param userId   ユーザID
 * @param memberId メンバID
 * @param role     メンバロール
 * @param status   メンバステータス
 * @param joinedAt グループ参加日時
 */
public record MemberSnapshot(
        MemberId memberId,
        UserId userId,
        MemberRole role,
        MemberStatus status,
        LocalDateTime joinedAt
) {
}
