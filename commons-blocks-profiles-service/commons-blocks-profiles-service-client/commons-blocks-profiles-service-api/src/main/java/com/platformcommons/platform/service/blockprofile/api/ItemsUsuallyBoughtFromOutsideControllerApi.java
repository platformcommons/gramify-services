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

import com.platformcommons.platform.service.blockprofile.dto.ItemsUsuallyBoughtFromOutsideDTO;

/**
 * API contract for ItemsUsuallyBoughtFromOutsideController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/items-usually-bought-from-outsides")
@Tag(name = "ItemsUsuallyBoughtFromOutside", description = "Operations for ItemsUsuallyBoughtFromOutside that delegate to ItemsUsuallyBoughtFromOutsideFacade")
public interface ItemsUsuallyBoughtFromOutsideControllerApi {

    @Operation(summary = "Create ItemsUsuallyBoughtFromOutside (delegates to ItemsUsuallyBoughtFromOutsideFacade.save)", tags = {"ItemsUsuallyBoughtFromOutside"},
            description = "Creates a new resource using ItemsUsuallyBoughtFromOutsideFacade.save with a ItemsUsuallyBoughtFromOutsideDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "ItemsUsuallyBoughtFromOutside payload used for creation", required = true)
            @RequestBody ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO);

    @Operation(summary = "Update ItemsUsuallyBoughtFromOutside (delegates to ItemsUsuallyBoughtFromOutsideFacade.update)", tags = {"ItemsUsuallyBoughtFromOutside"},
            description = "Updates a resource using ItemsUsuallyBoughtFromOutsideFacade.update with a ItemsUsuallyBoughtFromOutsideDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ItemsUsuallyBoughtFromOutsideDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ItemsUsuallyBoughtFromOutsideDTO> update(
            @Parameter(description = "ItemsUsuallyBoughtFromOutside payload used for update", required = true)
            @RequestBody ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO);

    @Operation(summary = "Get ItemsUsuallyBoughtFromOutsides page (delegates to ItemsUsuallyBoughtFromOutsideFacade.getAllPage)", tags = {"ItemsUsuallyBoughtFromOutside"},
            description = "Returns a paginated list using ItemsUsuallyBoughtFromOutsideFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ItemsUsuallyBoughtFromOutsideDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get ItemsUsuallyBoughtFromOutside by ID (delegates to ItemsUsuallyBoughtFromOutsideFacade.getById)" , tags = {"ItemsUsuallyBoughtFromOutside"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ItemsUsuallyBoughtFromOutsideDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ItemsUsuallyBoughtFromOutsideDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete ItemsUsuallyBoughtFromOutside (delegates to ItemsUsuallyBoughtFromOutsideFacade.delete)" , tags = {"ItemsUsuallyBoughtFromOutside"})
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

    @Operation(summary = "Get ItemsUsuallyBoughtFromOutside by IDs (delegates to ItemsUsuallyBoughtFromOutsideFacade.getAllByIds)" , tags = {"ItemsUsuallyBoughtFromOutside"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ItemsUsuallyBoughtFromOutsideDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
