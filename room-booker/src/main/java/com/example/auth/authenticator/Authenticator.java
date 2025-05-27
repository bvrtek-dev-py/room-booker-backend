package com.example.auth.authenticator;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.auth.dto.JwtPayload;
import com.example.auth.factory.JwtPayloadFactory;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;

@Component
public class Authenticator implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtPayloadFactory jwtPayloadFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        JwtPayload jwtPayload = jwtPayloadFactory.make(
            user.getId(),
            user.getEmail()
        );

        return new UsernamePasswordAuthenticationToken(jwtPayload, null, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}