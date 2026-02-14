package com.example.GroupTaskManagerApi.infra.repository.onMemoryRepository;

import com.example.GroupTaskManagerApi.domain.user.UserRepository;
import com.example.GroupTaskManagerApi.domain.user.model.User;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserOnMemoryRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    public UserOnMemoryRepository () {
        users.add(
                User.reconstruct(
                        "f2566ee5-c7ef-56de-818a-240fb7b3b330",
                        "サンプル太郎"
                )
        );
    }

    @Override
    public Optional<User> findById (UserId userId) {
        return users.stream().filter(u -> u.getId().equals(userId)).findFirst();
    }
}
