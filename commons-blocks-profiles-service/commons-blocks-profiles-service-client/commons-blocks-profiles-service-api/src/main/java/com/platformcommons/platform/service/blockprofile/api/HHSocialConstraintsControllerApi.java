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

import com.platformcommons.platform.service.blockprofile.dto.HHSocialConstraintsDTO;

/**
 * API contract for HHSocialConstraintsController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-social-constraintses")
@Tag(name = "HHSocialConstraints", description = "Operations for HHSocialConstraints that delegate to HHSocialConstraintsFacade")
public interface HHSocialConstraintsControllerApi {

    @Operation(summary = "Create HHSocialConstraints (delegates to HHSocialConstraintsFacade.save)", tags = {"HHSocialConstraints"},
            description = "Creates a new resource using HHSocialConstraintsFacade.save with a HHSocialConstraintsDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHSocialConstraints payload used for creation", required = true)
            @RequestBody HHSocialConstraintsDTO HHSocialConstraintsDTO);

    @Operation(summary = "Update HHSocialConstraints (delegates to HHSocialConstraintsFacade.update)", tags = {"HHSocialConstraints"},
            description = "Updates a resource using HHSocialConstraintsFacade.update with a HHSocialConstraintsDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHSocialConstraintsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHSocialConstraintsDTO> update(
            @Parameter(description = "HHSocialConstraints payload used for update", required = true)
            @RequestBody HHSocialConstraintsDTO HHSocialConstraintsDTO);

    @Operation(summary = "Get HHSocialConstraintss page (delegates to HHSocialConstraintsFacade.getAllPage)", tags = {"HHSocialConstraints"},
            description = "Returns a paginated list using HHSocialConstraintsFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHSocialConstraintsDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHSocialConstraints by ID (delegates to HHSocialConstraintsFacade.getById)" , tags = {"HHSocialConstraints"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHSocialConstraintsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHSocialConstraintsDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHSocialConstraints (delegates to HHSocialConstraintsFacade.delete)" , tags = {"HHSocialConstraints"})
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

    @Operation(summary = "Get HHSocialConstraints by IDs (delegates to HHSocialConstraintsFacade.getAllByIds)" , tags = {"HHSocialConstraints"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHSocialConstraintsDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
