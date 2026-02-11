package com.example.GroupTaskManagerApi.domain.task.model;


import com.example.GroupTaskManagerApi.domain.group.model.GroupId;
import com.example.GroupTaskManagerApi.domain.group.model.MemberId;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * タスク
 *
 * <p>
 * グループ内で発行されるタスク情報。アプリの中核。<br>
 * タスクはグループ内のメンバによって発行され、発行者を任意に含む、グループ内の任意のメンバを指定して割り当てることができる。
 * 割り当てられたメンバはタスクを「完了」することでステータスを進める。
 * </p>
 * <p>
 * TODO 各種値の変更メソッドの作成
 */
public class Task {

    /**
     * タイトルの長さ限界
     * <p>{@value}文字以下とする。</p>
     */
    private static final int TASK_TITLE_MAX_LENGTH = 64;

    private final TaskId id;
    private final GroupId groupId;
    private final LocalDateTime issuedAt;
    private final MemberId issuerId;
    private final Map<MemberId, Assignment> assignments = new HashMap<>();
    private String title;
    private String description;
    private LocalDateTime deadlineAt;
    private TaskStatus status;

    private Task (
            TaskId id,
            GroupId groupId,
            LocalDateTime issuedAt,
            MemberId issuerId,
            String title,
            String description,
            LocalDateTime deadlineAt,
            TaskStatus status
    ) {
        this.id = id;
        this.groupId = groupId;
        this.issuedAt = issuedAt;
        this.issuerId = issuerId;
        this.title = title;
        this.description = description;
        this.deadlineAt = deadlineAt;
        this.status = status;
    }

    /**
     * 新たなタスクを発行する
     *
     * @param groupId     発行するグループ
     * @param issuerId    発行者
     * @param title       タイトル
     * @param description 説明文
     * @param deadlineAt  〆切日時
     * @return 発行したタスク
     */
    public static Task createNew (
            GroupId groupId,
            MemberId issuerId,
            String title,
            String description,
            LocalDateTime deadlineAt
    ) {
        if (groupId == null) throw new IllegalArgumentException("GroupId must not be null.");
        if (!isTitleValid(title)) throw new IllegalArgumentException("Invalid task title.");
        if (issuerId == null) throw new IllegalArgumentException("IssuerId must not be null.");
        if (deadlineAt == null) throw new IllegalArgumentException("Deadline must not be null.");
        return new Task(
                TaskId.createNew(),
                groupId,
                LocalDateTime.now(),
                issuerId,
                title,
                description,
                deadlineAt,
                TaskStatus.ACTIVE
        );
    }

    /**
     * 既存データから復元する
     *
     * @param taskId              タスクID
     * @param groupId             グループID
     * @param issuedAt            発行日時
     * @param issuerId            発行者メンバID
     * @param title               タイトル
     * @param description         説明
     * @param deadlineAt          〆切日時
     * @param status              ステータス
     * @param assignmentSnapshots 割当リスト
     * @return 復元されたタスク情報
     */
    public static Task reconstruct (
            String taskId,
            String groupId,
            LocalDateTime issuedAt,
            String issuerId,
            String title,
            String description,
            LocalDateTime deadlineAt,
            TaskStatus status,
            List<AssignmentSnapshot> assignmentSnapshots
    ) {
        Task task = new Task(
                TaskId.fromString(taskId),
                GroupId.fromString(groupId),
                issuedAt,
                MemberId.fromString(issuerId),
                title,
                description,
                deadlineAt, status
        );

        assignmentSnapshots.forEach(e ->
                task.assignments.put(
                        e.memberId(),
                        Assignment.reconstruct(
                                e.assignedAt(),
                                e.status(),
                                e.doneAt()
                        )
                )
        );

        return task;
    }

    /**
     * タイトルの整合性チェック
     * <list>
     * <li> NULLでないこと</li>
     * <li> 空文字列でないこと</li>
     * <li> 空白文字でないこと</li>
     * <li> 長さが上限以下であること</li>
     * <list/>
     *
     * @param title 判定対象
     * @return true: 正しい
     * @see Task#TASK_TITLE_MAX_LENGTH
     */
    private static boolean isTitleValid (String title) {
        if (title == null) return false;
        if (title.isBlank()) return false;
        if (!(title.length() <= TASK_TITLE_MAX_LENGTH)) return false;
        return true;
    }

    /**
     * タスクにユーザを割り当てる
     *
     * <p>グループに含まれていることを事前に確認すること</p>
     *
     * @param assigneeId 割当対象者
     */
    public void assign (MemberId assigneeId) {
        if (assigneeId == null) throw new IllegalArgumentException("AssigneeId must not be null.");
        if (isAssigned(assigneeId)) throw new IllegalStateException("Already assigned.");
        this.assignments.put(assigneeId, Assignment.createNew());
    }

    public TaskId getId () {
        return id;
    }

    public GroupId getGroupId () {
        return groupId;
    }

    public LocalDateTime getIssuedAt () {
        return issuedAt;
    }

    public MemberId getIssuerId () {
        return issuerId;
    }

    public String getTitle () {
        return title;
    }

    public String getDescription () {
        return description;
    }

    public LocalDateTime getDeadlineAt () {
        return deadlineAt;
    }

    public TaskStatus getStatus () {
        return status;
    }

    /**
     * すでに割当済みか確認
     *
     * @param memberId 確認対象
     * @return true: 割り当て済み
     */
    public boolean isAssigned (MemberId memberId) {
        return assignments.containsKey(memberId);
    }

    /**
     * 対象ユーザの状態を完了にする
     *
     * @param memberId 対象ユーザ
     */
    public void doneFor (MemberId memberId) {
        if (!isAssigned(memberId)) throw new IllegalArgumentException("User not assigned.");
        assignments.get(memberId).done();
    }

    /**
     * アーカイブする
     */
    public void archive () {
        if (getStatus().equals(TaskStatus.ARCHIVED)) throw new IllegalStateException("Already archived.");
        status = TaskStatus.ARCHIVED;
    }

    /**
     * 割当済みのユーザが全員完了しているか
     * <p>割当数ゼロで完了扱いとする</p>
     *
     * @return true: 全員完了
     */
    public boolean isAllAssigneeDone () {
        if (assignments.isEmpty()) {
            return true;
        }
        return assignments.values().stream().allMatch(Assignment::isDone);
    }

    /**
     * 指定メンバの割当スナップショットを取得する
     *
     * @param memberId メンバ指定
     * @return 割当スナップショット
     */
    public AssignmentSnapshot getAssignmentSnapshotOf (MemberId memberId) {
        if (!isAssigned(memberId)) throw new IllegalArgumentException("Not assigned.");
        return assignments.get(memberId).toSnapshot(memberId);
    }

    /**
     * すべてのメンバの割当スナップショットを取得する
     *
     * @return 割当スナップショットのリスト
     */
    public List<AssignmentSnapshot> getAssignmentSnapshots () {
        return assignments.entrySet().stream()
                .map(e -> e.getValue().toSnapshot(e.getKey()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals (Object other) {
        if (!(other instanceof Task)) {
            return false;
        }
        return ((Task) other).getId().equals(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode () {
        return this.getId().hashCode();
    }
}
