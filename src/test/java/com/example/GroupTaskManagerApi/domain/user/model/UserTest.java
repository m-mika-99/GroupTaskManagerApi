package com.example.GroupTaskManagerApi.domain.user.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("正しく新規作成される")
    void createNew_shouldInitializeAuthUserCorrectly () {

        User user = User.createNew();

        assertNotNull(user.getId());
    }

    @Test
    @DisplayName("正しく復元される")
    void reconstruct_shouldReConstructAuthUserCorrectly () {
        UUID uuid = UUID.randomUUID();

        User user = User.reconstruct(uuid.toString());

        assertNotNull(user);
        assertEquals(uuid, user.getId().value());
    }

    @Test
    @DisplayName("ID一致してれば他違っても同一")
    void usersWithSameId_shouldBeEqual () {
        UUID uuid = UUID.randomUUID();

        User u1 = User.reconstruct(
                uuid.toString()
        );
        User u2 = User.reconstruct(
                uuid.toString()
        );

        assertEquals(u1, u2);
    }

    @Test
    @DisplayName("ID不一致なら他すべて一致でも異なる")
    void usersWithDifferentId_shouldNotBeEqual () {

        User u1 = User.reconstruct(
                UUID.randomUUID().toString()
        );
        User u2 = User.reconstruct(
                UUID.randomUUID().toString()
        );

        assertNotEquals(u1, u2);
    }

    @Test
    @DisplayName("別のクラスのインスタンスとは異なる")
    void shouldNotBeEqualToInstanceOfAnotherClass () {

        User user = User.createNew();
        assertFalse(user.equals("authUser"));
    }

    @Test
    @DisplayName("HashCodeはIDのHashCodeと一致")
    void hashCode_shouldEqualsToHashCodeOfId () {

        User user = User.createNew();

        assertEquals(user.hashCode(), user.getId().hashCode());
    }
}