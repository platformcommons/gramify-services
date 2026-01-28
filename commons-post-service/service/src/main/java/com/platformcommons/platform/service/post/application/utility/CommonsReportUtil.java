package com.platformcommons.platform.service.post.application.utility;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.dto.UserDetailsDTO;
import com.platformcommons.platform.service.post.facade.client.CommonsReportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonsReportUtil {

    @Autowired
    private CommonsReportClient commonsReportClient;

    public List<UserDetailsDTO> getUserDetails(Long currentUserId) {
        StringBuilder params = new StringBuilder();
        params.append("USER_ID=").append(currentUserId);
        return commonsReportClient.getUserDetails(PlatformSecurityUtil.getToken(),
                params.toString()).getBody();
    }
}
