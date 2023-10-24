package com.smartfactory.apiserver.api.auth.dto;

import com.smartfactory.apiserver.common.constant.CommonCode.UserStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class AuthDTO {
    @Data
    @Builder
    public static class TokenInfo{
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpireAt;
    }

    @Data
    public static class SignUpRequest{
        @NotBlank(message = "userId 필수값입니다.")
        private String userId;
        @NotBlank(message = "password 필수값입니다.")
        private String password;
        @NotBlank(message = "userName 필수값입니다.")
        private String userName;
        @NotBlank(message = "phoneNumber 필수값입니다.")
        private String phoneNumber;
        @NotBlank(message = "emailAddress 필수값입니다.")
        private String emailAddress;
    }

    @Data
    public static class SignInRequest{
        @NotBlank(message = "userId 필수값입니다.")
        private String userId;
        @NotBlank(message = "password 필수값입니다.")
        private String password;
    }
}
