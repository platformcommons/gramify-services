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

import com.platformcommons.platform.service.blockprofile.dto.ItemsUsuallyBoughtLocallyDTO;

/**
 * API contract for ItemsUsuallyBoughtLocallyController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/items-usually-bought-locallies")
@Tag(name = "ItemsUsuallyBoughtLocally", description = "Operations for ItemsUsuallyBoughtLocally that delegate to ItemsUsuallyBoughtLocallyFacade")
public interface ItemsUsuallyBoughtLocallyControllerApi {

    @Operation(summary = "Create ItemsUsuallyBoughtLocally (delegates to ItemsUsuallyBoughtLocallyFacade.save)", tags = {"ItemsUsuallyBoughtLocally"},
            description = "Creates a new resource using ItemsUsuallyBoughtLocallyFacade.save with a ItemsUsuallyBoughtLocallyDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "ItemsUsuallyBoughtLocally payload used for creation", required = true)
            @RequestBody ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO);

    @Operation(summary = "Update ItemsUsuallyBoughtLocally (delegates to ItemsUsuallyBoughtLocallyFacade.update)", tags = {"ItemsUsuallyBoughtLocally"},
            description = "Updates a resource using ItemsUsuallyBoughtLocallyFacade.update with a ItemsUsuallyBoughtLocallyDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ItemsUsuallyBoughtLocallyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ItemsUsuallyBoughtLocallyDTO> update(
            @Parameter(description = "ItemsUsuallyBoughtLocally payload used for update", required = true)
            @RequestBody ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO);

    @Operation(summary = "Get ItemsUsuallyBoughtLocallys page (delegates to ItemsUsuallyBoughtLocallyFacade.getAllPage)", tags = {"ItemsUsuallyBoughtLocally"},
            description = "Returns a paginated list using ItemsUsuallyBoughtLocallyFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ItemsUsuallyBoughtLocallyDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get ItemsUsuallyBoughtLocally by ID (delegates to ItemsUsuallyBoughtLocallyFacade.getById)" , tags = {"ItemsUsuallyBoughtLocally"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ItemsUsuallyBoughtLocallyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ItemsUsuallyBoughtLocallyDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete ItemsUsuallyBoughtLocally (delegates to ItemsUsuallyBoughtLocallyFacade.delete)" , tags = {"ItemsUsuallyBoughtLocally"})
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

    @Operation(summary = "Get ItemsUsuallyBoughtLocally by IDs (delegates to ItemsUsuallyBoughtLocallyFacade.getAllByIds)" , tags = {"ItemsUsuallyBoughtLocally"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ItemsUsuallyBoughtLocallyDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
