package com.example.GroupTaskManagerApi.domain.auth.model;


import com.example.GroupTaskManagerApi.domain.auth.PasswordHashEncoder;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 認証ユーザ
 *
 * <p>
 * 認証ユーザはアプリへのログイン（認証）の役割を果たす。<br>
 * ログイン後のアプリ内での一意生の確保についてはUserIdに移譲する。
 * </p>
 * <p>
 * TODO 各種値の変更メソッドの作成
 */
public class AuthUser {

    private final AuthUserId id;
    private Email email;
    private PasswordHash passwordHash;
    private LocalDateTime lastLoginAt;
    private LocalDateTime lastPasswordChangedAt;
    private UserId userId;

    private AuthUser (
            AuthUserId id,
            Email email,
            PasswordHash passwordHash,
            LocalDateTime lastLoginAt,
            LocalDateTime lastPasswordChangedAt,
            UserId userId
    ) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.lastLoginAt = lastLoginAt;
        this.lastPasswordChangedAt = lastPasswordChangedAt;
        this.userId = userId;
    }

    /**
     * 新たに認証ユーザを作成
     *
     * @param email        メアド
     * @param passwordHash ハッシュ化パスワード
     * @param userId       ユーザID
     * @return 作成された認証ユーザ
     */
    public static AuthUser createNew (
            Email email,
            PasswordHash passwordHash,
            UserId userId
    ) {
        return new AuthUser(
                AuthUserId.createNew(),
                email,
                passwordHash,
                null,
                LocalDateTime.now(),
                userId
        );
    }

    /**
     * 既存データから復元する
     *
     * @param id
     * @param email
     * @param passwordHash
     * @param lastLoginAt
     * @param lastPasswordChangedAt
     * @param userId
     * @return
     */
    public static AuthUser reconstruct (
            String id,
            String email,
            String passwordHash,
            LocalDateTime lastLoginAt,
            LocalDateTime lastPasswordChangedAt,
            String userId
    ) {
        return new AuthUser(
                AuthUserId.fromString(id),
                new Email(email),
                new PasswordHash(passwordHash),
                lastLoginAt,
                lastPasswordChangedAt,
                UserId.fromString(userId)
        );
    }

    public AuthUserId getId () {
        return id;
    }

    public UserId getUserId () {
        return userId;
    }

    public LocalDateTime getLastPasswordChangedAt () {
        return lastPasswordChangedAt;
    }

    /**
     * 最終ログイン日時
     *
     * @return ログイン実績なしでNull
     */
    public Optional<LocalDateTime> getLastLoginAt () {
        return Optional.ofNullable(lastLoginAt);
    }

    public PasswordHash getPasswordHash () {
        return passwordHash;
    }

    public Email getEmail () {
        return email;
    }

    /**
     * パスワード検証
     * <p>不一致で例外送出</p>
     *
     * @param raw     平文
     * @param encoder 比較先ハッシュ
     */
    public void verifyPassword (String raw, PasswordHashEncoder encoder) {
        // FIXME 投げる例外の種類とコメント
        if (!encoder.matches(raw, passwordHash)) throw new RuntimeException();
    }

    /**
     * ログイン成功
     *
     * <ul>
     *     <li>最終ログイン日時を更新する</li>
     * </ul>
     */
    public void loginSucceeded () {
        lastLoginAt = LocalDateTime.now();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals (Object other) {
        if (!(other instanceof AuthUser)) {
            return false;
        }
        return ((AuthUser) other).getId().equals(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode () {
        return getId().hashCode();
    }
}