package com.platformcommons.platform.service.iam.facade.assembler.obo;

import com.platformcommons.platform.service.iam.domain.AppDetail;
import com.platformcommons.platform.service.iam.dto.AppDetailDTO;

public interface AppDetailDTOIamAssembler {

    AppDetail fromDTO(AppDetailDTO appDetailDTO);

    AppDetailDTO toDTO(AppDetail appDetail);

}
