package com.example.user.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.exception.ObjectNotFoundException;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;

@Component
public class UserGetById {
    @Autowired
    private UserRepository userRepository;

    public UserEntity execute(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException());
    }
}
