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

import com.platformcommons.platform.service.blockprofile.dto.EmergingEnterpriseTypeDTO;

/**
 * API contract for EmergingEnterpriseTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/emerging-enterprise-types")
@Tag(name = "EmergingEnterpriseType", description = "Operations for EmergingEnterpriseType that delegate to EmergingEnterpriseTypeFacade")
public interface EmergingEnterpriseTypeControllerApi {

    @Operation(summary = "Create EmergingEnterpriseType (delegates to EmergingEnterpriseTypeFacade.save)", tags = {"EmergingEnterpriseType"},
            description = "Creates a new resource using EmergingEnterpriseTypeFacade.save with a EmergingEnterpriseTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "EmergingEnterpriseType payload used for creation", required = true)
            @RequestBody EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO);

    @Operation(summary = "Update EmergingEnterpriseType (delegates to EmergingEnterpriseTypeFacade.update)", tags = {"EmergingEnterpriseType"},
            description = "Updates a resource using EmergingEnterpriseTypeFacade.update with a EmergingEnterpriseTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = EmergingEnterpriseTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EmergingEnterpriseTypeDTO> update(
            @Parameter(description = "EmergingEnterpriseType payload used for update", required = true)
            @RequestBody EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO);

    @Operation(summary = "Get EmergingEnterpriseTypes page (delegates to EmergingEnterpriseTypeFacade.getAllPage)", tags = {"EmergingEnterpriseType"},
            description = "Returns a paginated list using EmergingEnterpriseTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<EmergingEnterpriseTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get EmergingEnterpriseType by ID (delegates to EmergingEnterpriseTypeFacade.getById)" , tags = {"EmergingEnterpriseType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = EmergingEnterpriseTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<EmergingEnterpriseTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete EmergingEnterpriseType (delegates to EmergingEnterpriseTypeFacade.delete)" , tags = {"EmergingEnterpriseType"})
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

    @Operation(summary = "Get EmergingEnterpriseType by IDs (delegates to EmergingEnterpriseTypeFacade.getAllByIds)" , tags = {"EmergingEnterpriseType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<EmergingEnterpriseTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
