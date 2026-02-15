package com.example.GroupTaskManagerApi.application.auth;

import com.example.GroupTaskManagerApi.domain.auth.AuthRepository;
import com.example.GroupTaskManagerApi.domain.auth.PasswordHashEncoder;
import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;
import com.example.GroupTaskManagerApi.domain.auth.model.Email;
import com.example.GroupTaskManagerApi.domain.auth.model.PasswordHash;
import com.example.GroupTaskManagerApi.domain.group.GroupRepository;
import com.example.GroupTaskManagerApi.domain.group.model.Group;
import com.example.GroupTaskManagerApi.domain.group.model.GroupId;
import com.example.GroupTaskManagerApi.domain.user.UserRepository;
import com.example.GroupTaskManagerApi.domain.user.model.User;
import com.example.GroupTaskManagerApi.infra.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthApplicationService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordHashEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthApplicationService (
            AuthRepository authRepository,
            UserRepository userRepository,
            GroupRepository groupRepository,
            PasswordHashEncoder encoder,
            JwtTokenProvider jwtTokenProvide
    ) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.encoder = encoder;
        this.jwtTokenProvider = jwtTokenProvide;
    }

    public String login (String email, String rawPassword) {

        AuthUser user = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("invalid email or password"));

        user.verifyPassword(rawPassword, encoder);

        user.loginSucceeded();

        authRepository.save(user);

        return jwtTokenProvider.createToken(user.getUserId().value());
    }

    public void signup (String email, String rawPassword, String displayName) {
        Optional<AuthUser> emailDuplicationUser = authRepository.findByEmail(email);

        if (emailDuplicationUser.isPresent()) {
            // TODO 例外種別
            throw new RuntimeException("Email already registered");
        }

        User newUser = User.createNew(displayName);
        AuthUser newAuthUser = AuthUser.createNew(
                new Email(email),
                new PasswordHash(encoder.encode(rawPassword)),
                newUser.getId()
        );
        // TODO: とりあえず固定グループのメンバにする
        Group fixedGroup = groupRepository.findById(GroupId.fromString("33333333-1111-1111-1111-000000000001")).get();
        fixedGroup.addMember(newUser.getId());

        // TODO: @Transactionalがきかないのでいったん一個のRepositoryメソッドに原子性のある処理はまとめる
        authRepository.saveNewUser(newAuthUser, newUser, fixedGroup);
    }
}
