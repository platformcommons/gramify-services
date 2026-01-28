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

import com.platformcommons.platform.service.blockprofile.dto.PowerCutSeasonDTO;

/**
 * API contract for PowerCutSeasonController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/power-cut-seasons")
@Tag(name = "PowerCutSeason", description = "Operations for PowerCutSeason that delegate to PowerCutSeasonFacade")
public interface PowerCutSeasonControllerApi {

    @Operation(summary = "Create PowerCutSeason (delegates to PowerCutSeasonFacade.save)", tags = {"PowerCutSeason"},
            description = "Creates a new resource using PowerCutSeasonFacade.save with a PowerCutSeasonDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "PowerCutSeason payload used for creation", required = true)
            @RequestBody PowerCutSeasonDTO PowerCutSeasonDTO);

    @Operation(summary = "Update PowerCutSeason (delegates to PowerCutSeasonFacade.update)", tags = {"PowerCutSeason"},
            description = "Updates a resource using PowerCutSeasonFacade.update with a PowerCutSeasonDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = PowerCutSeasonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PowerCutSeasonDTO> update(
            @Parameter(description = "PowerCutSeason payload used for update", required = true)
            @RequestBody PowerCutSeasonDTO PowerCutSeasonDTO);

    @Operation(summary = "Get PowerCutSeasons page (delegates to PowerCutSeasonFacade.getAllPage)", tags = {"PowerCutSeason"},
            description = "Returns a paginated list using PowerCutSeasonFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<PowerCutSeasonDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get PowerCutSeason by ID (delegates to PowerCutSeasonFacade.getById)" , tags = {"PowerCutSeason"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = PowerCutSeasonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<PowerCutSeasonDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete PowerCutSeason (delegates to PowerCutSeasonFacade.delete)" , tags = {"PowerCutSeason"})
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

    @Operation(summary = "Get PowerCutSeason by IDs (delegates to PowerCutSeasonFacade.getAllByIds)" , tags = {"PowerCutSeason"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<PowerCutSeasonDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
