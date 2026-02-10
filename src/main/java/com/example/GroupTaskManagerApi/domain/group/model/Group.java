package com.example.GroupTaskManagerApi.domain.group.model;


import com.example.GroupTaskManagerApi.domain.user.model.UserId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * グループ
 *
 * <p>
 * グループはこのアプリにおいて（より上位の存在である）ユーザ以外が股ぐことができず、
 * すべてのタスクの作成、変更等はグループの中で行われる。<br>
 * メンバは0以上の任意のグループに参加でき、グループに参加しているその中においてメンバとしてふるまう。
 * </p>
 * <p>グループは次のものを含む</p>
 * <ul>
 * <li>1以上のメンバ</li>
 * <li>0以上の発行されたタスク</li>
 * <li>メンバの内、ただ一人の所有者</li>
 * <li>所有者でないメンバのうち、任意の数の管理者</li>
 * <li>所有者でも管理者でもない、一般のメンバ</li>
 * </ul>
 *
 * <p>
 * ユーザは任意にグループを作成することができ、その場合グループの所有者となる。<br>
 * 所有者は最低1名を保障するため、変更ではなく、移譲によって変更される。
 * </p>
 */
public class Group {

    /**
     * グループ名の長さ限界
     * <p>{@value}文字以下とする。</p>
     */
    private static final int GROUP_NAME_MAX_LENGTH = 64;

    private final GroupId id;
    private final Map<MemberId, Member> members = new HashMap<>();
    private String name;
    private String description;

