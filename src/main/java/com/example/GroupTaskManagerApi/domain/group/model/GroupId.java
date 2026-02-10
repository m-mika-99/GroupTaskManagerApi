package com.example.GroupTaskManagerApi.domain.group.model;


import java.util.UUID;

/**
 * グループID(値)
 *
 * @param value ID
 */
public record GroupId(UUID value) {

    /**
     * 新たにUUIDを発番してID発行する
     *
     * @return 発行したID
     */
    public static GroupId createNew () {
        return new GroupId(UUID.randomUUID());
    }

    /**
     * UUID文字列からIDを復元する
     *
     * @param uuid 作成元UUID文字列
     * @return 復元したID
     */
    public static GroupId fromString (String uuid) {
        return new GroupId(UUID.fromString(uuid));
    }
}