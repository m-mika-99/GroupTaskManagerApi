package com.example.GroupTaskManagerApi.application.user;

import com.example.GroupTaskManagerApi.domain.user.UserRepository;
import com.example.GroupTaskManagerApi.domain.user.model.User;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApplicationService {

    private final UserRepository userRepository;

    @Autowired
    public UserApplicationService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * IDからユーザ情報を取得する
     *
     * @param userId 取得対象
     * @return ユーザ情報
     */
    public User findUserById (UserId userId) {
        Optional<User> user = userRepository.findById(userId);
        // TODO: 例外の種類
        return user.orElseThrow(() -> new RuntimeException("user not found"));
    }
}
