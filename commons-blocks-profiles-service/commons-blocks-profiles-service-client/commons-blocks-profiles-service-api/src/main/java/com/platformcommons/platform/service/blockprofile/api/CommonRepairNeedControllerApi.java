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

import com.platformcommons.platform.service.blockprofile.dto.CommonRepairNeedDTO;

/**
 * API contract for CommonRepairNeedController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/common-repair-needs")
@Tag(name = "CommonRepairNeed", description = "Operations for CommonRepairNeed that delegate to CommonRepairNeedFacade")
public interface CommonRepairNeedControllerApi {

    @Operation(summary = "Create CommonRepairNeed (delegates to CommonRepairNeedFacade.save)", tags = {"CommonRepairNeed"},
            description = "Creates a new resource using CommonRepairNeedFacade.save with a CommonRepairNeedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "CommonRepairNeed payload used for creation", required = true)
            @RequestBody CommonRepairNeedDTO CommonRepairNeedDTO);

    @Operation(summary = "Update CommonRepairNeed (delegates to CommonRepairNeedFacade.update)", tags = {"CommonRepairNeed"},
            description = "Updates a resource using CommonRepairNeedFacade.update with a CommonRepairNeedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonRepairNeedDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommonRepairNeedDTO> update(
            @Parameter(description = "CommonRepairNeed payload used for update", required = true)
            @RequestBody CommonRepairNeedDTO CommonRepairNeedDTO);

    @Operation(summary = "Get CommonRepairNeeds page (delegates to CommonRepairNeedFacade.getAllPage)", tags = {"CommonRepairNeed"},
            description = "Returns a paginated list using CommonRepairNeedFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<CommonRepairNeedDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get CommonRepairNeed by ID (delegates to CommonRepairNeedFacade.getById)" , tags = {"CommonRepairNeed"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = CommonRepairNeedDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CommonRepairNeedDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete CommonRepairNeed (delegates to CommonRepairNeedFacade.delete)" , tags = {"CommonRepairNeed"})
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

    @Operation(summary = "Get CommonRepairNeed by IDs (delegates to CommonRepairNeedFacade.getAllByIds)" , tags = {"CommonRepairNeed"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<CommonRepairNeedDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
