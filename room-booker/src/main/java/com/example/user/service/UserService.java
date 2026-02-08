package com.example.user.service;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.common.exception.ObjectAlreadyExistsException;
import com.example.common.exception.ObjectNotFoundException;
import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserResponse;
import com.example.user.entity.UserEntity;
import com.example.user.mapper.UserEntityMapper;
import com.example.user.mapper.UserResponseMapper;
import com.example.user.repository.UserRepository;
import com.example.user.role.UserRole;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserEntityMapper userEntityMapper;

    private final UserResponseMapper userResponseMapper;

    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userResponseMapper::map).toList();
    }

    public UserResponse register(@NotNull UserCreateRequest request) {
        isUsernameTaken(request.getUsername());

        final String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserCreateRequest finalRequest = request.with(encodedPassword, null, null);

        UserEntity user = userEntityMapper.map(finalRequest, UserRole.USER);
        UserEntity savedUser = userRepository.save(user);

        return userResponseMapper.map(savedUser);
    }

    public void delete(@NotNull Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse update(@NotNull Long userId, @NotNull UserUpdateRequest request) {
        UserEntity user = userRepository.findById(userId).orElseThrow(ObjectNotFoundException::new);

        UserEntity mappedUser = userEntityMapper.map(request, user);
        UserEntity updatedUser = userRepository.save(mappedUser);

        return userResponseMapper.map(updatedUser);
    }

    private void isUsernameTaken(@NotNull String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ObjectAlreadyExistsException();
        }
    }
}
