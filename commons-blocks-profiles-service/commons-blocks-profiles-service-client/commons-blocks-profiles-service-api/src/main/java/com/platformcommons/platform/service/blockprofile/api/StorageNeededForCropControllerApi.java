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

import com.platformcommons.platform.service.blockprofile.dto.StorageNeededForCropDTO;

/**
 * API contract for StorageNeededForCropController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/storage-needed-for-crops")
@Tag(name = "StorageNeededForCrop", description = "Operations for StorageNeededForCrop that delegate to StorageNeededForCropFacade")
public interface StorageNeededForCropControllerApi {

    @Operation(summary = "Create StorageNeededForCrop (delegates to StorageNeededForCropFacade.save)", tags = {"StorageNeededForCrop"},
            description = "Creates a new resource using StorageNeededForCropFacade.save with a StorageNeededForCropDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "StorageNeededForCrop payload used for creation", required = true)
            @RequestBody StorageNeededForCropDTO StorageNeededForCropDTO);

    @Operation(summary = "Update StorageNeededForCrop (delegates to StorageNeededForCropFacade.update)", tags = {"StorageNeededForCrop"},
            description = "Updates a resource using StorageNeededForCropFacade.update with a StorageNeededForCropDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = StorageNeededForCropDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StorageNeededForCropDTO> update(
            @Parameter(description = "StorageNeededForCrop payload used for update", required = true)
            @RequestBody StorageNeededForCropDTO StorageNeededForCropDTO);

    @Operation(summary = "Get StorageNeededForCrops page (delegates to StorageNeededForCropFacade.getAllPage)", tags = {"StorageNeededForCrop"},
            description = "Returns a paginated list using StorageNeededForCropFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<StorageNeededForCropDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get StorageNeededForCrop by ID (delegates to StorageNeededForCropFacade.getById)" , tags = {"StorageNeededForCrop"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = StorageNeededForCropDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<StorageNeededForCropDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete StorageNeededForCrop (delegates to StorageNeededForCropFacade.delete)" , tags = {"StorageNeededForCrop"})
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

    @Operation(summary = "Get StorageNeededForCrop by IDs (delegates to StorageNeededForCropFacade.getAllByIds)" , tags = {"StorageNeededForCrop"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<StorageNeededForCropDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
