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

import com.platformcommons.platform.service.blockprofile.dto.VillageForestProduceTypeDTO;

/**
 * API contract for VillageForestProduceTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-forest-produce-types")
@Tag(name = "VillageForestProduceType", description = "Operations for VillageForestProduceType that delegate to VillageForestProduceTypeFacade")
public interface VillageForestProduceTypeControllerApi {

    @Operation(summary = "Create VillageForestProduceType (delegates to VillageForestProduceTypeFacade.save)", tags = {"VillageForestProduceType"},
            description = "Creates a new resource using VillageForestProduceTypeFacade.save with a VillageForestProduceTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageForestProduceType payload used for creation", required = true)
            @RequestBody VillageForestProduceTypeDTO VillageForestProduceTypeDTO);

    @Operation(summary = "Update VillageForestProduceType (delegates to VillageForestProduceTypeFacade.update)", tags = {"VillageForestProduceType"},
            description = "Updates a resource using VillageForestProduceTypeFacade.update with a VillageForestProduceTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageForestProduceTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageForestProduceTypeDTO> update(
            @Parameter(description = "VillageForestProduceType payload used for update", required = true)
            @RequestBody VillageForestProduceTypeDTO VillageForestProduceTypeDTO);

    @Operation(summary = "Get VillageForestProduceTypes page (delegates to VillageForestProduceTypeFacade.getAllPage)", tags = {"VillageForestProduceType"},
            description = "Returns a paginated list using VillageForestProduceTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageForestProduceTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageForestProduceType by ID (delegates to VillageForestProduceTypeFacade.getById)" , tags = {"VillageForestProduceType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageForestProduceTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageForestProduceTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageForestProduceType (delegates to VillageForestProduceTypeFacade.delete)" , tags = {"VillageForestProduceType"})
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

    @Operation(summary = "Get VillageForestProduceType by IDs (delegates to VillageForestProduceTypeFacade.getAllByIds)" , tags = {"VillageForestProduceType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageForestProduceTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