    private Group (
            GroupId id,
            String name,
            String description
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * 新たなグループを作成
     *
     * @param name        グループ名(Not null / Not blank)
     * @param description 説明文
     * @param ownerUserId 所有者ユーザID(Not null)
     * @return 作成されたグループ
     */
    public static Group createNew (
            String name,
            String description,
            UserId ownerUserId
    ) {
        if (!isNameValid(name)) throw new IllegalArgumentException("Group name must not be null or empty.");
        if (ownerUserId == null) throw new IllegalArgumentException("OwnerId must not be null.");
        Group created = new Group(
                GroupId.createNew(),
                name,
                description
        );
        created.addMember(ownerUserId, MemberRole.OWNER);
        return created;
    }

    /**
     * 名前の整合性チェック
     * <list>
     * <li> NULLでないこと</li>
     * <li> 空文字列でないこと</li>
     * <li> 空白文字でないこと</li>
     * <li> 長さが上限以下であること</li>
     * <list/>
     *
     * @param name 判定対象
     * @return true: 正しい
     * @see Group#GROUP_NAME_MAX_LENGTH
     */
    private static boolean isNameValid (String name) {
        if (name == null) return false;
        if (name.isBlank()) return false;
        if (!(name.length() <= GROUP_NAME_MAX_LENGTH)) return false;
        return true;
    }

    public GroupId getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getDescription () {
        return description;
    }

    /**
     * グループ所有者ID取得
     *
     * @return 所有者ID
     * @throws IllegalStateException 所有者が見つからない
     */
    public MemberId getOwnerId () {
        Optional<MemberId> ownerId = members.entrySet().stream()
                .filter(e -> e.getValue().getRole().equals(MemberRole.OWNER))
                .map(Map.Entry::getKey)
                .findFirst();
        return ownerId.orElseThrow(() -> new IllegalStateException("Owner not found."));
    }

    /**
     * グループ名変更
     *
     * @param newName 変更後グループ名
     */
    public void changeName (String newName) {
        if (!isNameValid(newName)) throw new IllegalArgumentException("Invalid group name.");
        this.name = newName;
    }

    /**
     * グループ説明変更
     *
     * @param newDescription 変更後グループ説明
     */
    public void changeDescription (String newDescription) {
        description = newDescription;
    }

    /**
     * メンバのロールを取得
     *
     * @param memberId 取得対象
     * @return 取得対象のロール
     * @throws IllegalArgumentException メンバに取得対象が存在しない
     */
    public MemberRole getRoleOf (MemberId memberId) {
        if (memberId == null) throw new IllegalArgumentException("UserId must not be null.");
        if (!hasMember(memberId)) throw new IllegalArgumentException("User is not member of group.");
        return members.get(memberId).getRole();
    }

    /**
     * ロールを指定してメンバ追加
     *
     * @param userId 追加対象ユーザ
     * @param role   追加時ロール
     * @throws IllegalArgumentException すでにメンバに含まれる
     */
    public void addMember (UserId userId, MemberRole role) {
        if (userId == null) throw new IllegalArgumentException("UserId must not be null.");
        if (role == null) throw new IllegalArgumentException("Role must not be null.");
        if (hasUserInMember(userId)) throw new IllegalArgumentException("User already contained.");
        Member member = Member.createNew(userId, role);
        members.put(member.getId(), member);
    }

    /**
     * メンバロールでメンバ追加
     *
     * @param userId 追加対象
     */
    public void addMember (UserId userId) {
        addMember(userId, MemberRole.MEMBER);
    }

    /**
     * メンバロールを変更
     *
     * <p>以下の所有者を含む変更は認めない。別メソッドを利用すること</p>
     * <list>
     * <li>所有者を他のロールに変更</li>
     * <li>所有者に変更</li>
     * </list>
     *
     * @param memberId 変更対象
     * @param newRole  変更先ロール
     * @throws IllegalArgumentException メンバに変更対象が存在しない
     * @throws IllegalArgumentException 所有者を含む変更
     * @see Group#transferOwnershipTo
     */
    public void changeRole (MemberId memberId, MemberRole newRole) {
        if (memberId == null) throw new IllegalArgumentException("UserId must not be null.");
        if (!hasMember(memberId)) throw new IllegalArgumentException("User is not member of group.");
        if (newRole == null) throw new IllegalArgumentException("New role must not be null.");
        if (newRole.equals(MemberRole.OWNER)) throw new IllegalArgumentException("Cannot change to owner.");
        if (members.get(memberId).getRole().equals(MemberRole.OWNER))
            throw new IllegalArgumentException("Owner can not be another role.");

        members.get(memberId).changeRole(newRole);
    }

    /**
     * グループ所有権限を移譲する
     * <p>所有者以外のロール変更は別メソッドを利用すること</p>
     *
     * @param newOwnerId 移譲先
     * @throws IllegalArgumentException メンバに移譲先が存在しない
     * @see Group#changeRole
     */
    public void transferOwnershipTo (MemberId newOwnerId) {
        if (!hasMember(newOwnerId)) throw new IllegalArgumentException("Member is not joined in the group.");
        MemberId exOwnerId = getOwnerId();
        members.get(exOwnerId).changeRole(MemberRole.ADMIN);
        members.get(newOwnerId).changeRole(MemberRole.OWNER);
    }

    /**
     * メンバに含まれるか判定
     *
     * @param memberId 判定対象
     * @return true: 判定対象MemberIdがすでにメンバに存在する
     */
    public boolean hasMember (MemberId memberId) {
        if (memberId == null) throw new IllegalArgumentException("MemberId must not be null.");
        return members.containsKey(memberId);
    }

    /**
     * グループメンバに特定のユーザが含まれるか判定
     *
     * @param userId 判定対象
     * @return true: 判定対象UserIdがすでにメンバに存在する
     */
    public boolean hasUserInMember (UserId userId) {
        if (userId == null) throw new IllegalArgumentException("UserId must not be null.");
        return members.values().stream().anyMatch(e -> e.getUserId().equals(userId));
    }

    /**
     * 指定メンバのスナップショットを取得する
     *
     * @param memberId メンバ指定
     * @return メンバスナップショット
     */
    public MemberSnapshot getMemberSnapshotOf (MemberId memberId) {
        if (memberId == null) throw new IllegalArgumentException("MemberId must not be null.");
        if (!hasMember(memberId)) throw new IllegalArgumentException("Member is not joined in the group.");
        return members.get(memberId).toSnapshot();
    }

    /**
     * すべてのメンバのスナップショットを取得する
     *
     * @return メンバスナップショットのリスト
     */
    public List<MemberSnapshot> getMemberSnapshots () {
        return members.values().stream().map(Member::toSnapshot).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals (Object other) {
        if (!(other instanceof Group)) {
            return false;
        }
        return ((Group) other).getId().equals(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode () {
        return getId().hashCode();
    }
}
