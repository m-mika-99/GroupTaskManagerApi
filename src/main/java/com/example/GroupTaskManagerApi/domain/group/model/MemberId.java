package com.example.GroupTaskManagerApi.domain.group.model;


import java.util.UUID;

/**
 * メンバID(値)
 *
 * @param value ID
 */
public record MemberId(UUID value) {

    /**
     * 新たにUUIDを発番してID発行する
     *
     * @return 発行したID
     */
    public static MemberId createNew () {
        return new MemberId(UUID.randomUUID());
    }

    /**
     * UUID文字列からIDを復元する
     *
     * @param uuid 作成元UUID文字列
     * @return 復元したID
     */
    public static MemberId fromString (String uuid) {
        return new MemberId(UUID.fromString(uuid));
    }
}
