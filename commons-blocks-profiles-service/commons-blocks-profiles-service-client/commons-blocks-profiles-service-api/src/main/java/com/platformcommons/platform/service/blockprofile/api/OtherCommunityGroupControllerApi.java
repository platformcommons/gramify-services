package com.platformcommons.platform.service.blockprofile.api;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.OtherCommunityGroupDTO;

/**
 * API contract for OtherCommunityGroupController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/other-community-groups")
@Tag(name = "OtherCommunityGroup", description = "Operations for OtherCommunityGroup that delegate to OtherCommunityGroupFacade")
public interface OtherCommunityGroupControllerApi {

    @Operation(summary = "Create OtherCommunityGroup (delegates to OtherCommunityGroupFacade.save)", tags = {"OtherCommunityGroup"},
            description = "Creates a new resource using OtherCommunityGroupFacade.save with a OtherCommunityGroupDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "OtherCommunityGroup payload used for creation", required = true)
            @RequestBody OtherCommunityGroupDTO OtherCommunityGroupDTO);

    @Operation(summary = "Update OtherCommunityGroup (delegates to OtherCommunityGroupFacade.update)", tags = {"OtherCommunityGroup"},
            description = "Updates a resource using OtherCommunityGroupFacade.update with a OtherCommunityGroupDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = OtherCommunityGroupDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OtherCommunityGroupDTO> update(
            @Parameter(description = "OtherCommunityGroup payload used for update", required = true)
            @RequestBody OtherCommunityGroupDTO OtherCommunityGroupDTO);

    @Operation(summary = "Get OtherCommunityGroups page (delegates to OtherCommunityGroupFacade.getAllPage)", tags = {"OtherCommunityGroup"},
            description = "Returns a paginated list using OtherCommunityGroupFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<OtherCommunityGroupDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get OtherCommunityGroup by ID (delegates to OtherCommunityGroupFacade.getById)" , tags = {"OtherCommunityGroup"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = OtherCommunityGroupDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<OtherCommunityGroupDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete OtherCommunityGroup (delegates to OtherCommunityGroupFacade.delete)" , tags = {"OtherCommunityGroup"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Reason for deletion")
            @RequestParam(required = false) String reason);

    @Operation(summary = "Get OtherCommunityGroup by IDs (delegates to OtherCommunityGroupFacade.getAllByIds)" , tags = {"OtherCommunityGroup"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<OtherCommunityGroupDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
