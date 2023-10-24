package com.smartfactory.apiserver.domain.database.repository;

import com.smartfactory.apiserver.domain.database.entity.UserAuthorityEntity;
import com.smartfactory.apiserver.domain.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthorityEntity, Long> {
}
