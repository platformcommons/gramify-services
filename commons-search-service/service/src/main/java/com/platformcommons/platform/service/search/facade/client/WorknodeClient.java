package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(
        name = "commons-worknode-service${commons.service.commons-worknode-service.context-path:/commons-worknode-service}",
        contextId = "worknode-service")
public interface WorknodeClient {

    @RequestMapping(value = "/api/v1/worknode/parent-worknode-id/{parentWorkNodeId}/workforce/userIds",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<Long>> getUserIdsFromWorkForceChildWorkNodeIdsAndParentWorkNode(@PathVariable("parentWorkNodeId") Long parentWorkNodeId,
                                                                                       @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @RequestMapping(value = "/api/v1/worknode/parent-worknode-id/{parentWorknodeId}/ids",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Long>> getLeafWorkNodeIdsForParentWorkNode(@PathVariable("parentWorknodeId") Long parentWorknodeId,
                                                                    @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);


    @RequestMapping(value = "/api/v1/workforce/worknode/ids",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<Long>> getWorkNodeForWorkforce(@RequestParam(required = false) Long userId,
                                                       @RequestParam(required = false) Boolean isDeduced,
                                                       @RequestParam(required = false) String deducedWorknodeType,
                                                       @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @RequestMapping(value = "/api/v2/workforce/worknode/ids",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Long>> getWorkNodeIdsAssignedToUser(@RequestParam Long userId,
                                                            @RequestHeader(name = PlatformSecurityConstant.AUTHORIZATION) String apikey);

}