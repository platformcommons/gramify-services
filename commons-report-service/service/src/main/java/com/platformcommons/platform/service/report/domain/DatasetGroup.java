package com.platformcommons.platform.service.report.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "dataset_group")
@NoArgsConstructor
public class DatasetGroup extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<DatasetGroup> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code",unique = true)
    private String code;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dataset_group_dataset", joinColumns = @JoinColumn(name = "dataset_group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dataset_id", referencedColumnName = "id"))
    private Set<Dataset> datasets;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "allowed_tenants",referencedColumnName = "id")
    private Set<TenantInfo> allowedTenants;

    @Builder
    public DatasetGroup(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                        String name, String code, Set<Dataset> datasets, Set<TenantInfo> allowedTenants) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.code = code;
        this.datasets = datasets;
        this.allowedTenants = allowedTenants;
    }

    public void init(){
        this.id = null;
    }

    public void update(DatasetGroup datasetGroup){}
}
