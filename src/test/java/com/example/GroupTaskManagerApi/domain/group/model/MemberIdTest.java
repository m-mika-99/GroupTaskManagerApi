package com.example.GroupTaskManagerApi.domain.group.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberIdTest {

    @Test
    @DisplayName("新規作成で正しく作成される")
    void createNew_shouldGenerateValidId () {
        MemberId id = MemberId.createNew();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    @DisplayName("新規作成でランダムに作成される")
    void createNew_shouldGenerateDifferentIds () {
        MemberId id1 = MemberId.createNew();
        MemberId id2 = MemberId.createNew();

        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("文字列から正しく復元される")
    void fromString_shouldRestoreSameUuid () {
        UUID uuid = UUID.randomUUID();
        MemberId id = MemberId.fromString(uuid.toString());

        assertEquals(uuid, id.value());
    }

    @Test
    @DisplayName("不正文字列で例外送出する")
    void fromString_shouldThrowException_whenInvalidUuid () {
        assertThrows(
                IllegalArgumentException.class,
                () -> MemberId.fromString("invalid-uuid")
        );
    }
}