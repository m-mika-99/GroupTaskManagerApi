package com.example.GroupTaskManagerApi.domain.auth.model;


import java.util.UUID;

/**
 * 認証ユーザID(値)
 *
 * @param value ID
 */
public record AuthUserId(UUID value) {

    /**
     * 新たにUUIDを発番してID発行する
     *
     * @return 発行したID
     */
    public static AuthUserId createNew () {
        return new AuthUserId(UUID.randomUUID());
    }

    /**
     * UUID文字列からIDを復元する
     *
     * @param uuid 作成元UUID文字列
     * @return 復元したID
     */
    public static AuthUserId fromString (String uuid) {
        return new AuthUserId(UUID.fromString(uuid));
    }
}