package com.platformcommons.platform.service.iam.facade.v2;

import com.platformcommons.platform.security.filter.session.CurrentExecutiveDTO;

public interface SessionFacade {

    CurrentExecutiveDTO getLoggedInUserSessionDetails();
}
