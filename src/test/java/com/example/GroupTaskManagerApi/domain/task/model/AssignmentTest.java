package com.example.GroupTaskManagerApi.domain.task.model;

import com.example.GroupTaskManagerApi.domain.group.model.MemberId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    @Test
    @DisplayName("新規作成で作業中ステータスで作成される")
    void createNew () {
        Assignment assignment = Assignment.createNew();

        assertEquals(AssignedTaskStatus.IN_PROCESSING, assignment.getStatus());
        assertFalse(assignment.isDone());
        assertTrue(assignment.getDoneAt().isEmpty());
        assertNotNull(assignment.getAssignedAt());
    }

    @Test
    @DisplayName("正しく復元される")
    void reconstruct () {
        LocalDateTime assignedAt = LocalDateTime.now();
        AssignedTaskStatus status = AssignedTaskStatus.DONE;
        LocalDateTime doneAt = LocalDateTime.now();

        Assignment assignment = Assignment.reconstruct(
                assignedAt,
                status,
                doneAt
        );

        assertEquals(assignedAt, assignment.getAssignedAt());
        assertEquals(status, assignment.getStatus());
        assertEquals(doneAt, assignment.getDoneAt().get());
    }

    @Test
    @DisplayName("完了するとステータスが遷移して完了日が入る")
    void done () {
        Assignment assignment = Assignment.createNew();

        assignment.done();

        assertEquals(AssignedTaskStatus.DONE, assignment.getStatus());
        assertTrue(assignment.isDone());
        assertTrue(assignment.getDoneAt().isPresent());
    }

    @Test
    @DisplayName("完了済みのものを完了しようとすると例外送出")
    void done_ErrorIfAlreadyDone () {
        Assignment assignment = Assignment.createNew();
        assignment.done();

        assertThrows(IllegalStateException.class, assignment::done);
    }

    @Test
    @DisplayName("スナップショット作成が正しい")
    void toSnapshot () {
        LocalDateTime assignedAt = LocalDateTime.now();
        AssignedTaskStatus status = AssignedTaskStatus.DONE;
        LocalDateTime doneAt = LocalDateTime.now();

        Assignment assignment = Assignment.reconstruct(
                assignedAt,
                status,
                doneAt
        );
        UUID memberId = UUID.randomUUID();
        AssignmentSnapshot snapshot = assignment.toSnapshot(MemberId.fromString(memberId.toString()));

        assertEquals(memberId, snapshot.memberId().value());
        assertEquals(assignedAt, snapshot.assignedAt());
        assertEquals(status, snapshot.status());
        assertEquals(doneAt, snapshot.doneAt());
    }
}