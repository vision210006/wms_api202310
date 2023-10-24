package com.smartfactory.apiserver.common.exception.auth;

import com.smartfactory.apiserver.common.response.ApiResponseCode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AuthException extends RuntimeException{
    private ApiResponseCode responseCode;
    private String message;

    @Builder
    public AuthException(ApiResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
        this.message = message;
    }
}
