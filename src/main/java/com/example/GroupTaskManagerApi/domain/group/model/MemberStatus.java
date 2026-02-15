package com.example.GroupTaskManagerApi.domain.group.model;

/**
 * メンバのグループ内でのステータス
 */
public enum MemberStatus {
    /**
     * 参加済み
     */
    JOINED(0),
    /**
     * 退出済み
     */
    LEAVED(1);

    public final int statusCode;

    MemberStatus (int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * statusCode から MemberStatus を取得
     *
     * @param code statusCode
     * @return 対応する MemberStatus
     * @throws IllegalArgumentException code に対応するものがなければ例外
     */
    public static MemberStatus from (int code) {
        for (MemberStatus status : values()) {
            if (status.statusCode == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
