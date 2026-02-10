package com.example.GroupTaskManagerApi.domain.auth.model;

import com.example.GroupTaskManagerApi.domain.auth.PasswordHashEncoder;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthUserTest {

    @Test
    @DisplayName("正しく新規作成される")
    void createNew_shouldInitializeAuthUserCorrectly () {
        Email email = new Email("test@example.com");
        PasswordHash hash = new PasswordHash("hashed");
        UserId userId = UserId.createNew();

        AuthUser authUser = AuthUser.createNew(email, hash, userId);

        assertNotNull(authUser.getId());
        assertEquals(email, authUser.getEmail());
        assertEquals(hash, authUser.getPasswordHash());
        assertEquals(userId, authUser.getUserId());
        assertTrue(authUser.getLastLoginAt().isEmpty());
        assertNotNull(authUser.getLastPasswordChangedAt());
    }

    @Test
    @DisplayName("正しく復元される")
    void reconstruct_shouldReConstructAuthUserCorrectly () {
        LocalDateTime now = LocalDateTime.now();
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        AuthUser user = AuthUser.reconstruct(
                uuid1.toString(),
                "test@example.com",
                "hashed-password",
                now,
                now.minusDays(1),
                uuid2.toString()
        );

        assertNotNull(user);
        assertEquals(uuid1, user.getId().value());
        assertEquals("test@example.com", user.getEmail().value());
        assertEquals("hashed-password", user.getPasswordHash().value());
        assertEquals(now, user.getLastLoginAt().get());
        assertEquals(now.minusDays(1), user.getLastPasswordChangedAt());
        assertEquals(uuid2, user.getUserId().value());
    }

    @Test
    @DisplayName("パスワードがあっていれば例外送出しない")
    void verifyPassword_shouldPass_whenPasswordMatches () {
        PasswordHashEncoder encoder = mock(PasswordHashEncoder.class);
        PasswordHash hash = new PasswordHash("hashed");
        AuthUser user = AuthUser.createNew(
                new Email("test@example.com"),
                hash,
                UserId.createNew()
        );

        when(encoder.matches("raw", hash)).thenReturn(true);

        assertDoesNotThrow(() -> user.verifyPassword("raw", encoder));
    }

    @Test
    @DisplayName("パスワードがあっていないとき例外送出する")
    void verifyPassword_shouldThrowException_whenPasswordNotMatches () {
        PasswordHashEncoder encoder = mock(PasswordHashEncoder.class);
        PasswordHash hash = new PasswordHash("hashed");
        AuthUser user = AuthUser.createNew(
                new Email("test@example.com"),
                hash,
                UserId.createNew()
        );

        when(encoder.matches("raw", hash)).thenReturn(false);

        assertThrows(
                RuntimeException.class, // FIXME 例外の種類見直し
                () -> user.verifyPassword("raw", encoder)
        );
    }

    @Test
    @DisplayName("ログイン時、最終ログイン時刻が更新される")
    void loginSucceeded_shouldUpdateLastLoginAt () throws InterruptedException {
        AuthUser user = AuthUser.createNew(
                new Email("test@example.com"),
                new PasswordHash("hashed"),
                UserId.createNew()
        );
        // 作成直後は空
        assertTrue(user.getLastLoginAt().isEmpty());

        // 一回目ログインで値が入る
        user.loginSucceeded();
        LocalDateTime first = user.getLastLoginAt().orElseThrow();

        Thread.sleep(1);

        // 二回目ログインで別の値（個々の時刻を変化させるためsleep↑）
        user.loginSucceeded();
        LocalDateTime second = user.getLastLoginAt().orElseThrow();

        assertTrue(second.isAfter(first));
    }

    @Test
    @DisplayName("ID一致してれば他違っても同一")
    void authUsersWithSameId_shouldBeEqual () {
        UUID uuid = UUID.randomUUID();

        AuthUser u1 = AuthUser.reconstruct(
                uuid.toString(),
                "sample1@sample.com",
                "password-hash-1",
                LocalDateTime.now(),
                LocalDateTime.now(),
                UUID.randomUUID().toString()
        );
        AuthUser u2 = AuthUser.reconstruct(
                uuid.toString(),
                "sample2@sample.com",
                "password-hash-2",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusDays(1),
                UUID.randomUUID().toString()
        );

        assertEquals(u1, u2);
    }

    @Test
    @DisplayName("ID不一致なら他すべて一致でも異なる")
    void authUsersWithDifferentId_shouldNotBeEqual () {
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AuthUser u1 = AuthUser.reconstruct(
                UUID.randomUUID().toString(),
                "sample@sample.com",
                "password-hash",
                now,
                now,
                uuid.toString()
        );
        AuthUser u2 = AuthUser.reconstruct(
                UUID.randomUUID().toString(),
                "sample@sample.com",
                "password-hash",
                now,
                now,
                uuid.toString()
        );

        assertNotEquals(u1, u2);
    }

    @Test
    @DisplayName("別のクラスのインスタンスとは異なる")
    void shouldNotBeEqualToInstanceOfAnotherClass () {
        Email email = new Email("test@example.com");
        PasswordHash hash = new PasswordHash("hashed");
        UserId userId = UserId.createNew();

        AuthUser authUser = AuthUser.createNew(email, hash, userId);
        assertFalse(authUser.equals("authUser"));
    }

    @Test
    @DisplayName("HashCodeはIDのHashCodeと一致")
    void hashCode_shouldEqualsToHashCodeOfId () {

        Email email = new Email("test@example.com");
        PasswordHash hash = new PasswordHash("hashed");
        UserId userId = UserId.createNew();

        AuthUser authUser = AuthUser.createNew(email, hash, userId);

        assertEquals(authUser.hashCode(), authUser.getId().hashCode());
    }
}