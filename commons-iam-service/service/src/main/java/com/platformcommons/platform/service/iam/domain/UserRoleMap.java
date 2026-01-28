package com.platformcommons.platform.service.iam.domain;


import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_role_map",uniqueConstraints = {@UniqueConstraint(columnNames = {"role_code", "user_id"})})
public class UserRoleMap extends BaseTransactionalEntity {

    @Id
    @GenericGenerator(name = "sequence_role_map_id", strategy = "com.platformcommons.platform.service.iam.application.config.TLDIdentifierGenerator")
    @GeneratedValue(generator = "sequence_role_map_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code", referencedColumnName = "role_code",nullable = false)
    private Role role;

    @Builder
    public UserRoleMap(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, User user, Role role) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.user = user;
        this.role = role;
        if(user.getUserRoleMaps()==null){
            user.setUserRoleMaps(new HashSet<>());
            user.getUserRoleMaps().add(this);
        }
    }

    /**
     * This constructor is used by JPA to provide selected projections for getting selected id reference
     * Don't change sequence of param.
     */
    public UserRoleMap(Long id) {
        this.id = id;
    }
}
