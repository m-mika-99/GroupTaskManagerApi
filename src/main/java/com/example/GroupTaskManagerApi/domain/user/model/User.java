package com.example.GroupTaskManagerApi.domain.user.model;


/**
 * ユーザ
 *
 * <p>
 * ユーザはアプリ内で単一のいわゆるユーザとして存在します。<br>
 * 認証情報から一意に指定され、認証完了後にシステム内での振る舞いにおける一意生を示します。
 * </p>
 */
public class User {

    private final UserId id;
    private final String displayName;

    private User (
            UserId id,
            String displayName
    ) {
        this.id = id;
        this.displayName = displayName;
    }

    /**
     * IDを新規発番して新たなユーザ情報を作成
     *
     * @return 作成したユーザ
     */
    public static User createNew () {
        // FIXME
        return new User(UserId.createNew(), "sample");
    }

    /**
     * 既存データから復元する
     *
     * @param userId ユーザID
     * @return 復元されたユーザ
     */
    public static User reconstruct (String userId, String displayName) {
        return new User(UserId.fromString(userId), displayName);
    }

    public UserId getId () {
        return this.id;
    }

    public String getDisplayName () {
        return this.displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals (Object other) {
        if (!(other instanceof User)) {
            return false;
        }
        return ((User) other).getId().equals(this.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode () {
        return this.getId().hashCode();
    }
}
