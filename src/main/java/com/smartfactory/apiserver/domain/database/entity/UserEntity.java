package com.smartfactory.apiserver.domain.database.entity;

import com.smartfactory.apiserver.common.constant.CommonCode;
import com.smartfactory.apiserver.common.constant.CommonCode.UserStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "user")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "user_seq")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userSeq;

    @Column(name = "user_id", unique = true, length = 45)
    private String userId;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "refresh_token", length = 128)
    private String refreshToken;

    @Column(name = "refresh_token_expire_at")
    private Long refreshTokenExpireAt;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "email_address", length = 128)
    private String emailAddress;

    @Column(name = "create_at", nullable = false)
    @CreationTimestamp
    private Date createAt;

    @Column(name = "create_id", length = 45, nullable = false)
    private String createId;

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt;

    @Column(name = "update_id", length = 45)
    private String updateId;

    @Column(name = "staff_seq", unique = true)
    private Long staffSeq;

    @Column(name = "user_status", length = 45)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAuthorityEntity> userAuthorityEntities;

    @Builder
    public UserEntity(Long userSeq, String userId, String password, String refreshToken, Long refreshTokenExpireAt, String userName, String phoneNumber, String emailAddress, Date createAt, String createId, Date updateAt, String updateId, Long staffSeq, UserStatus userStatus) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.password = password;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireAt = refreshTokenExpireAt;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.createAt = createAt;
        this.createId = createId;
        this.updateAt = updateAt;
        this.updateId = updateId;
        this.staffSeq = staffSeq;
        this.userStatus = userStatus;
    }
}