//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.platformcommons.platform.service.domain.client;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "commons-domain-service${commons.service.commons-domain-service.context-path:}",
    contextId = "TagClientV2"
)
public interface TagClientV2 {
    @RequestMapping(
        value = {"/api/v1/tag"},
        method = {RequestMethod.DELETE}
    )
    ResponseEntity<Void> deleteTag(@RequestParam(value = "id",required = true) @NotNull @Valid Long id, @RequestParam(value = "reason",required = true) @NotNull @Valid String reason);

    @RequestMapping(
        value = {"/api/v1/tag"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<TagDTO> getTag(@RequestParam(value = "id",required = true) @NotNull @Valid Long id);

    @RequestMapping(
        value = {"/api/v1/tag/codes"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<Set<TagDTO>> getTagByCodes(@RequestParam(value = "codes",required = true) @NotNull @Valid Set<String> codes);


    @RequestMapping(
        value = {"/api/v1/tag/page"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<PageDTO<TagDTO>> getTagPage(@RequestParam(value = "page",required = true) @NotNull @Valid Integer page, @RequestParam(value = "size",required = true) @NotNull @Valid Integer size);

    @RequestMapping(
        value = {"/api/v1/tag"},
        method = {RequestMethod.POST}
    )
    ResponseEntity<Long> postTag(@RequestBody @Valid TagDTO body);

    @RequestMapping(
        value = {"/api/v1/tag"},
        method = {RequestMethod.PUT}
    )
    ResponseEntity<TagDTO> putTag(@RequestBody @Valid TagDTO body);

    @GetMapping("/api/v1/tag/sub-tags")
    ResponseEntity<Set<TagHierarchyDTO>> getSubTags(
            @RequestParam String context,
            @NotNull @RequestParam Set<Long> parentTagIds,
            @RequestParam(required = false) String type,
            @Max(value = 100L, message = "Depth cannot be greater than 100")
            @RequestParam(required = false) Long depth);

    @GetMapping("/api/v2/tag/sub-tags")
    ResponseEntity<Set<TagDTO>> getSubTagsByParentId(
            @NotNull @RequestParam Long parentTagId,
            @NotNull @RequestParam Long depth,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) String type);
}
