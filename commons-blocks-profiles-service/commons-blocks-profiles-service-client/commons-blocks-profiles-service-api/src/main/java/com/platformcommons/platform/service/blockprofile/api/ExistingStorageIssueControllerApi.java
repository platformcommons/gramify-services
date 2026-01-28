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

import com.platformcommons.platform.service.blockprofile.dto.ExistingStorageIssueDTO;

/**
 * API contract for ExistingStorageIssueController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/existing-storage-issues")
@Tag(name = "ExistingStorageIssue", description = "Operations for ExistingStorageIssue that delegate to ExistingStorageIssueFacade")
public interface ExistingStorageIssueControllerApi {

    @Operation(summary = "Create ExistingStorageIssue (delegates to ExistingStorageIssueFacade.save)", tags = {"ExistingStorageIssue"},
            description = "Creates a new resource using ExistingStorageIssueFacade.save with a ExistingStorageIssueDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "ExistingStorageIssue payload used for creation", required = true)
            @RequestBody ExistingStorageIssueDTO ExistingStorageIssueDTO);

    @Operation(summary = "Update ExistingStorageIssue (delegates to ExistingStorageIssueFacade.update)", tags = {"ExistingStorageIssue"},
            description = "Updates a resource using ExistingStorageIssueFacade.update with a ExistingStorageIssueDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ExistingStorageIssueDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExistingStorageIssueDTO> update(
            @Parameter(description = "ExistingStorageIssue payload used for update", required = true)
            @RequestBody ExistingStorageIssueDTO ExistingStorageIssueDTO);

    @Operation(summary = "Get ExistingStorageIssues page (delegates to ExistingStorageIssueFacade.getAllPage)", tags = {"ExistingStorageIssue"},
            description = "Returns a paginated list using ExistingStorageIssueFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ExistingStorageIssueDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get ExistingStorageIssue by ID (delegates to ExistingStorageIssueFacade.getById)" , tags = {"ExistingStorageIssue"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ExistingStorageIssueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ExistingStorageIssueDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete ExistingStorageIssue (delegates to ExistingStorageIssueFacade.delete)" , tags = {"ExistingStorageIssue"})
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

    @Operation(summary = "Get ExistingStorageIssue by IDs (delegates to ExistingStorageIssueFacade.getAllByIds)" , tags = {"ExistingStorageIssue"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ExistingStorageIssueDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
