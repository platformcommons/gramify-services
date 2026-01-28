package com.platformcommons.platform.service.assessment.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssesseSyncedDTO extends BaseTransactionalDTO {

    private Long assesseeId;

    @Builder
    public AssesseSyncedDTO(Long assesseeId) {
        this.assesseeId = assesseeId;
    }
}
