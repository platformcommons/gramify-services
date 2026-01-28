package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.UserMetaData;

import java.util.Set;

public interface UserMetaDataService {
    Set<UserMetaData> getByMetaKeyInBulkForLoggedInUser(Set<String> metaKeys
            , Long userId);

    void postOrUpdateForUser(Set<UserMetaData> userMetaData, Long userId);

}
