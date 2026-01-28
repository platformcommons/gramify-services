package com.platformcommons.platform.service.report.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "dataset_change_log")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DataSetChangeLog extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<DataSetChangeLog> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dataset_id", nullable = false)
    private Dataset dataset;

    @Column(name = "name")
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


    @Builder
    public DataSetChangeLog(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Dataset dataset, String name, String queryText, String defaultParamMap, String datasetType, Boolean isCronEnabled, Long isRawInputUpload) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.dataset = dataset;
        this.name = name;
        this.queryText = queryText;
        this.defaultParamMap = defaultParamMap;
        this.datasetType = datasetType;
        this.isCronEnabled = isCronEnabled;
        this.isRawInputUpload = isRawInputUpload;
    }

    public DataSetChangeLog(Dataset fetchedDataset) {
        this.id=0L;
        this.dataset = fetchedDataset;
        this.name = fetchedDataset.getName();
        this.queryText = fetchedDataset.getQueryText();
        this.defaultParamMap = fetchedDataset.getDefaultParamMap();
        this.datasetType = fetchedDataset.getDatasetType();
        this.isCronEnabled = fetchedDataset.getIsCronEnabled();
        this.isRawInputUpload = fetchedDataset.getIsRawInputUpload();
    }
}
