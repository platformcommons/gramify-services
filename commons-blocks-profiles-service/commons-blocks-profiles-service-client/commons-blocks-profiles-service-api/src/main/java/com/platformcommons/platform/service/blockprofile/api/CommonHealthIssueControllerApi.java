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

import com.platformcommons.platform.service.blockprofile.dto.CommonHealthIssueDTO;

/**
 * API contract for CommonHealthIssueController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/common-health-issues")
@Tag(name = "CommonHealthIssue", description = "Operations for CommonHealthIssue that delegate to CommonHealthIssueFacade")
public interface CommonHealthIssueControllerApi {

    @Operation(summary = "Create CommonHealthIssue (delegates to CommonHealthIssueFacade.save)", tags = {"CommonHealthIssue"},
            description = "Creates a new resource using CommonHealthIssueFacade.save with a CommonHealthIssueDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "CommonHealthIssue payload used for creation", required = true)
            @RequestBody CommonHealthIssueDTO CommonHealthIssueDTO);

    @Operation(summary = "Update CommonHealthIssue (delegates to CommonHealthIssueFacade.update)", tags = {"CommonHealthIssue"},
            description = "Updates a resource using CommonHealthIssueFacade.update with a CommonHealthIssueDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonHealthIssueDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommonHealthIssueDTO> update(
            @Parameter(description = "CommonHealthIssue payload used for update", required = true)
            @RequestBody CommonHealthIssueDTO CommonHealthIssueDTO);

    @Operation(summary = "Get CommonHealthIssues page (delegates to CommonHealthIssueFacade.getAllPage)", tags = {"CommonHealthIssue"},
            description = "Returns a paginated list using CommonHealthIssueFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<CommonHealthIssueDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get CommonHealthIssue by ID (delegates to CommonHealthIssueFacade.getById)" , tags = {"CommonHealthIssue"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = CommonHealthIssueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CommonHealthIssueDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete CommonHealthIssue (delegates to CommonHealthIssueFacade.delete)" , tags = {"CommonHealthIssue"})
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

    @Operation(summary = "Get CommonHealthIssue by IDs (delegates to CommonHealthIssueFacade.getAllByIds)" , tags = {"CommonHealthIssue"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<CommonHealthIssueDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
