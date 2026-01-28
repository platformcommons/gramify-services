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

import com.platformcommons.platform.service.blockprofile.dto.NGOThematicAreaDTO;

/**
 * API contract for NGOThematicAreaController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/ngo-thematic-areas")
@Tag(name = "NGOThematicArea", description = "Operations for NGOThematicArea that delegate to NGOThematicAreaFacade")
public interface NGOThematicAreaControllerApi {

    @Operation(summary = "Create NGOThematicArea (delegates to NGOThematicAreaFacade.save)", tags = {"NGOThematicArea"},
            description = "Creates a new resource using NGOThematicAreaFacade.save with a NGOThematicAreaDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "NGOThematicArea payload used for creation", required = true)
            @RequestBody NGOThematicAreaDTO NGOThematicAreaDTO);

    @Operation(summary = "Update NGOThematicArea (delegates to NGOThematicAreaFacade.update)", tags = {"NGOThematicArea"},
            description = "Updates a resource using NGOThematicAreaFacade.update with a NGOThematicAreaDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = NGOThematicAreaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NGOThematicAreaDTO> update(
            @Parameter(description = "NGOThematicArea payload used for update", required = true)
            @RequestBody NGOThematicAreaDTO NGOThematicAreaDTO);

    @Operation(summary = "Get NGOThematicAreas page (delegates to NGOThematicAreaFacade.getAllPage)", tags = {"NGOThematicArea"},
            description = "Returns a paginated list using NGOThematicAreaFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<NGOThematicAreaDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get NGOThematicArea by ID (delegates to NGOThematicAreaFacade.getById)" , tags = {"NGOThematicArea"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = NGOThematicAreaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<NGOThematicAreaDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete NGOThematicArea (delegates to NGOThematicAreaFacade.delete)" , tags = {"NGOThematicArea"})
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

    @Operation(summary = "Get NGOThematicArea by IDs (delegates to NGOThematicAreaFacade.getAllByIds)" , tags = {"NGOThematicArea"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<NGOThematicAreaDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
