package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"child_role",
        "parent_role", "role_hierarchy_function", "tenant_id"})})
public class RoleHierarchyStructure extends BaseTransactionalEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "role_hierarchy_function", nullable = false, updatable = false)
    private String function;

    @ManyToOne
    @JoinColumn(nullable = false, name = "child_role", referencedColumnName = "role_code")
    private Role role;

    @ManyToOne
    @JoinColumn(nullable = false, name = "parent_role", referencedColumnName = "role_code")
    private Role parentRole;

    @Builder
    public RoleHierarchyStructure(
            Long id, String function, Role role, Role parentRole) {
        this.id = id;
        this.function = function;
        this.role = role;
        this.parentRole = parentRole;
    }

    public void init() {
        this.id = null;
    }
}
