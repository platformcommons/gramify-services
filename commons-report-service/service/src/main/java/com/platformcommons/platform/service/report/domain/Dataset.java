package com.platformcommons.platform.service.report.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "dataset")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Dataset extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<Dataset> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;


    @NotNull(message = "dataSource must not be null")
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private DataSource dataSource;

    @Column(name = "name", unique = true)
    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "queryText must not be null")
    @Column(name = "query_text", columnDefinition = "TEXT")
    private String queryText;

    @Column(name = "default_param_map")
    private String defaultParamMap;

    @Column(name = "dataset_type")
    private String datasetType;

    @Column(name = "is_cron_enabled")
    private Boolean isCronEnabled;

    @Column(name = "is_raw_input_upload")
    private Long isRawInputUpload;

    @Transient
    private boolean isNew;

    @ManyToMany
    @JoinTable(name = "dataset_group_dataset", joinColumns = @JoinColumn(name = "dataset_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dataset_group_id", referencedColumnName = "id"))
    private Set<DatasetGroup> datasetGroups;


    @OneToMany(cascade = CascadeType.ALL,fetch =  FetchType.LAZY)
    @JoinColumn(name = "dataset_id", referencedColumnName = "id")
    private Set<DataSetParam> dataSetParams;

    @Column
    @OneToMany(fetch =  FetchType.LAZY,mappedBy = "dataset")
    private Set<DatasetCronMeta> datasetCronMetas;

    @Column(name = "is_exposed")
    private Boolean isExposed;


    @Builder
    public Dataset(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                   DataSource dataSource, String name, String queryText, String defaultParamMap, String datasetType,
                   Boolean isCronEnabled, Long isRawInputUpload, boolean isNew, Set<DatasetGroup> datasetGroups,Boolean isExposed,
                    Set<DataSetParam> dataSetParams, Set<DatasetCronMeta> datasetCronMetas) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.dataSource = dataSource;
        this.name = name;
        this.queryText = queryText;
        this.defaultParamMap = defaultParamMap;
        this.datasetType = datasetType;
        this.isCronEnabled = isCronEnabled;
        this.isRawInputUpload = isRawInputUpload;
        this.isNew = isNew;
        this.datasetGroups = datasetGroups;
        this.dataSetParams = dataSetParams;
        this.isExposed = isExposed !=null ? isExposed : Boolean.FALSE;
        this.datasetCronMetas = datasetCronMetas;
    }

    public void init(DataSource dataSource) {
        this.id = null;
        this.dataSource = dataSource;
    }

    public void update(Dataset toBeUpdated) {
        this.queryText = toBeUpdated.queryText;
        this.isCronEnabled = toBeUpdated.isCronEnabled;
        this.defaultParamMap = toBeUpdated.defaultParamMap;
        this.isExposed = toBeUpdated.getIsExposed();
    }

    public boolean getIsCronEnabled(){
        return this.isCronEnabled!=null && this.isCronEnabled;
    }


}