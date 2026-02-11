package com.example.GroupTaskManagerApi.domain.task.model;

import com.example.GroupTaskManagerApi.domain.group.model.GroupId;
import com.example.GroupTaskManagerApi.domain.group.model.MemberId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    @DisplayName("新規作成は割り当てなし、未完了で作成")
    void createNew () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        Task task = Task.createNew(
                GroupId.fromString(groupId.toString()),
                MemberId.fromString(issuerId.toString()),
                title,
                description,
                deadlineAt
        );

        assertInstanceOf(TaskId.class, task.getId());
        assertEquals(groupId, task.getGroupId().value());
        assertInstanceOf(LocalDateTime.class, task.getIssuedAt());
        assertEquals(issuerId, task.getIssuerId().value());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(deadlineAt, task.getDeadlineAt());
        assertEquals(TaskStatus.ACTIVE, task.getStatus());
    }

    @Test
    @DisplayName("新規作成時、グループIDがnullなら例外送出")
    void createNew_errorWhenGroupIdIsNull () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                Task.createNew(
                        null,
                        MemberId.fromString(issuerId.toString()),
                        title,
                        description,
                        deadlineAt
                )
        );
    }

    @Test
    @DisplayName("新規作成時、発行者メンバIDがnullなら例外送出")
    void createNew_errorWhenIssuerMemberIdIsNull () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                Task.createNew(
                        GroupId.fromString(groupId.toString()),
                        null,
                        title,
                        description,
                        deadlineAt
                )
        );
    }

    @Test
    @DisplayName("新規作成時、タイトルがnullなら例外送出")
    void createNew_errorWhenTitleIsNull () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                Task.createNew(
                        GroupId.fromString(groupId.toString()),
                        MemberId.fromString(issuerId.toString()),
                        null,
                        description,
                        deadlineAt
                )
        );
    }

    @Test
    @DisplayName("新規作成時、タイトルがから文字列なら例外送出")
    void createNew_errorWhenTitleIsBlank () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                Task.createNew(
                        GroupId.fromString(groupId.toString()),
                        MemberId.fromString(issuerId.toString()),
                        "",
                        description,
                        deadlineAt
                )
        );
    }

    @Test
    @DisplayName("新規作成時、タイトルがから空文字列なら例外送出")
    void createNew_errorWhenTitleIsWhiteSpace () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                Task.createNew(
                        GroupId.fromString(groupId.toString()),
                        MemberId.fromString(issuerId.toString()),
                        " 　 ", // 半角スペース + 全角スペース + タブ
                        description,
                        deadlineAt
                )
        );
    }

    @Test
    @DisplayName("新規作成時、タイトルが64文字越えなら例外送出")
    void createNew_errorWhenTitleHasOver64Chars () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                Task.createNew(
                        GroupId.fromString(groupId.toString()),
                        MemberId.fromString(issuerId.toString()),
                        "あいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこあいうえおかきくけこさしすせそ", // 65文字
                        description,
                        deadlineAt
                )
        );
    }

    @Test
    @DisplayName("新規作成時、締切日がNullなら例外送出")
    void createNew_errorWhenDeadlineIsNull () {
        UUID groupId = UUID.randomUUID();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                Task.createNew(
                        GroupId.fromString(groupId.toString()),
                        MemberId.fromString(issuerId.toString()),
                        title,
                        description,
                        null
                )
        );
    }

    @Test
    @DisplayName("正しく復元される")
    void reconstruct () {
        UUID taskId = UUID.randomUUID();
        UUID GroupId = UUID.randomUUID();
        LocalDateTime issuedAt = LocalDateTime.now();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();
        TaskStatus status = TaskStatus.ACTIVE;

        UUID assigneeId = UUID.randomUUID();
        LocalDateTime assignedAt = LocalDateTime.now();
        AssignedTaskStatus assignStatus = AssignedTaskStatus.DONE;
        LocalDateTime doneAt = LocalDateTime.now();
        List<AssignmentSnapshot> assignmentSnapshots = List.of(
                new AssignmentSnapshot(MemberId.fromString(assigneeId.toString()), assignedAt, assignStatus, doneAt)
        );
        Task task = Task.reconstruct(
                taskId.toString(),
                GroupId.toString(),
                issuedAt,
                issuerId.toString(),
                title,
                description,
                deadlineAt,
                status,
                assignmentSnapshots
        );

        assertEquals(taskId, task.getId().value());
        assertEquals(GroupId, task.getGroupId().value());
        assertEquals(issuedAt, task.getIssuedAt());
        assertEquals(issuerId, task.getIssuerId().value());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(deadlineAt, task.getDeadlineAt());
        assertEquals(status, task.getStatus());

        assertEquals(1, task.getAssignmentSnapshots().size());
        assertEquals(assigneeId, task.getAssignmentSnapshots().getFirst().memberId().value());
        assertEquals(assignedAt, task.getAssignmentSnapshots().getFirst().assignedAt());
        assertEquals(assignStatus, task.getAssignmentSnapshots().getFirst().status());
        assertEquals(doneAt, task.getAssignmentSnapshots().getFirst().doneAt());

    }


    @Test
    @DisplayName("割当でメンバが追加")
    void assign () {

        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        MemberId memberId = MemberId.createNew();

        task.assign(memberId);

        assertEquals(1, task.getAssignmentSnapshots().size());
        assertTrue(task.isAssigned(memberId));
        assertEquals(AssignedTaskStatus.IN_PROCESSING, task.getAssignmentSnapshotOf(memberId).status());
        assertInstanceOf(LocalDateTime.class, task.getAssignmentSnapshotOf(memberId).assignedAt());
        assertNull(task.getAssignmentSnapshotOf(memberId).doneAt());
    }

    @Test
    @DisplayName("割当時、メンバIDがNullで例外送出")
    void assign_errorWhenMemberIdIsNull () {

        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        MemberId memberId = MemberId.createNew();

        assertThrows(IllegalArgumentException.class, () ->
                task.assign(null)
        );
    }

    @Test
    @DisplayName("割当時、メンバIDがすでに割り当て済みで例外送出")
    void assign_errorWhenMemberIdIsAlreadyAssigned () {

        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        MemberId memberId = MemberId.createNew();
        task.assign(memberId);

        assertThrows(IllegalStateException.class, () ->
                task.assign(memberId)
        );
    }

    @Test
    @DisplayName("割り当て済み判定")
    void isAssigned () {
        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        MemberId memberId1 = MemberId.createNew();
        MemberId memberId2 = MemberId.createNew();

        assertFalse(task.isAssigned(memberId1));
        assertFalse(task.isAssigned(memberId2));

        task.assign(memberId1);

        assertTrue(task.isAssigned(memberId1));
        assertFalse(task.isAssigned(memberId2));

    }

    @Test
    @DisplayName("指定した割当メンバのみを完了")
    void doneFor () {
        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        MemberId memberId1 = MemberId.createNew();
        MemberId memberId2 = MemberId.createNew();

        task.assign(memberId1);
        task.assign(memberId2);

        task.doneFor(memberId1);

        assertEquals(AssignedTaskStatus.DONE, task.getAssignmentSnapshotOf(memberId1).status());
        assertEquals(AssignedTaskStatus.IN_PROCESSING, task.getAssignmentSnapshotOf(memberId2).status());
    }

    @Test
    @DisplayName("完了指定したメンバが未割当で例外送出")
    void doneFor_errorWhenMemberIsNotAssigned () {
        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        MemberId memberId1 = MemberId.createNew();
        MemberId memberId2 = MemberId.createNew();

        task.assign(memberId1);

        assertThrows(IllegalArgumentException.class, () ->
                task.doneFor(memberId2)
        );
    }

    @Test
    @DisplayName("アーカイブでステータス遷移")
    void archive () {
        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        task.archive();

        assertEquals(TaskStatus.ARCHIVED, task.getStatus());
    }

    @Test
    @DisplayName("アーカイブ時、すでにアーカイブ済みなら例外送出")
    void archive_errorWhenTaskAlreadyArchived () {
        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        task.archive();

        assertThrows(IllegalStateException.class, task::archive);
    }

    @Test
    @DisplayName("全員完了判定")
    void isAllAssigneeDone () {
        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );
        // 割り当てなし
        assertTrue(task.isAllAssigneeDone());

        MemberId memberId1 = MemberId.createNew();
        MemberId memberId2 = MemberId.createNew();
        task.assign(memberId1);
        task.assign(memberId2);

        // 0/2完了
        assertFalse(task.isAllAssigneeDone());

        task.doneFor(memberId1);
        // 1/2完了
        assertFalse(task.isAllAssigneeDone());

        task.doneFor(memberId2);
        // 2/2完了
        assertTrue(task.isAllAssigneeDone());
    }

    @Test
    @DisplayName("スナップショット作成時、未アサインメンバ指定で例外送出")
    void getAssignmentSnapshotOf_errorWhenMemberNotAssigned () {
        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );
        MemberId memberId1 = MemberId.createNew();

        assertThrows(IllegalArgumentException.class, () ->
                task.getAssignmentSnapshotOf(memberId1)
        );
    }

    @Test
    @DisplayName("ID一致してれば他違っても同一")
    void equals_shouldBeEqualWithSameId () {
        UUID taskId = UUID.randomUUID();
        Task t1 = Task.reconstruct(
                taskId.toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                UUID.randomUUID().toString(),
                "title",
                "description",
                LocalDateTime.now(),
                TaskStatus.ACTIVE,
                List.of()
        );
        Task t2 = Task.reconstruct(
                taskId.toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                UUID.randomUUID().toString(),
                "title",
                "description",
                LocalDateTime.now(),
                TaskStatus.ACTIVE,
                List.of()
        );
        assertEquals(t1, t2);
    }

    @Test
    @DisplayName("ID不一致なら他すべて一致でも異なる")
    void equals_shouldNotBeEqualWithDifferentId () {
        UUID GroupId = UUID.randomUUID();
        LocalDateTime issuedAt = LocalDateTime.now();
        UUID issuerId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        LocalDateTime deadlineAt = LocalDateTime.now();
        TaskStatus status = TaskStatus.ACTIVE;

        UUID assigneeId = UUID.randomUUID();
        LocalDateTime assignedAt = LocalDateTime.now();
        AssignedTaskStatus assignStatus = AssignedTaskStatus.DONE;
        LocalDateTime doneAt = LocalDateTime.now();
        List<AssignmentSnapshot> assignmentSnapshots = List.of(
                new AssignmentSnapshot(MemberId.fromString(assigneeId.toString()), assignedAt, assignStatus, doneAt)
        );
        Task t1 = Task.reconstruct(
                UUID.randomUUID().toString(),
                GroupId.toString(),
                issuedAt,
                issuerId.toString(),
                title,
                description,
                deadlineAt,
                status,
                assignmentSnapshots
        );
        Task t2 = Task.reconstruct(
                UUID.randomUUID().toString(),
                GroupId.toString(),
                issuedAt,
                issuerId.toString(),
                title,
                description,
                deadlineAt,
                status,
                assignmentSnapshots
        );
        assertNotEquals(t1, t2);
    }

    @Test
    @DisplayName("別のクラスのインスタンスとは異なる")
    void equals_shouldNotBeEqualToInstanceOfAnotherClass () {

        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );
        assertFalse(task.equals("task"));
    }

    @Test
    @DisplayName("HashCodeはIDのHashCodeと一致")
    void hashCode_shouldEqualsToHashCodeOfId () {

        Task task = Task.createNew(
                GroupId.fromString(UUID.randomUUID().toString()),
                MemberId.fromString(UUID.randomUUID().toString()),
                "title",
                "description",
                LocalDateTime.now()
        );

        assertEquals(task.hashCode(), task.getId().hashCode());
    }
}