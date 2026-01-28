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

import com.platformcommons.platform.service.blockprofile.dto.VillageWaterSupplyGapDTO;

/**
 * API contract for VillageWaterSupplyGapController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-water-supply-gaps")
@Tag(name = "VillageWaterSupplyGap", description = "Operations for VillageWaterSupplyGap that delegate to VillageWaterSupplyGapFacade")
public interface VillageWaterSupplyGapControllerApi {

    @Operation(summary = "Create VillageWaterSupplyGap (delegates to VillageWaterSupplyGapFacade.save)", tags = {"VillageWaterSupplyGap"},
            description = "Creates a new resource using VillageWaterSupplyGapFacade.save with a VillageWaterSupplyGapDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageWaterSupplyGap payload used for creation", required = true)
            @RequestBody VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO);

    @Operation(summary = "Update VillageWaterSupplyGap (delegates to VillageWaterSupplyGapFacade.update)", tags = {"VillageWaterSupplyGap"},
            description = "Updates a resource using VillageWaterSupplyGapFacade.update with a VillageWaterSupplyGapDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageWaterSupplyGapDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageWaterSupplyGapDTO> update(
            @Parameter(description = "VillageWaterSupplyGap payload used for update", required = true)
            @RequestBody VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO);

    @Operation(summary = "Get VillageWaterSupplyGaps page (delegates to VillageWaterSupplyGapFacade.getAllPage)", tags = {"VillageWaterSupplyGap"},
            description = "Returns a paginated list using VillageWaterSupplyGapFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageWaterSupplyGapDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageWaterSupplyGap by ID (delegates to VillageWaterSupplyGapFacade.getById)" , tags = {"VillageWaterSupplyGap"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageWaterSupplyGapDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageWaterSupplyGapDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageWaterSupplyGap (delegates to VillageWaterSupplyGapFacade.delete)" , tags = {"VillageWaterSupplyGap"})
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

    @Operation(summary = "Get VillageWaterSupplyGap by IDs (delegates to VillageWaterSupplyGapFacade.getAllByIds)" , tags = {"VillageWaterSupplyGap"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageWaterSupplyGapDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
