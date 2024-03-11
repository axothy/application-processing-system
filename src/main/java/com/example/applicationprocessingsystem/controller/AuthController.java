package com.example.applicationprocessingsystem.controller;

import com.example.applicationprocessingsystem.auth.AuthApi;
import com.example.applicationprocessingsystem.auth.model.dto.AuthenticationRequest;
import com.example.applicationprocessingsystem.auth.model.dto.AuthenticationResponse;
import com.example.applicationprocessingsystem.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<AuthenticationResponse> apiAuthLoginPost(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.createToken(authentication);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @Override
    public ResponseEntity<String> apiAuthLogoutGet() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Ok");
    }
}
