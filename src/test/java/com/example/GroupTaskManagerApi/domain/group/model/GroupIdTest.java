package com.example.GroupTaskManagerApi.domain.group.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroupIdTest {

    @Test
    @DisplayName("新規作成で正しく作成されること")
    void createNew_shouldGenerateValidId () {
        GroupId id = GroupId.createNew();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    @DisplayName("新規作成でランダムにできること")
    void createNew_shouldGenerateDifferentIds () {
        GroupId id1 = GroupId.createNew();
        GroupId id2 = GroupId.createNew();

        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("文字列から正しく復元されること")
    void fromString_shouldRestoreSameUuid () {
        UUID uuid = UUID.randomUUID();
        GroupId id = GroupId.fromString(uuid.toString());

        assertEquals(uuid, id.value());
    }

    @Test
    @DisplayName("不正文字列で例外送出すること")
    void fromString_shouldThrowException_whenInvalidUuid () {
        assertThrows(
                IllegalArgumentException.class,
                () -> GroupId.fromString("invalid-uuid")
        );
    }
}