package com.example.GroupTaskManagerApi.domain.task.model;


import com.example.GroupTaskManagerApi.domain.group.model.MemberId;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * タスク割当
 * <p>
 * タスクから管理されるためpackage-private
 * </p>
 * <p>
 * メンバに割り当てられているタスクの状態を管理する。
 * </p>
 */
class Assignment {

    private final LocalDateTime assignedAt;
    private AssignedTaskStatus status;
    private LocalDateTime doneAt;

    private Assignment (
            LocalDateTime assignedAt,
            AssignedTaskStatus status,
            LocalDateTime doneAt
    ) {
        this.assignedAt = assignedAt;
        this.status = status;
        this.doneAt = doneAt;
    }

    /**
     * 新たな割り当て情報を作成
     *
     * @return ステータス=作業中の割り当て情報
     */
    public static Assignment createNew () {
        return new Assignment(
                LocalDateTime.now(),
                AssignedTaskStatus.IN_PROCESSING,
                null
        );
    }

    /**
     * 既存データから復元する
     *
     * @param assignedAt 割当日時
     * @param status     ステータス
     * @param doneAt     完了日時
     * @return 復元された割り当て情報
     */
    public static Assignment reconstruct (
            LocalDateTime assignedAt,
            AssignedTaskStatus status,
            LocalDateTime doneAt
    ) {
        return new Assignment(
                assignedAt,
                status,
                doneAt
        );
    }

    public LocalDateTime getAssignedAt () {
        return assignedAt;
    }

    public AssignedTaskStatus getStatus () {
        return status;
    }

    /**
     * 割り当てられたステータスが完了しているか
     *
     * @return true: 完了
     */
    public boolean isDone () {
        return this.getStatus().equals(AssignedTaskStatus.DONE);
    }

    /**
     * 完了日時取得
     * <p>未完了時はnull</p>
     *
     * @return 完了していれば完了日時
     */
    public Optional<LocalDateTime> getDoneAt () {
        return Optional.ofNullable(this.doneAt);
    }

    /**
     * タスクを完了
     */
    public void done () {
        if (this.status.equals(AssignedTaskStatus.DONE)) throw new IllegalStateException("Already done.");
        this.status = AssignedTaskStatus.DONE;
        this.doneAt = LocalDateTime.now();
    }

    /**
     * スナップショット変換
     *
     * @param memberId メンバID
     * @return スナップショット
     */
    public AssignmentSnapshot toSnapshot (MemberId memberId) {
        return new AssignmentSnapshot(
                memberId,
                assignedAt,
                status,
                doneAt
        );
    }
}
