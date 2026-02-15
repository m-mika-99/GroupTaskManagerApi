package com.example.GroupTaskManagerApi.infra.repository.uroborosql.group.entity;

import com.example.GroupTaskManagerApi.domain.group.model.Group;
import com.example.GroupTaskManagerApi.domain.group.model.MemberSnapshot;

import java.util.List;
import java.util.UUID;

public class GroupEntity {

    private UUID id;
    private String name;
    private String description;

    public UUID getId () {
        return id;
    }

    public void setId (UUID id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Group toDomain (List<MemberSnapshot> memberSnapshots) {
        return Group.reconstruct(
                id.toString(),
                name,
                description,
                memberSnapshots
        );
    }

}
