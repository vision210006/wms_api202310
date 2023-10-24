package com.smartfactory.apiserver.domain.database.repository.querydsl;

import com.smartfactory.apiserver.api.user.dto.UserDTO.UserInfoResponse;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public interface CustomUserRespository {
    UserInfoResponse findUserInfoByUserSeq(Long userSeq);
}
