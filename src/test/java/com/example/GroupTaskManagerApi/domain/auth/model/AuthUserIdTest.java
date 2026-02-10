package com.example.GroupTaskManagerApi.domain.auth.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthUserIdTest {

    @Test
    @DisplayName("新規作成で正しく作成されること")
    void createNew_shouldGenerateValidId () {
        AuthUserId id = AuthUserId.createNew();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    @DisplayName("新規作成でランダムにできること")
    void createNew_shouldGenerateDifferentIds () {
        AuthUserId id1 = AuthUserId.createNew();
        AuthUserId id2 = AuthUserId.createNew();

        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("文字列から正しく復元されること")
    void fromString_shouldRestoreSameUuid () {
        UUID uuid = UUID.randomUUID();
        AuthUserId id = AuthUserId.fromString(uuid.toString());

        assertEquals(uuid, id.value());
    }

    @Test
    @DisplayName("不正文字列で例外送出すること")
    void fromString_shouldThrowException_whenInvalidUuid () {
        assertThrows(
                IllegalArgumentException.class,
                () -> AuthUserId.fromString("invalid-uuid")
        );
    }
}