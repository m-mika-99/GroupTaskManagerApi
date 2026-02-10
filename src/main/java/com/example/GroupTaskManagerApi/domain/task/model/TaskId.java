package com.example.GroupTaskManagerApi.domain.task.model;


import java.util.UUID;

/**
 * タスクID(値)
 *
 * @param value ID
 */
public record TaskId(UUID value) {

    /**
     * 新たにUUIDを発番してID発行する
     *
     * @return 発行したID
     */
    public static TaskId createNew () {
        return new TaskId(UUID.randomUUID());
    }

    /**
     * UUID文字列からIDを復元する
     *
     * @param uuid 作成元UUID文字列
     * @return 復元したID
     */
    public static TaskId fromString (String uuid) {
        return new TaskId(UUID.fromString(uuid));
    }
}
