package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ExtraAttributeValueDTO extends BaseTransactionalDTO {

    private Long id;

    private String dataCode;

    private String dataValue;

    private Long sequence;


    @Builder
    public ExtraAttributeValueDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Long id, String dataCode, String dataValue, Long sequence) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.dataCode = dataCode;
        this.dataValue = dataValue;
        this.sequence = sequence;
    }
}
