package com.example.auth.authenticator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.auth.dto.JwtPayload;
import com.example.auth.factory.JwtPayloadFactory;
import com.example.auth.factory.UsernamePasswordAuthenticationTokenFactory;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;
import com.example.user.role.UserRole;

@ExtendWith(MockitoExtension.class)
class AuthenticatorTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtPayloadFactory jwtPayloadFactory;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsernamePasswordAuthenticationTokenFactory authenticationTokenFactory;

    @InjectMocks
    private Authenticator authenticator;

    @Test
    void authenticate_shouldReturnAuthentication_whenCredentialsAreCorrect() {
        // given
        String email = "test@example.com";
        String password = "plain";
        String encodedPassword = "encoded";
        UserEntity user = new UserEntity(1L, "user", encodedPassword, email, UserRole.USER);
        JwtPayload payload = new JwtPayload(1L, email);
        UsernamePasswordAuthenticationToken expectedToken = new UsernamePasswordAuthenticationToken(payload, null);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtPayloadFactory.make(user.getId(), user.getEmail())).thenReturn(payload);
        when(authenticationTokenFactory.make(payload, null)).thenReturn(expectedToken);

        // when
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
        Authentication result = authenticator.authenticate(auth);

        // then
        assertNotNull(result);
        assertEquals(expectedToken, result);
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(password, encodedPassword);
        verify(jwtPayloadFactory).make(user.getId(), user.getEmail());
        verify(authenticationTokenFactory).make(payload, null);
        verifyNoMoreInteractions(userRepository, passwordEncoder, jwtPayloadFactory, authenticationTokenFactory);
    }

    @Test
    void authenticate_shouldThrowException_whenUserNotFound() {
        // given
        String email = "notfound@example.com";
        String password = "pass";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);

        // when & then
        assertThrows(BadCredentialsException.class, () -> authenticator.authenticate(auth));
        verify(userRepository).findByEmail(email);
        verifyNoMoreInteractions(userRepository, passwordEncoder, jwtPayloadFactory, authenticationTokenFactory);
    }

    @Test
    void authenticate_shouldThrowException_whenPasswordIncorrect() {
        // given
        String email = "test@example.com";
        String password = "wrong";
        String encodedPassword = "encoded";
        UserEntity user = new UserEntity(1L, "user", encodedPassword, email, UserRole.USER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);

        // when & then
        assertThrows(BadCredentialsException.class, () -> authenticator.authenticate(auth));
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(password, encodedPassword);
        verifyNoMoreInteractions(userRepository, passwordEncoder, jwtPayloadFactory, authenticationTokenFactory);
    }
}
