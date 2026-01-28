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

import com.platformcommons.platform.service.blockprofile.dto.NicheProductsAvailabilityDTO;

/**
 * API contract for NicheProductsAvailabilityController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/niche-products-availabilities")
@Tag(name = "NicheProductsAvailability", description = "Operations for NicheProductsAvailability that delegate to NicheProductsAvailabilityFacade")
public interface NicheProductsAvailabilityControllerApi {

    @Operation(summary = "Create NicheProductsAvailability (delegates to NicheProductsAvailabilityFacade.save)", tags = {"NicheProductsAvailability"},
            description = "Creates a new resource using NicheProductsAvailabilityFacade.save with a NicheProductsAvailabilityDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "NicheProductsAvailability payload used for creation", required = true)
            @RequestBody NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO);

    @Operation(summary = "Update NicheProductsAvailability (delegates to NicheProductsAvailabilityFacade.update)", tags = {"NicheProductsAvailability"},
            description = "Updates a resource using NicheProductsAvailabilityFacade.update with a NicheProductsAvailabilityDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = NicheProductsAvailabilityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NicheProductsAvailabilityDTO> update(
            @Parameter(description = "NicheProductsAvailability payload used for update", required = true)
            @RequestBody NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO);

    @Operation(summary = "Get NicheProductsAvailabilitys page (delegates to NicheProductsAvailabilityFacade.getAllPage)", tags = {"NicheProductsAvailability"},
            description = "Returns a paginated list using NicheProductsAvailabilityFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<NicheProductsAvailabilityDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get NicheProductsAvailability by ID (delegates to NicheProductsAvailabilityFacade.getById)" , tags = {"NicheProductsAvailability"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = NicheProductsAvailabilityDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<NicheProductsAvailabilityDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete NicheProductsAvailability (delegates to NicheProductsAvailabilityFacade.delete)" , tags = {"NicheProductsAvailability"})
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

    @Operation(summary = "Get NicheProductsAvailability by IDs (delegates to NicheProductsAvailabilityFacade.getAllByIds)" , tags = {"NicheProductsAvailability"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<NicheProductsAvailabilityDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
