package com.example.GroupTaskManagerApi.domain.user.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    @DisplayName("新規作成で正しく作成されること")
    void createNew_shouldGenerateValidId () {
        UserId id = UserId.createNew();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    @DisplayName("新規作成でランダムにできること")
    void createNew_shouldGenerateDifferentIds () {
        UserId id1 = UserId.createNew();
        UserId id2 = UserId.createNew();

        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("文字列から正しく復元されること")
    void fromString_shouldRestoreSameUuid () {
        UUID uuid = UUID.randomUUID();
        UserId id = UserId.fromString(uuid.toString());

        assertEquals(uuid, id.value());
    }

    @Test
    @DisplayName("不正文字列で例外送出すること")
    void fromString_shouldThrowException_whenInvalidUuid () {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserId.fromString("invalid-uuid")
        );
    }
}