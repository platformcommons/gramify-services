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

import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFloraDTO;

/**
 * API contract for VillageCommonFloraController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-common-floras")
@Tag(name = "VillageCommonFlora", description = "Operations for VillageCommonFlora that delegate to VillageCommonFloraFacade")
public interface VillageCommonFloraControllerApi {

    @Operation(summary = "Create VillageCommonFlora (delegates to VillageCommonFloraFacade.save)", tags = {"VillageCommonFlora"},
            description = "Creates a new resource using VillageCommonFloraFacade.save with a VillageCommonFloraDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageCommonFlora payload used for creation", required = true)
            @RequestBody VillageCommonFloraDTO VillageCommonFloraDTO);

    @Operation(summary = "Update VillageCommonFlora (delegates to VillageCommonFloraFacade.update)", tags = {"VillageCommonFlora"},
            description = "Updates a resource using VillageCommonFloraFacade.update with a VillageCommonFloraDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageCommonFloraDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageCommonFloraDTO> update(
            @Parameter(description = "VillageCommonFlora payload used for update", required = true)
            @RequestBody VillageCommonFloraDTO VillageCommonFloraDTO);

    @Operation(summary = "Get VillageCommonFloras page (delegates to VillageCommonFloraFacade.getAllPage)", tags = {"VillageCommonFlora"},
            description = "Returns a paginated list using VillageCommonFloraFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageCommonFloraDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageCommonFlora by ID (delegates to VillageCommonFloraFacade.getById)" , tags = {"VillageCommonFlora"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageCommonFloraDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageCommonFloraDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageCommonFlora (delegates to VillageCommonFloraFacade.delete)" , tags = {"VillageCommonFlora"})
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

    @Operation(summary = "Get VillageCommonFlora by IDs (delegates to VillageCommonFloraFacade.getAllByIds)" , tags = {"VillageCommonFlora"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageCommonFloraDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
