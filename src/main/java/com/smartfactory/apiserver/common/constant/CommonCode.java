package com.smartfactory.apiserver.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommonCode {
    public enum UserStatus{
        USE, //사용
        WITHDRAW,   //탈퇴
        DORMANT;    //휴면
    }

    @Getter
    @AllArgsConstructor
    public enum UserAuthority{
        ROLE_SUPER_ADMIN("슈퍼 관리자"),
        ROLE_STAFF("매니저"),
        ROLE_WORKER("근로자"),
        ROLE_GENERAL("일반사용자");
        private String desc;
    }
}
