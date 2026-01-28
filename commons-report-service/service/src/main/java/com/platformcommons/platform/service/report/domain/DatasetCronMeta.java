package com.platformcommons.platform.service.report.domain;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.report.dto.DatasetCronMetaDTO;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.scheduling.support.CronExpression;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

@Entity  @Getter  @Setter
@Table(name = "dataset_cron_meta")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active")
public class DatasetCronMeta extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<DatasetCronMetaDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "dataSource must not be null")
    @ManyToOne
    @JoinColumn(name = "dataset_id",  nullable = false)
    private Dataset dataset;

    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    @Column(name = "name")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String,String> dataSetCronData;

    private Date startTime;
    private Date endTime;
    private TimeZone timeZone;

    @Column(updatable = false)
    private String method;

    @Builder
    public DatasetCronMeta(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Dataset dataset, String cronExpression, String name,String method,Map<String,String> dataSetCronData,Date startTime,Date endTime, TimeZone timeZone) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.dataset = dataset;
        this.cronExpression = cronExpression;
        this.name = name;
        this.dataSetCronData = dataSetCronData;
        this.method = method;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
    }

    public void init() {
        this.validateDataset();
        this.validateCornExpression();
        this.id = null;
    }

    private void validateCornExpression() {
        if(!CronExpression.isValidExpression(this.cronExpression))
            throw new InvalidInputException("Invalid cron expression");
    }
    private void validateDataset() {
        if(this.dataset==null || this.dataset.getId()==null || this.dataset.getId()==0L)
            throw new InvalidInputException("Dataset is required");
    }

    public void patch(DatasetCronMeta datasetCronMeta, Dataset dataset) {
        if(dataset!=null && datasetCronMeta.dataset!=null &&
           datasetCronMeta.dataset.getId()!=null &&
           !Objects.equals(datasetCronMeta.dataset.getId(), dataset.getId()))
        {
            this.dataset = dataset;

        }
        if(datasetCronMeta.cronExpression!=null){
            this.cronExpression = datasetCronMeta.cronExpression;
        }
        if(datasetCronMeta.name!=null){
            this.name = datasetCronMeta.name;
        }
        if(datasetCronMeta.method!=null){
            this.method = datasetCronMeta.method;
        }
        if(datasetCronMeta.startTime!=null){
            this.startTime = datasetCronMeta.startTime;
        }
        if(datasetCronMeta.endTime!=null){
            this.endTime = datasetCronMeta.endTime;
        }
        if(datasetCronMeta.timeZone!=null){
            this.timeZone = datasetCronMeta.timeZone;
        }

    }
}
