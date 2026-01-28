package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;


@Getter
@NoArgsConstructor
@ToString
public class ExtraAttributesDTO extends BaseTransactionalDTO {

    private Long id;

    @NotNull(message = "attributeCode can not be null")
    private String attributeCode;

    private Set<ExtraAttributeValueDTO> attributeValues;


    @Builder
    public ExtraAttributesDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Long id, String attributeCode, Set<ExtraAttributeValueDTO> attributeValues) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.attributeCode = attributeCode;
        this.attributeValues = attributeValues;
    }
}
