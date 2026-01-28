package com.platformcommons.platform.service.report.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.Map;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import com.platformcommons.platform.service.dto.commons.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Validated
public class DatasetCronMetaDTO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    private DatasetDTO dataset;
    private String cronExpression;
    private Map<String,String> dataSetCronData;
    private String name;
    private String method;
    private Date startTime;
    private Date endTime;
    private String timeZone;

    @Builder
    public DatasetCronMetaDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt,Long id, DatasetDTO dataset, String cronExpression, Map<String,String> dataSetCronData, String name, String method, Date startTime, Date endTime, String timeZone) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.dataset = dataset;
        this.cronExpression = cronExpression;
        this.dataSetCronData = dataSetCronData;
        this.name = name;
        this.method = method;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
    }
}
