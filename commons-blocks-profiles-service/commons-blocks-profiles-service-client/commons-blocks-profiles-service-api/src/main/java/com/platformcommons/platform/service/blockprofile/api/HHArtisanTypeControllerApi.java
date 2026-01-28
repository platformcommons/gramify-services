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

import com.platformcommons.platform.service.blockprofile.dto.HHArtisanTypeDTO;

/**
 * API contract for HHArtisanTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-artisan-types")
@Tag(name = "HHArtisanType", description = "Operations for HHArtisanType that delegate to HHArtisanTypeFacade")
public interface HHArtisanTypeControllerApi {

    @Operation(summary = "Create HHArtisanType (delegates to HHArtisanTypeFacade.save)", tags = {"HHArtisanType"},
            description = "Creates a new resource using HHArtisanTypeFacade.save with a HHArtisanTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHArtisanType payload used for creation", required = true)
            @RequestBody HHArtisanTypeDTO HHArtisanTypeDTO);

    @Operation(summary = "Update HHArtisanType (delegates to HHArtisanTypeFacade.update)", tags = {"HHArtisanType"},
            description = "Updates a resource using HHArtisanTypeFacade.update with a HHArtisanTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHArtisanTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHArtisanTypeDTO> update(
            @Parameter(description = "HHArtisanType payload used for update", required = true)
            @RequestBody HHArtisanTypeDTO HHArtisanTypeDTO);

    @Operation(summary = "Get HHArtisanTypes page (delegates to HHArtisanTypeFacade.getAllPage)", tags = {"HHArtisanType"},
            description = "Returns a paginated list using HHArtisanTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHArtisanTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHArtisanType by ID (delegates to HHArtisanTypeFacade.getById)" , tags = {"HHArtisanType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHArtisanTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHArtisanTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHArtisanType (delegates to HHArtisanTypeFacade.delete)" , tags = {"HHArtisanType"})
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

    @Operation(summary = "Get HHArtisanType by IDs (delegates to HHArtisanTypeFacade.getAllByIds)" , tags = {"HHArtisanType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHArtisanTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
