package com.smartfactory.apiserver.domain.database.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smartfactory.apiserver.api.user.dto.UserDTO.UserInfoResponse;
import com.smartfactory.apiserver.domain.database.entity.QUserAuthorityEntity;
import com.smartfactory.apiserver.domain.database.entity.QUserEntity;
import com.smartfactory.apiserver.domain.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRespository {

    private final JPAQueryFactory queryFactory;

    @Override
    public UserInfoResponse findUserInfoByUserSeq(Long userSeq) {
        return null;
    }
}
