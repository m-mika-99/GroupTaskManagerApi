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

    private User (
            UserId id
    ) {
        this.id = id;
    }

    /**
     * IDを新規発番して新たなユーザ情報を作成
     *
     * @return 作成したユーザ
     */
    public static User createNew () {
        return new User(UserId.createNew());
    }

    public UserId getId () {
        return this.id;
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
