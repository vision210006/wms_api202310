package com.smartfactory.apiserver.domain.database.repository;

import com.smartfactory.apiserver.domain.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUserId(String userId);
}