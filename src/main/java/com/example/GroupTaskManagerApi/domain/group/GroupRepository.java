package com.example.GroupTaskManagerApi.domain.group;

import com.example.GroupTaskManagerApi.domain.group.model.Group;
import com.example.GroupTaskManagerApi.domain.group.model.GroupId;

import java.util.Optional;

/**
 * グループ永続化
 */
public interface GroupRepository {

    Optional<Group> findById (GroupId groupId);

    void save (Group fixedGroup);
}
