package com.platformcommons.platform.service.app.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import com.platformcommons.platform.service.entity.base.BaseEntity;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "app_config_scope_master")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AppConfigScopeMaster extends BaseEntity implements DomainEntity<AppConfigScopeMaster> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "code", unique = true)
    @NotNull(message = "code must not be null")
    private String code;

    @Column(name = "priority", unique = true)
    @NotNull(message = "priority must not be null")
    private Long priority;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "data_type")
    private String dataType;


    @Builder
    public AppConfigScopeMaster(Long id, String code, Long priority, String name,
                                String description, String dataType) {
        this.id = id;
        this.code = code;
        this.priority = priority;
        this.name = name;
        this.description = description;
        this.dataType = dataType;
    }
}