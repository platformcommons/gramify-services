package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.client.TagAPI;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import com.platformcommons.platform.service.domain.dto.TagV2DTO;
import com.platformcommons.platform.service.domain.facade.TagFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@Tag(name = "Tag")
public class TagController implements TagAPI {

    @Autowired
    private TagFacade tagFacade;

    @Override
    public ResponseEntity<Void> deleteTag(Long id, String reason) {
        tagFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<TagDTO> getTag(Long id) {
        return  ResponseEntity.status(HttpStatus.OK).body(tagFacade.getById(id));

    }


    @ApiOperation(value = "Get By Codes", nickname = "getTagByCodes", notes = "", response = TagDTO.class, responseContainer = "Set", tags={ "Tag", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/tag/codes",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Set<TagDTO>> getTagByCodes(@RequestParam Set<String> codes) {
        return ResponseEntity.status(HttpStatus.OK).body(tagFacade.getTagByCodes(codes).getElements());
    }

    @Override
    public ResponseEntity<PageDTO<TagDTO>> getTagPage(Integer page, Integer size,String context, Boolean includeRefLabel) {
        PageDTO<TagDTO> result= tagFacade.getAllPage(page,size,context,includeRefLabel);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT: HttpStatus.OK).body(result);
    }


    @ApiOperation(value = "Get By Page", nickname = "getTagPage", notes = "", response = TagDTO.class, responseContainer = "Set", tags={ "Tag", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/tags",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<TagDTO>> getTags(@NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                        @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size){
        PageDTO<TagDTO> result= tagFacade.getAllPage(page,size,null,Boolean.FALSE);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT: HttpStatus.OK).body(result.getElements());
    }

    @Override
    public ResponseEntity<Long> postTag(TagDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagFacade.save(body));
    }

    @Override
    public ResponseEntity<TagDTO> postTagV2(TagDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagFacade.saveTagV2(body));
    }

    @ApiOperation(value = "", nickname = "postTagAll", notes = "", response = Long.class, tags={ "Tag", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class) })
    @RequestMapping(value = "/api/v1/tag/batch",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<Void> postTagAll(@RequestBody List<TagDTO> body) {
        tagFacade.saveAll(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<TagDTO> putTag(TagDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(tagFacade.update(body));
    }


    @ApiOperation(value = "Create Tag Hierarchy Batch", nickname = "createTagHierarchy", notes = "Create parent-child relationships for tags in batch.", tags = {"Tag"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created - Tag hierarchy successfully created."),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 404, message = "Tag not found."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @PatchMapping("/api/v1/tag/hierarchy/batch")
    public ResponseEntity<Void> addTagHierarchyBatch(@RequestParam List<Long> childTagIds,@RequestParam Long parentTagId){
        tagFacade.addTagHierarchyBatch(childTagIds,parentTagId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Get Tags", nickname = "getTags", notes = "Retrieve a paginated list of tags based on context, root status, and type.", response = PageDTO.class, tags = {"Tag"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created - Tags successfully retrieved.", response = PageDTO.class),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @GetMapping("/api/v1/tags")
    public ResponseEntity<PageDTO<TagDTO>> getTags(@RequestParam Integer page,
                                        @RequestParam Integer size,
                                        @RequestParam(required = false) String context,
                                        @RequestParam(required = false) Boolean isRoot,
                                        @RequestParam(required = false) String type
    ){

        PageDTO<TagDTO> tags= tagFacade.getTags(page,size,context,isRoot,type);
        return ResponseEntity.status(tags.hasNext()? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(tags);
    }

    @ApiOperation(value = "Get Sub Tags", nickname = "getSubTags", notes = "Retrieve sub-tags based on specified criteria.", response = Set.class, tags = {"Tag"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PageDTO.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/api/v1/tag/sub-tags")
    public ResponseEntity<Set<TagHierarchyDTO>> getSubTags(
            @RequestParam String context,
            @NotNull @RequestParam Set<Long> parentTagIds,
            @RequestParam(required = false) String type,
            @Max(value = 100L, message = "Depth cannot be greater than 100")
            @RequestParam(required = false) Long depth) {
        Set<TagHierarchyDTO> results = tagFacade.getSubTags(parentTagIds, depth, context, type);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Get Sub Tags by Parent ID", nickname = "getSubTagsByParentId", notes = "Retrieve sub-tags for a single parent tag ID.", response = Set.class, tags = {"Tag"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagDTO.class, responseContainer = "Set"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/api/v2/tag/sub-tags")
    public ResponseEntity<Set<TagDTO>> getSubTagsByParentId(
            @NotNull @RequestParam Long parentTagId,
            @NotNull @RequestParam Long depth,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) String type) {
        Set<TagDTO> results = tagFacade.getSubTagsByParentId(parentTagId, depth, context, type);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "Attached Domain to Tag", nickname = "AttachedDomainToTag", notes = "", response = TagV2DTO.class, tags={ "Tag", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagV2DTO.class) })
    @RequestMapping(value = "/api/v1/tags/{tagId}/domains",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<TagV2DTO> attachDomainsToTag(@PathVariable(value = "tagId") Long tagId,
                                        @RequestParam(value = "domainIds") Set<Long> domainIds){
        TagV2DTO result= tagFacade.attachDomainsToTag(tagId,domainIds);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get By Page", nickname = "getTagPage", notes = "", response = TagV2DTO.class, responseContainer = "Set", tags={ "Tag", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/tags/type",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<TagV2DTO>> getTagsByType(@RequestParam(value = "page", required = true) Integer page,
                                                @RequestParam(value = "size", required = true) Integer size,
                                                @RequestParam(value = "type") String type){
        PageDTO<TagV2DTO> result= tagFacade.getTagsByType(type,page,size);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT: HttpStatus.OK).body(result.getElements());
    }




}
