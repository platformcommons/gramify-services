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

import com.platformcommons.platform.service.blockprofile.dto.HHSavingsModeDTO;

/**
 * API contract for HHSavingsModeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-savings-modes")
@Tag(name = "HHSavingsMode", description = "Operations for HHSavingsMode that delegate to HHSavingsModeFacade")
public interface HHSavingsModeControllerApi {

    @Operation(summary = "Create HHSavingsMode (delegates to HHSavingsModeFacade.save)", tags = {"HHSavingsMode"},
            description = "Creates a new resource using HHSavingsModeFacade.save with a HHSavingsModeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHSavingsMode payload used for creation", required = true)
            @RequestBody HHSavingsModeDTO HHSavingsModeDTO);

    @Operation(summary = "Update HHSavingsMode (delegates to HHSavingsModeFacade.update)", tags = {"HHSavingsMode"},
            description = "Updates a resource using HHSavingsModeFacade.update with a HHSavingsModeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHSavingsModeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHSavingsModeDTO> update(
            @Parameter(description = "HHSavingsMode payload used for update", required = true)
            @RequestBody HHSavingsModeDTO HHSavingsModeDTO);

    @Operation(summary = "Get HHSavingsModes page (delegates to HHSavingsModeFacade.getAllPage)", tags = {"HHSavingsMode"},
            description = "Returns a paginated list using HHSavingsModeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHSavingsModeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHSavingsMode by ID (delegates to HHSavingsModeFacade.getById)" , tags = {"HHSavingsMode"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHSavingsModeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHSavingsModeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHSavingsMode (delegates to HHSavingsModeFacade.delete)" , tags = {"HHSavingsMode"})
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

    @Operation(summary = "Get HHSavingsMode by IDs (delegates to HHSavingsModeFacade.getAllByIds)" , tags = {"HHSavingsMode"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHSavingsModeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
