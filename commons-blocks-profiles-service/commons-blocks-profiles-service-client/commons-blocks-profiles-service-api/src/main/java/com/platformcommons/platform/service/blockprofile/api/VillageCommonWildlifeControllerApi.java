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

import com.platformcommons.platform.service.blockprofile.dto.VillageCommonWildlifeDTO;

/**
 * API contract for VillageCommonWildlifeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-common-wildlifes")
@Tag(name = "VillageCommonWildlife", description = "Operations for VillageCommonWildlife that delegate to VillageCommonWildlifeFacade")
public interface VillageCommonWildlifeControllerApi {

    @Operation(summary = "Create VillageCommonWildlife (delegates to VillageCommonWildlifeFacade.save)", tags = {"VillageCommonWildlife"},
            description = "Creates a new resource using VillageCommonWildlifeFacade.save with a VillageCommonWildlifeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageCommonWildlife payload used for creation", required = true)
            @RequestBody VillageCommonWildlifeDTO VillageCommonWildlifeDTO);

    @Operation(summary = "Update VillageCommonWildlife (delegates to VillageCommonWildlifeFacade.update)", tags = {"VillageCommonWildlife"},
            description = "Updates a resource using VillageCommonWildlifeFacade.update with a VillageCommonWildlifeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageCommonWildlifeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageCommonWildlifeDTO> update(
            @Parameter(description = "VillageCommonWildlife payload used for update", required = true)
            @RequestBody VillageCommonWildlifeDTO VillageCommonWildlifeDTO);

    @Operation(summary = "Get VillageCommonWildlifes page (delegates to VillageCommonWildlifeFacade.getAllPage)", tags = {"VillageCommonWildlife"},
            description = "Returns a paginated list using VillageCommonWildlifeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageCommonWildlifeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageCommonWildlife by ID (delegates to VillageCommonWildlifeFacade.getById)" , tags = {"VillageCommonWildlife"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageCommonWildlifeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageCommonWildlifeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageCommonWildlife (delegates to VillageCommonWildlifeFacade.delete)" , tags = {"VillageCommonWildlife"})
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

    @Operation(summary = "Get VillageCommonWildlife by IDs (delegates to VillageCommonWildlifeFacade.getAllByIds)" , tags = {"VillageCommonWildlife"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageCommonWildlifeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
