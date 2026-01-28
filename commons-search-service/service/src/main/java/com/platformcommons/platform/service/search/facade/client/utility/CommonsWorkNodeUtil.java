package com.platformcommons.platform.service.search.facade.client.utility;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.search.facade.client.WorknodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class CommonsWorkNodeUtil {

    @Autowired
    private WorknodeClient worknodeClient;

    public Set<Long> getAllLeafWorkNodesIdByParentId(Long entityId) {
        Set<Long> worknodeIds = null;
        if(entityId != null) {
            worknodeIds = new HashSet<>();
            worknodeIds.add(entityId);
            worknodeIds.addAll(Objects.requireNonNull(worknodeClient.getLeafWorkNodeIdsForParentWorkNode(entityId,
                    PlatformSecurityUtil.getToken()).getBody()));
        }
        return worknodeIds;
    }
}
