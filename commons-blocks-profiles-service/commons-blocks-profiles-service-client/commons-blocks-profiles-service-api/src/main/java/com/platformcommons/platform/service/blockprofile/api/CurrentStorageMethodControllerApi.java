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

import com.platformcommons.platform.service.blockprofile.dto.CurrentStorageMethodDTO;

/**
 * API contract for CurrentStorageMethodController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/current-storage-methods")
@Tag(name = "CurrentStorageMethod", description = "Operations for CurrentStorageMethod that delegate to CurrentStorageMethodFacade")
public interface CurrentStorageMethodControllerApi {

    @Operation(summary = "Create CurrentStorageMethod (delegates to CurrentStorageMethodFacade.save)", tags = {"CurrentStorageMethod"},
            description = "Creates a new resource using CurrentStorageMethodFacade.save with a CurrentStorageMethodDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "CurrentStorageMethod payload used for creation", required = true)
            @RequestBody CurrentStorageMethodDTO CurrentStorageMethodDTO);

    @Operation(summary = "Update CurrentStorageMethod (delegates to CurrentStorageMethodFacade.update)", tags = {"CurrentStorageMethod"},
            description = "Updates a resource using CurrentStorageMethodFacade.update with a CurrentStorageMethodDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = CurrentStorageMethodDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CurrentStorageMethodDTO> update(
            @Parameter(description = "CurrentStorageMethod payload used for update", required = true)
            @RequestBody CurrentStorageMethodDTO CurrentStorageMethodDTO);

    @Operation(summary = "Get CurrentStorageMethods page (delegates to CurrentStorageMethodFacade.getAllPage)", tags = {"CurrentStorageMethod"},
            description = "Returns a paginated list using CurrentStorageMethodFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<CurrentStorageMethodDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get CurrentStorageMethod by ID (delegates to CurrentStorageMethodFacade.getById)" , tags = {"CurrentStorageMethod"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = CurrentStorageMethodDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CurrentStorageMethodDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete CurrentStorageMethod (delegates to CurrentStorageMethodFacade.delete)" , tags = {"CurrentStorageMethod"})
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

    @Operation(summary = "Get CurrentStorageMethod by IDs (delegates to CurrentStorageMethodFacade.getAllByIds)" , tags = {"CurrentStorageMethod"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<CurrentStorageMethodDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
