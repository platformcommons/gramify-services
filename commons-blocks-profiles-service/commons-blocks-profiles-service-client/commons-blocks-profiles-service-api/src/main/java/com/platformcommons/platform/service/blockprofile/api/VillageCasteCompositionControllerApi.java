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

import com.platformcommons.platform.service.blockprofile.dto.VillageCasteCompositionDTO;

/**
 * API contract for VillageCasteCompositionController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-caste-compositions")
@Tag(name = "VillageCasteComposition", description = "Operations for VillageCasteComposition that delegate to VillageCasteCompositionFacade")
public interface VillageCasteCompositionControllerApi {

    @Operation(summary = "Create VillageCasteComposition (delegates to VillageCasteCompositionFacade.save)", tags = {"VillageCasteComposition"},
            description = "Creates a new resource using VillageCasteCompositionFacade.save with a VillageCasteCompositionDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageCasteComposition payload used for creation", required = true)
            @RequestBody VillageCasteCompositionDTO VillageCasteCompositionDTO);

    @Operation(summary = "Update VillageCasteComposition (delegates to VillageCasteCompositionFacade.update)", tags = {"VillageCasteComposition"},
            description = "Updates a resource using VillageCasteCompositionFacade.update with a VillageCasteCompositionDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageCasteCompositionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageCasteCompositionDTO> update(
            @Parameter(description = "VillageCasteComposition payload used for update", required = true)
            @RequestBody VillageCasteCompositionDTO VillageCasteCompositionDTO);

    @Operation(summary = "Get VillageCasteCompositions page (delegates to VillageCasteCompositionFacade.getAllPage)", tags = {"VillageCasteComposition"},
            description = "Returns a paginated list using VillageCasteCompositionFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageCasteCompositionDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageCasteComposition by ID (delegates to VillageCasteCompositionFacade.getById)" , tags = {"VillageCasteComposition"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageCasteCompositionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageCasteCompositionDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageCasteComposition (delegates to VillageCasteCompositionFacade.delete)" , tags = {"VillageCasteComposition"})
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

    @Operation(summary = "Get VillageCasteComposition by IDs (delegates to VillageCasteCompositionFacade.getAllByIds)" , tags = {"VillageCasteComposition"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageCasteCompositionDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
