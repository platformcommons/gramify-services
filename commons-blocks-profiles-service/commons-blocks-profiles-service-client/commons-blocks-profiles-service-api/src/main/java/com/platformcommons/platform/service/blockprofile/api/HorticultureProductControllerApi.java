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

import com.platformcommons.platform.service.blockprofile.dto.HorticultureProductDTO;

/**
 * API contract for HorticultureProductController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/horticulture-products")
@Tag(name = "HorticultureProduct", description = "Operations for HorticultureProduct that delegate to HorticultureProductFacade")
public interface HorticultureProductControllerApi {

    @Operation(summary = "Create HorticultureProduct (delegates to HorticultureProductFacade.save)", tags = {"HorticultureProduct"},
            description = "Creates a new resource using HorticultureProductFacade.save with a HorticultureProductDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HorticultureProduct payload used for creation", required = true)
            @RequestBody HorticultureProductDTO HorticultureProductDTO);

    @Operation(summary = "Update HorticultureProduct (delegates to HorticultureProductFacade.update)", tags = {"HorticultureProduct"},
            description = "Updates a resource using HorticultureProductFacade.update with a HorticultureProductDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HorticultureProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HorticultureProductDTO> update(
            @Parameter(description = "HorticultureProduct payload used for update", required = true)
            @RequestBody HorticultureProductDTO HorticultureProductDTO);

    @Operation(summary = "Get HorticultureProducts page (delegates to HorticultureProductFacade.getAllPage)", tags = {"HorticultureProduct"},
            description = "Returns a paginated list using HorticultureProductFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HorticultureProductDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HorticultureProduct by ID (delegates to HorticultureProductFacade.getById)" , tags = {"HorticultureProduct"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HorticultureProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HorticultureProductDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HorticultureProduct (delegates to HorticultureProductFacade.delete)" , tags = {"HorticultureProduct"})
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

    @Operation(summary = "Get HorticultureProduct by IDs (delegates to HorticultureProductFacade.getAllByIds)" , tags = {"HorticultureProduct"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HorticultureProductDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
