package com.example.GroupTaskManagerApi.infra.repository.onMemoryRepository;

import com.example.GroupTaskManagerApi.domain.auth.AuthRepository;
import com.example.GroupTaskManagerApi.domain.auth.model.AuthUser;
import com.example.GroupTaskManagerApi.domain.auth.model.Email;
import com.example.GroupTaskManagerApi.domain.auth.model.PasswordHash;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthUserOnMemoryRepository implements AuthRepository {

    private final PasswordEncoder passwordEncoder;

    private final List<AuthUser> users = new ArrayList();

    @Autowired
    public AuthUserOnMemoryRepository (
            PasswordEncoder passwordEncoder
    ) {
        this.passwordEncoder = passwordEncoder;
        users.add(
                AuthUser.createNew(
                        new Email("user@example.com"),
                        new PasswordHash(
                                passwordEncoder.encode("string")
                        ),
                        UserId.fromString("f2566ee5-c7ef-56de-818a-240fb7b3b330")
                )
        );


    }

    @Override
    public Optional<AuthUser> findByEmail (String email) {
        return this.users.stream().filter(u -> u.getEmail().value().equals(email)).findFirst();
    }

    @Override
    public void save (AuthUser user) {
        users.removeIf(u ->
                u.getId().value().equals(user.getId().value())
        );

        users.add(user);
    }
}
