package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@ToString
public class AttributeMetaDTO extends BaseTransactionalDTO {


    private Long id;

    @NotNull(message = "attributeCode can not be null")
    private String attributeCode;

    
    private String dataType;

    private String dataSource;


    private String validationConstraint;

    @Builder
    public AttributeMetaDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt,
                            Long id, String attributeCode, String dataType,String dataSource, String validationConstraint) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.attributeCode = attributeCode;
        this.dataType = dataType;
        this.dataSource = dataSource;
        this.validationConstraint = validationConstraint;
    }
}
