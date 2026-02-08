package com.example.user.use_case;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.common.exception.ObjectNotFoundException;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserGetById {
    private final UserRepository userRepository;

    public UserEntity execute(@NotNull Long id) {
        return userRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }
}
