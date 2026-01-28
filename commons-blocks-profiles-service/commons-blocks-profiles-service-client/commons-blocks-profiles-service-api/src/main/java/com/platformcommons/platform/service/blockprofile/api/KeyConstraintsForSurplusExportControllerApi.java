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

import com.platformcommons.platform.service.blockprofile.dto.KeyConstraintsForSurplusExportDTO;

/**
 * API contract for KeyConstraintsForSurplusExportController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/key-constraints-for-surplus-exports")
@Tag(name = "KeyConstraintsForSurplusExport", description = "Operations for KeyConstraintsForSurplusExport that delegate to KeyConstraintsForSurplusExportFacade")
public interface KeyConstraintsForSurplusExportControllerApi {

    @Operation(summary = "Create KeyConstraintsForSurplusExport (delegates to KeyConstraintsForSurplusExportFacade.save)", tags = {"KeyConstraintsForSurplusExport"},
            description = "Creates a new resource using KeyConstraintsForSurplusExportFacade.save with a KeyConstraintsForSurplusExportDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "KeyConstraintsForSurplusExport payload used for creation", required = true)
            @RequestBody KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO);

    @Operation(summary = "Update KeyConstraintsForSurplusExport (delegates to KeyConstraintsForSurplusExportFacade.update)", tags = {"KeyConstraintsForSurplusExport"},
            description = "Updates a resource using KeyConstraintsForSurplusExportFacade.update with a KeyConstraintsForSurplusExportDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = KeyConstraintsForSurplusExportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KeyConstraintsForSurplusExportDTO> update(
            @Parameter(description = "KeyConstraintsForSurplusExport payload used for update", required = true)
            @RequestBody KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO);

    @Operation(summary = "Get KeyConstraintsForSurplusExports page (delegates to KeyConstraintsForSurplusExportFacade.getAllPage)", tags = {"KeyConstraintsForSurplusExport"},
            description = "Returns a paginated list using KeyConstraintsForSurplusExportFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<KeyConstraintsForSurplusExportDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get KeyConstraintsForSurplusExport by ID (delegates to KeyConstraintsForSurplusExportFacade.getById)" , tags = {"KeyConstraintsForSurplusExport"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = KeyConstraintsForSurplusExportDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<KeyConstraintsForSurplusExportDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete KeyConstraintsForSurplusExport (delegates to KeyConstraintsForSurplusExportFacade.delete)" , tags = {"KeyConstraintsForSurplusExport"})
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

    @Operation(summary = "Get KeyConstraintsForSurplusExport by IDs (delegates to KeyConstraintsForSurplusExportFacade.getAllByIds)" , tags = {"KeyConstraintsForSurplusExport"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<KeyConstraintsForSurplusExportDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
