package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.dto.UserMetaDataDTO;

import java.util.Set;

public interface UserMetaDataFacade {
    Set<UserMetaDataDTO> getByMetaKeyInBulkForLoggedInUser(Set<String> metaKeys);

    void postOrUpdateInBulkForLoggedInUser(Set<UserMetaDataDTO> userMetaDataDTOS);

    void postOrUpdateInBulkForUser(Set<UserMetaDataDTO> userMetaDataDTOS,Long userId);
}
