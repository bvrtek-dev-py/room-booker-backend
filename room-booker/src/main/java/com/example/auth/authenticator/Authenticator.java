package com.example.auth.authenticator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.auth.dto.JwtPayload;
import com.example.auth.factory.JwtPayloadFactory;
import com.example.auth.factory.UsernamePasswordAuthenticationTokenFactory;
import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class Authenticator implements AuthenticationProvider {
    private final UserRepository userRepository;

    private final JwtPayloadFactory jwtPayloadFactory;

    private final PasswordEncoder passwordEncoder;

    private final UsernamePasswordAuthenticationTokenFactory authenticationTokenFactory;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        JwtPayload jwtPayload = jwtPayloadFactory.make(user.getId(), user.getEmail());

        return authenticationTokenFactory.make(jwtPayload, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
