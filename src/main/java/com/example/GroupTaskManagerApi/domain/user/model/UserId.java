package com.example.GroupTaskManagerApi.domain.user.model;

import java.util.UUID;

/**
 * ユーザID(値)
 *
 * @param value ID
 */
public record UserId(UUID value) {

    /**
     * 新たにUUIDを発番してID発行する
     *
     * @return 発行したID
     */
    public static UserId createNew () {
        return new UserId(UUID.randomUUID());
    }

    /**
     * UUID文字列からIDを復元する
     *
     * @param uuid 作成元UUID文字列
     * @return 復元したID
     */
    public static UserId fromString (String uuid) {
        return new UserId(UUID.fromString(uuid));
    }
}
