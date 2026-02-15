package com.example.GroupTaskManagerApi.infra.repository.uroborosql.group.entity;

import com.example.GroupTaskManagerApi.domain.group.model.MemberId;
import com.example.GroupTaskManagerApi.domain.group.model.MemberRole;
import com.example.GroupTaskManagerApi.domain.group.model.MemberSnapshot;
import com.example.GroupTaskManagerApi.domain.group.model.MemberStatus;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

public class MemberEntity {

    private UUID id;
    private UUID userId;
    private UUID groupId;
    private LocalDateTime joinedAt;
    private int roleCode;
    private String displayNameOverride;
    private int statusCode;

    public int getRoleCode () {
        return roleCode;
    }

    public void setRoleCode (int roleCode) {
        this.roleCode = roleCode;
    }

    public LocalDateTime getJoinedAt () {
        return joinedAt;
    }

    public void setJoinedAt (LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public UUID getGroupId () {
        return groupId;
    }

    public void setGroupId (UUID groupId) {
        this.groupId = groupId;
    }

    public UUID getUserId () {
        return userId;
    }

    public void setUserId (UUID userId) {
        this.userId = userId;
    }

    public UUID getId () {
        return id;
    }

    public void setId (UUID id) {
        this.id = id;
    }

    public String getDisplayNameOverride () {
        return displayNameOverride;
    }

    public void setDisplayNameOverride (String displayNameOverride) {
        this.displayNameOverride = displayNameOverride;
    }

    public int getStatusCode () {
        return statusCode;
    }

    public void setStatusCode (int statusCode) {
        this.statusCode = statusCode;
    }

    public MemberSnapshot toSnapshot () {
        return new MemberSnapshot(
                new MemberId(id),
                new UserId(userId),
                MemberRole.from(roleCode),
                MemberStatus.from(statusCode),
                joinedAt
        );
    }
}
