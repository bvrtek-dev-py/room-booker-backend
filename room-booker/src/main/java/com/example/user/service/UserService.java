package com.example.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private UserResponseMapper userResponseMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userResponseMapper::map)
                .toList();
    }

    public UserResponse register(UserCreateRequest request) {
        isUsernameTaken(request.getUsername());

        final String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserCreateRequest finalRequest = request.with(Optional.of(encodedPassword), Optional.empty(), Optional.empty());

        UserEntity user = userEntityMapper.map(finalRequest, UserRole.USER);
        UserEntity savedUser = userRepository.save(user);

        return userResponseMapper.map(savedUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse update(Long id, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
        
        UserEntity mappedUser = userEntityMapper.map(request, user);
        UserEntity updatedUser = userRepository.save(mappedUser);

        return userResponseMapper.map(updatedUser);
    }

    private void isUsernameTaken(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ObjectAlreadyExistsException();
        }
    }
}