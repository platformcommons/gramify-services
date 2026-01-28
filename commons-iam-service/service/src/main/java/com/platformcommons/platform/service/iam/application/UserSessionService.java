package com.platformcommons.platform.service.iam.application;


import com.platformcommons.platform.service.iam.domain.UserSession;

public interface UserSessionService {


    void storeSessionDetails(UserSession password);

    void invalidateSessionDetails(String sessionId);
}
