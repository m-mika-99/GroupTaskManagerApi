package com.example.GroupTaskManagerApi.domain.task.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskIdTest {

    @Test
    @DisplayName("新規作成で正しく作成される")
    void createNew_shouldGenerateValidId () {
        TaskId id = TaskId.createNew();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    @DisplayName("新規作成でランダムに作成される")
    void createNew_shouldGenerateDifferentIds () {
        TaskId id1 = TaskId.createNew();
        TaskId id2 = TaskId.createNew();

        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("文字列から正しく復元される")
    void fromString_shouldRestoreSameUuid () {
        UUID uuid = UUID.randomUUID();
        TaskId id = TaskId.fromString(uuid.toString());

        assertEquals(uuid, id.value());
    }

    @Test
    @DisplayName("不正文字列で例外送出する")
    void fromString_shouldThrowException_whenInvalidUuid () {
        assertThrows(
                IllegalArgumentException.class,
                () -> TaskId.fromString("invalid-uuid")
        );
    }
}