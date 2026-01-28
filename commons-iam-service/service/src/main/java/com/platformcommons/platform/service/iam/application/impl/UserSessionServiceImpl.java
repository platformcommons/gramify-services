package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.iam.application.UserSessionService;
import com.platformcommons.platform.service.iam.domain.UserSession;
import com.platformcommons.platform.service.iam.domain.repo.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository userSessionRepository;

    @Override
    public void storeSessionDetails(UserSession password) {
        userSessionRepository.save(password);
    }

    @Override
    public void invalidateSessionDetails(String sessionId) {
        UserSession userSession = userSessionRepository.findBySession(sessionId).orElseThrow(()->new NotFoundException("User session not found"));
        userSession.invalidateSessionDetails();
        userSessionRepository.save(userSession);
    }


}
