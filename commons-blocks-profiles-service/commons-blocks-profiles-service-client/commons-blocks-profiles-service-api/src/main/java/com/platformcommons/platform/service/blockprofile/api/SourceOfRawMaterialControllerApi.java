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

import com.platformcommons.platform.service.blockprofile.dto.SourceOfRawMaterialDTO;

/**
 * API contract for SourceOfRawMaterialController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/source-of-raw-materials")
@Tag(name = "SourceOfRawMaterial", description = "Operations for SourceOfRawMaterial that delegate to SourceOfRawMaterialFacade")
public interface SourceOfRawMaterialControllerApi {

    @Operation(summary = "Create SourceOfRawMaterial (delegates to SourceOfRawMaterialFacade.save)", tags = {"SourceOfRawMaterial"},
            description = "Creates a new resource using SourceOfRawMaterialFacade.save with a SourceOfRawMaterialDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "SourceOfRawMaterial payload used for creation", required = true)
            @RequestBody SourceOfRawMaterialDTO SourceOfRawMaterialDTO);

    @Operation(summary = "Update SourceOfRawMaterial (delegates to SourceOfRawMaterialFacade.update)", tags = {"SourceOfRawMaterial"},
            description = "Updates a resource using SourceOfRawMaterialFacade.update with a SourceOfRawMaterialDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = SourceOfRawMaterialDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SourceOfRawMaterialDTO> update(
            @Parameter(description = "SourceOfRawMaterial payload used for update", required = true)
            @RequestBody SourceOfRawMaterialDTO SourceOfRawMaterialDTO);

    @Operation(summary = "Get SourceOfRawMaterials page (delegates to SourceOfRawMaterialFacade.getAllPage)", tags = {"SourceOfRawMaterial"},
            description = "Returns a paginated list using SourceOfRawMaterialFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<SourceOfRawMaterialDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get SourceOfRawMaterial by ID (delegates to SourceOfRawMaterialFacade.getById)" , tags = {"SourceOfRawMaterial"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = SourceOfRawMaterialDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<SourceOfRawMaterialDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete SourceOfRawMaterial (delegates to SourceOfRawMaterialFacade.delete)" , tags = {"SourceOfRawMaterial"})
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

    @Operation(summary = "Get SourceOfRawMaterial by IDs (delegates to SourceOfRawMaterialFacade.getAllByIds)" , tags = {"SourceOfRawMaterial"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<SourceOfRawMaterialDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
