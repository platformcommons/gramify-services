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

import com.platformcommons.platform.service.blockprofile.dto.VillageIrrigationSystemTypeDTO;

/**
 * API contract for VillageIrrigationSystemTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-irrigation-system-types")
@Tag(name = "VillageIrrigationSystemType", description = "Operations for VillageIrrigationSystemType that delegate to VillageIrrigationSystemTypeFacade")
public interface VillageIrrigationSystemTypeControllerApi {

    @Operation(summary = "Create VillageIrrigationSystemType (delegates to VillageIrrigationSystemTypeFacade.save)", tags = {"VillageIrrigationSystemType"},
            description = "Creates a new resource using VillageIrrigationSystemTypeFacade.save with a VillageIrrigationSystemTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageIrrigationSystemType payload used for creation", required = true)
            @RequestBody VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO);

    @Operation(summary = "Update VillageIrrigationSystemType (delegates to VillageIrrigationSystemTypeFacade.update)", tags = {"VillageIrrigationSystemType"},
            description = "Updates a resource using VillageIrrigationSystemTypeFacade.update with a VillageIrrigationSystemTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageIrrigationSystemTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageIrrigationSystemTypeDTO> update(
            @Parameter(description = "VillageIrrigationSystemType payload used for update", required = true)
            @RequestBody VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO);

    @Operation(summary = "Get VillageIrrigationSystemTypes page (delegates to VillageIrrigationSystemTypeFacade.getAllPage)", tags = {"VillageIrrigationSystemType"},
            description = "Returns a paginated list using VillageIrrigationSystemTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageIrrigationSystemTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageIrrigationSystemType by ID (delegates to VillageIrrigationSystemTypeFacade.getById)" , tags = {"VillageIrrigationSystemType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageIrrigationSystemTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageIrrigationSystemTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageIrrigationSystemType (delegates to VillageIrrigationSystemTypeFacade.delete)" , tags = {"VillageIrrigationSystemType"})
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

    @Operation(summary = "Get VillageIrrigationSystemType by IDs (delegates to VillageIrrigationSystemTypeFacade.getAllByIds)" , tags = {"VillageIrrigationSystemType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageIrrigationSystemTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
