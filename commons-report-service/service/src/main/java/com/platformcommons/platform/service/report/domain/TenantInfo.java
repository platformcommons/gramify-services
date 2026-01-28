package com.platformcommons.platform.service.report.domain;

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
@Table(name = "tenant_info")
@NoArgsConstructor
public class TenantInfo extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantInfo> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tenant_id")
    private Long tenantId;

    @ManyToMany(mappedBy = "allowedTenants")
    private Set<DatasetGroup> datasetGroups;


    @Builder
    public TenantInfo(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                      String name, Long tenantId, Set<DatasetGroup> datasetGroups) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.tenantId = tenantId;
        this.datasetGroups = datasetGroups;
    }

    public void init(){
        this.id = null;
    }

    public void update(TenantInfo toBeUpdate){}
}
