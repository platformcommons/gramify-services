package com.platformcommons.platform.service.iam.facade.v2.impl;



import com.mindtree.bridge.platform.dto.UserWrapperDTO;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.filter.session.CurrentExecutiveDTO;
import com.platformcommons.platform.security.filter.session.TLDUserClient;
import com.platformcommons.platform.security.filter.session.TLDUserContext;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.UserService;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.facade.v2.SessionFacade;
import com.platformcommons.platform.service.iam.facade.v2.mapper.TLDMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionFacadeImpl implements SessionFacade {

    private final TLDMapperUtil tldMapperUtil;
    private final TLDUserClient tldUserClient;
    private final UserService userService;


    @Override
    public CurrentExecutiveDTO getLoggedInUserSessionDetails() {
        CurrentExecutiveDTO currentExecutiveDTO = tldUserClient.getUserInfo(PlatformSecurityUtil.getToken());
        try {
            User user = userService.getUserByLogin(PlatformSecurityUtil.getCurrentUserLogin());
            updateUserContext(currentExecutiveDTO, user);
        } catch (NotFoundException e) {
            UserWrapperDTO userWrapperDTO = tldMapperUtil.getUserWrapperDTO(PlatformSecurityUtil.getCurrentUserId());
            User user = tldMapperUtil.fromDTO(userWrapperDTO.getUserDTO(), PlatformSecurityUtil.getCurrentTenantId());
            User savedUser = userService.addUser(user, null);
            updateUserContext(currentExecutiveDTO, savedUser);
        }
        return currentExecutiveDTO;
    }

    private void updateUserContext(CurrentExecutiveDTO currentExecutiveDTO, User user) {
        TLDUserContext tldUserContext = new TLDUserContext();
        tldUserContext.setUserId(currentExecutiveDTO.getUserContext().getUserId());
        tldUserContext.setUserLoginName(currentExecutiveDTO.getUserContext().getUserLoginName());
        tldUserContext.setFirstName(user.getFirstName());
        tldUserContext.setLastName(user.getLastName());
        tldUserContext.setAuthorities(currentExecutiveDTO.getUserContext().getAuthorities());
        currentExecutiveDTO.setUserContext(tldUserContext);
    }

}
