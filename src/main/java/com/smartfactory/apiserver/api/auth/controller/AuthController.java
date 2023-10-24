package com.smartfactory.apiserver.api.auth.controller;


import com.smartfactory.apiserver.api.auth.dto.AuthDTO.SignInRequest;
import com.smartfactory.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import com.smartfactory.apiserver.api.auth.dto.AuthDTO.TokenInfo;
import com.smartfactory.apiserver.api.auth.service.AuthService;
import com.smartfactory.apiserver.common.response.ApiResponseCode;
import com.smartfactory.apiserver.common.response.BaseResponse;
import com.smartfactory.apiserver.common.response.RestApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            log.debug("Request data : " + signUpRequest.toString());
            authService.signUp(signUpRequest);
            RestApiResponse restApiResponse = new RestApiResponse();
            restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
            return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/sign-in", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            TokenInfo token = authService.signIn(signInRequest);
            RestApiResponse restApiResponse = new RestApiResponse();
            restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), token);
            return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/test")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<?> tokentest() {
        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), "TOKEN TEST");
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }
}
