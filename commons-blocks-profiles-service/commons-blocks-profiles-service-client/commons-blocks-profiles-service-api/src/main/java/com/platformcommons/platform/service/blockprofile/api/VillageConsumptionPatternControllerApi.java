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

import com.platformcommons.platform.service.blockprofile.dto.VillageConsumptionPatternDTO;

/**
 * API contract for VillageConsumptionPatternController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-consumption-patterns")
@Tag(name = "VillageConsumptionPattern", description = "Operations for VillageConsumptionPattern that delegate to VillageConsumptionPatternFacade")
public interface VillageConsumptionPatternControllerApi {

    @Operation(summary = "Create VillageConsumptionPattern (delegates to VillageConsumptionPatternFacade.save)", tags = {"VillageConsumptionPattern"},
            description = "Creates a new resource using VillageConsumptionPatternFacade.save with a VillageConsumptionPatternDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageConsumptionPattern payload used for creation", required = true)
            @RequestBody VillageConsumptionPatternDTO VillageConsumptionPatternDTO);

    @Operation(summary = "Update VillageConsumptionPattern (delegates to VillageConsumptionPatternFacade.update)", tags = {"VillageConsumptionPattern"},
            description = "Updates a resource using VillageConsumptionPatternFacade.update with a VillageConsumptionPatternDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageConsumptionPatternDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageConsumptionPatternDTO> update(
            @Parameter(description = "VillageConsumptionPattern payload used for update", required = true)
            @RequestBody VillageConsumptionPatternDTO VillageConsumptionPatternDTO);

    @Operation(summary = "Get VillageConsumptionPatterns page (delegates to VillageConsumptionPatternFacade.getAllPage)", tags = {"VillageConsumptionPattern"},
            description = "Returns a paginated list using VillageConsumptionPatternFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageConsumptionPatternDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageConsumptionPattern by ID (delegates to VillageConsumptionPatternFacade.getById)" , tags = {"VillageConsumptionPattern"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageConsumptionPatternDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageConsumptionPatternDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageConsumptionPattern (delegates to VillageConsumptionPatternFacade.delete)" , tags = {"VillageConsumptionPattern"})
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

    @Operation(summary = "Get VillageConsumptionPattern by IDs (delegates to VillageConsumptionPatternFacade.getAllByIds)" , tags = {"VillageConsumptionPattern"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageConsumptionPatternDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
