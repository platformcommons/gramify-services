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

import com.platformcommons.platform.service.blockprofile.dto.HHMigrationMonthDTO;

/**
 * API contract for HHMigrationMonthController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-migration-months")
@Tag(name = "HHMigrationMonth", description = "Operations for HHMigrationMonth that delegate to HHMigrationMonthFacade")
public interface HHMigrationMonthControllerApi {

    @Operation(summary = "Create HHMigrationMonth (delegates to HHMigrationMonthFacade.save)", tags = {"HHMigrationMonth"},
            description = "Creates a new resource using HHMigrationMonthFacade.save with a HHMigrationMonthDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHMigrationMonth payload used for creation", required = true)
            @RequestBody HHMigrationMonthDTO HHMigrationMonthDTO);

    @Operation(summary = "Update HHMigrationMonth (delegates to HHMigrationMonthFacade.update)", tags = {"HHMigrationMonth"},
            description = "Updates a resource using HHMigrationMonthFacade.update with a HHMigrationMonthDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHMigrationMonthDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHMigrationMonthDTO> update(
            @Parameter(description = "HHMigrationMonth payload used for update", required = true)
            @RequestBody HHMigrationMonthDTO HHMigrationMonthDTO);

    @Operation(summary = "Get HHMigrationMonths page (delegates to HHMigrationMonthFacade.getAllPage)", tags = {"HHMigrationMonth"},
            description = "Returns a paginated list using HHMigrationMonthFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHMigrationMonthDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHMigrationMonth by ID (delegates to HHMigrationMonthFacade.getById)" , tags = {"HHMigrationMonth"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHMigrationMonthDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHMigrationMonthDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHMigrationMonth (delegates to HHMigrationMonthFacade.delete)" , tags = {"HHMigrationMonth"})
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

    @Operation(summary = "Get HHMigrationMonth by IDs (delegates to HHMigrationMonthFacade.getAllByIds)" , tags = {"HHMigrationMonth"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHMigrationMonthDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
