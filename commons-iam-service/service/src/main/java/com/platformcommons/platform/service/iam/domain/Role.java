package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.iam.exception.ConflictException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"role_code", "tenant_id"})})
public class Role extends BaseTransactionalEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NaturalId
    @Column(nullable = false, name = "role_code", unique = true, updatable = false)
    private String code;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;


    @ManyToMany
    @JoinTable(name = "role_authority_map",
            joinColumns = @JoinColumn(name = "role", referencedColumnName = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "authority", referencedColumnName = "code")
    )
    private Set<Authority> authorities = new LinkedHashSet<>();

    //set of app-role/process-role for runtime granted authority
    @ManyToMany
    @JoinTable(name = "inherited_role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Role> inheritedRolePermissions;

    @Builder
    public Role(Long id , String code, String roleName, RoleType roleType, Set<Role> inheritedRolePermissions, Set<Authority> authorities) {
        this.id = id;
        this.code = code;
        this.roleName = roleName;
        this.inheritedRolePermissions=inheritedRolePermissions;
        this.roleType=roleType;
        this.authorities = authorities;
    }

    public void update(Role roleToBeUpdated) {
        this.roleName = roleToBeUpdated.roleName;
    }

    public void addAuthority(Authority authorityToBeAdded) {
        if (authorityToBeAdded != null) {
            this.authorities.add(authorityToBeAdded);
        }
    }

    public void removeAuthority(Authority authorityToBeAdded) {
        if (authorityToBeAdded != null && authorities.contains(authorityToBeAdded)) {
            this.authorities.remove(authorityToBeAdded);
        }
    }

    public void init() {
        this.id = null;
        this.authorities = Collections.emptySet();
        if(this.roleType==null || this.roleType==RoleType.TENANT)
            this.roleType=RoleType.TENANT;
        else
            PlatformSecurityUtil.validatePlatformAdmin();
    }

    public void inheritCheck() {
        if(roleType==null)this.roleType=RoleType.TENANT;
        if(this.roleType==RoleType.TENANT){
            if(Objects.nonNull(this.authorities) && !this.authorities.isEmpty())  {
                throw new InvalidInputException("Tenant role must not have authorities");
            }
            if(inheritedRolePermissions!=null && !inheritedRolePermissions.isEmpty()){
                inheritedRolePermissions.forEach(role -> {
                    if(!(role.roleType==RoleType.APP_ROLE || role.roleType==RoleType.PROCESS))
                        throw new ConflictException("Tenant role must inherit only app and process role.");
                    role.setInheritedRolePermissions(new HashSet<>());
                });
            }
        }
        else{
            if(inheritedRolePermissions!=null && !inheritedRolePermissions.isEmpty()){
                throw new ConflictException(roleType.name()+" role type can't inherit role permissions.");
            }
        }
    }

    public void initV2() {
        this.id = null;
        this.authorities = Objects.isNull(this.authorities)?Collections.emptySet():this.authorities;
    }

    public void updateAuthorities(Set<Authority> authorities) {
        this.getAuthorities().addAll(authorities);
    }

    public enum RoleType {
        TENANT, PROCESS, APP_ROLE
    }

    /**
     * This constructor is used by JPA to provide selected projections for getting role id reference
     * Don't change sequence of param.
     */
    public Role(Long id,String code) {
        this.id = id;
        this.code = code;
    }

}
