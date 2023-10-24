package com.smartfactory.apiserver.domain.database.entity;

import com.smartfactory.apiserver.common.constant.CommonCode.UserAuthority;
import com.smartfactory.apiserver.common.constant.CommonCode.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_authority")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class UserAuthorityEntity {
    @Id
    @Column(name = "authority_seq")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long authoritySeq;

    @ManyToOne
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private UserEntity user;

    @Column(name = "authority", length = 45)
    @Enumerated(EnumType.STRING)
    private UserAuthority authority;

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


    @Builder
    public UserAuthorityEntity(Long authoritySeq, UserEntity user, UserAuthority authority, Date createAt, String createId, Date updateAt, String updateId) {
        this.authoritySeq = authoritySeq;
        this.user = user;
        this.authority = authority;
        this.createAt = createAt;
        this.createId = createId;
        this.updateAt = updateAt;
        this.updateId = updateId;
    }
}
