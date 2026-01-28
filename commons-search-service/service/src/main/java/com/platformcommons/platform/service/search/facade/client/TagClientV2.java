package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@FeignClient(
    name = "commons-domain-service${commons.service.commons-domain-service.context-path:/commons-domain-service}",
    contextId = "search-service-tag-client"
)
public interface TagClientV2 {

    @RequestMapping(
        value = {"/api/v1/tag"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<TagDTO> getTag(@RequestParam(value = "id",required = true) @NotNull @Valid Long id,
                                  @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);

    @RequestMapping(
        value = {"/api/v1/tag/codes"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<Set<TagDTO>> getTagByCodes(@RequestParam(value = "codes",required = true) @NotNull @Valid Set<String> codes,
                                              @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);

    @RequestMapping(
        value = {"/api/v1/tag/page"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<PageDTO<TagDTO>> getTagPage(@RequestParam(value = "page",required = true) @NotNull @Valid Integer page,
                                               @RequestParam(value = "size",required = true) @NotNull @Valid Integer size,
                                               @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);


    @RequestMapping(
            value = {"/api/v1/tags"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<Set<TagDTO>> getTags(@RequestParam(value = "page",required = true) @NotNull @Valid Integer page,
                                               @RequestParam(value = "size",required = true) @NotNull @Valid Integer size,
                                               @RequestParam(value = "type",required = false) @NotNull @Valid String type,
                                               @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);



    @RequestMapping(
            value = {"/api/v1/tags"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<PageDTO<TagDTO>> getTagsV2(@RequestParam Integer page,
                                                     @RequestParam Integer size,
                                                     @RequestParam(required = false) String context,
                                                     @RequestParam(required = false) Boolean isRoot,
                                                     @RequestParam(required = false) String type,
                                                     @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId
    );

}
