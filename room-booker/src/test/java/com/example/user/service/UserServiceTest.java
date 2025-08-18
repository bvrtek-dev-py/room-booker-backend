package com.example.user.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserResponse;
import com.example.user.entity.UserEntity;
import com.example.user.mapper.UserEntityMapper;
import com.example.user.mapper.UserResponseMapper;
import com.example.user.repository.UserRepository;
import com.example.user.role.UserRole;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserEntityMapper userEntityMapper;
    @Mock
    private UserResponseMapper userResponseMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers() {
        // given
        UserEntity user1 = new UserEntity(1L, "user1", "pass1", "user1@example.com", UserRole.USER);
        UserEntity user2 = new UserEntity(2L, "user2", "pass2", "user2@example.com", UserRole.SUPERUSER);
    
        UserResponse response1 = new UserResponse(1L, "user1", "user1@example.com", UserRole.USER);
        UserResponse response2 = new UserResponse(2L, "user2", "user2@example.com", UserRole.SUPERUSER);
    
        List<UserEntity> entities = List.of(user1, user2);
        List<UserResponse> expectedResponses = List.of(response1, response2);
    
        when(userRepository.findAll()).thenReturn(entities);
        when(userResponseMapper.map(user1)).thenReturn(response1);
        when(userResponseMapper.map(user2)).thenReturn(response2);
    
        // when
        List<UserResponse> actualResponses = userService.getAll();
    
        // then
        assertNotNull(actualResponses);
        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses, actualResponses);
    
        verify(userRepository, times(1)).findAll();
        verify(userResponseMapper, times(1)).map(user1);
        verify(userResponseMapper, times(1)).map(user2);
    }
    

    @Test
    void testRegisterUser() {
        // given
        UserCreateRequest request = new UserCreateRequest(
            "testUser", "plainPassword", "test@example.com"
        );

        String encodedPassword = "encodedPassword";
        UserEntity mappedEntity = new UserEntity(null, "testUser", encodedPassword, "test@example.com", UserRole.USER);
        UserEntity savedEntity = new UserEntity(1L, "testUser", encodedPassword, "test@example.com", UserRole.USER);
        UserResponse expectedResponse = new UserResponse(1L, "testUser", "test@example.com", UserRole.USER);

        when(passwordEncoder.encode("plainPassword")).thenReturn(encodedPassword);
        when(userEntityMapper.map(any(UserCreateRequest.class), eq(UserRole.USER))).thenReturn(mappedEntity);
        when(userRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(userResponseMapper.map(savedEntity)).thenReturn(expectedResponse);

        // when
        UserResponse actualResponse = userService.register(request);

        // then
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getRole(), actualResponse.getRole());

        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userEntityMapper, times(1)).map(any(UserCreateRequest.class), eq(UserRole.USER));
        verify(userRepository, times(1)).save(mappedEntity);
        verify(userResponseMapper, times(1)).map(savedEntity);
    }

    @Test
    void testUpdateUser() {
        // given
        Long userId = 1L;
        UserUpdateRequest updateRequest = new UserUpdateRequest("newUsername");

        UserEntity existingUser = new UserEntity(userId, "oldUsername", "encodedPass", "old@example.com", UserRole.USER);
        UserEntity mappedUser = new UserEntity(userId, "oldUsername", "encodedPass", "newEmail@example.com", UserRole.USER);
        UserEntity savedUser = new UserEntity(userId, "oldUsername", "encodedPass", "newEmail@example.com", UserRole.USER);
        UserResponse expectedResponse = new UserResponse(userId, "oldUsername", "newEmail@example.com", UserRole.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userEntityMapper.map(updateRequest, existingUser)).thenReturn(mappedUser);
        when(userRepository.save(mappedUser)).thenReturn(savedUser);
        when(userResponseMapper.map(savedUser)).thenReturn(expectedResponse);

        // when
        UserResponse actualResponse = userService.update(userId, updateRequest);

        // then
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getRole(), actualResponse.getRole());

        verify(userRepository, times(1)).findById(userId);
        verify(userEntityMapper, times(1)).map(updateRequest, existingUser);
        verify(userRepository, times(1)).save(mappedUser);
        verify(userResponseMapper, times(1)).map(savedUser);
    }

    @Test
    void testDeleteUser() {
        // given
        Long userId = 1L;

        // when
        userService.delete(userId);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }
}