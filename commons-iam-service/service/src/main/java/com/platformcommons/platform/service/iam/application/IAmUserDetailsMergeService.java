package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.security.token.PlatformToken;

public interface IAmUserDetailsMergeService {
    void addUserContext(PlatformToken platformToken);
}
