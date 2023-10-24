package com.smartfactory.apiserver.api.auth.service;

import com.smartfactory.apiserver.api.auth.dto.AuthDTO.SignInRequest;
import com.smartfactory.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import com.smartfactory.apiserver.api.auth.dto.AuthDTO.TokenInfo;

public interface AuthService {
    void signUp(SignUpRequest signUpRequest);
    TokenInfo signIn(SignInRequest signInRequest);
}
