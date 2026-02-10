package com.example.GroupTaskManagerApi.domain.task;

import com.example.GroupTaskManagerApi.domain.group.model.MemberId;

import java.time.LocalDateTime;

/**
 * 割当状態スナップショット
 *
 * <p>
 * package-privateなメンバの情報を編集可能で外に出さないようにするため、<br>
 * 変更不可のレコードにてある断面の状態を保持する。
 * </p>
 *
 * @param memberId   メンバID
 * @param assignedAt 割当日時
 * @param status     ステータス
 * @param doneAt     完了日時（未完了でNULL）
 */
public record AssignmentSnapshot(
        MemberId memberId,
        LocalDateTime assignedAt,
        AssignedTaskStatus status,
        LocalDateTime doneAt
) {
}

