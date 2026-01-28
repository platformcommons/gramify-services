package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.worknode.dto.ActorVerticalAssociationDTO;
import com.platformcommons.platform.service.worknode.dto.WorkforceDTO;
import com.platformcommons.platform.service.worknode.dto.WorknodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@FeignClient(
        name = "commons-worknode-service${commons.service.commons-worknode-service.context-path:/commons-worknode-service}",
        contextId = "worknode-service"
)
public interface WorknodeClient {

    @GetMapping(value = "/api/v1/workforce/user/role")
    ResponseEntity<Set<WorkforceDTO>> getWorkforcesByUserIdAndRoleId(@NotNull @Valid @RequestParam(value = "userId") Long userId,
                                                                     @NotNull @Valid @RequestParam(value = "roleId") Long roleId,
                                                                     @RequestParam(value = "workNodeId") Long workNodeId,
                                                                     @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @GetMapping(value = "/api/v1/worknode/name")
    ResponseEntity<WorknodeDTO> getWorknodeByNameAndType(@NotNull  @Valid @RequestParam(value = "name", required = true) String name,
                                                         @NotNull  @Valid @RequestParam(value = "type", required = true) String type,
                                                         @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @PostMapping(value = {"/api/v1/workforce"})
    ResponseEntity<Long> createWorkForce(@RequestParam(value = "worknodeId", required = true) Long worknodeId,
                                         @NotNull @Valid @RequestBody WorkforceDTO body,
                                         @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @PostMapping(value = "/api/v1/actor-vertical-associations/post-patch")
    ResponseEntity<ActorVerticalAssociationDTO> postOrPatchUpdateActorVerticalAssociation(@RequestBody ActorVerticalAssociationDTO actorVerticalAssociationDTO,
                                                                                          @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

}
