package com.platformcommons.platform.service.report.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "data_source")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DataSource extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<DataSource> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "url must not be null")
    @Column(name = "url")
    private String url;

    @NotNull(message = "user must not be null")
    @Column(name = "user")
    private String user;

    @NotNull(message = "password must not be null")
    @Column(name = "password")
    private String password;

    @NotNull(message = "dataSourceDriver must not be null")
    @Column(name = "data_source_driver")
    private String dataSourceDriver;

    @Column(name = "is_default_data_source")
    private Boolean isDefaultDataSource;

    @Transient
    private boolean isNew;


    @Builder
    public DataSource(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                      String name, String url, String user, String password, String dataSourceDriver,
                      Boolean isDefaultDataSource, boolean isNew) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.url = url;
        this.user = user;
        this.password = password;
        this.dataSourceDriver = dataSourceDriver;
        this.isDefaultDataSource = isDefaultDataSource;
        this.isNew = isNew;
    }

    public void init() {
    }

    public void update(DataSource toBeUpdated) {
    }
}