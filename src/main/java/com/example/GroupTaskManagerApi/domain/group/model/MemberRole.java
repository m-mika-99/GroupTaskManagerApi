package com.example.GroupTaskManagerApi.domain.group.model;

/**
 * グループ内ロール
 */
public enum MemberRole {
    /**
     * 一般メンバ
     */
    MEMBER(1),
    /**
     * 管理者
     */
    ADMIN(2),
    /**
     * 所有者
     */
    OWNER(3);

    public final int roleCode;

    MemberRole (int roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * roleCode から MemberRole を取得
     *
     * @param code roleCode
     * @return 対応する MemberRole
     * @throws IllegalArgumentException code に対応するものがなければ例外
     */
    public static MemberRole from (int code) {
        for (MemberRole role : values()) {
            if (role.roleCode == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role code: " + code);
    }
}
