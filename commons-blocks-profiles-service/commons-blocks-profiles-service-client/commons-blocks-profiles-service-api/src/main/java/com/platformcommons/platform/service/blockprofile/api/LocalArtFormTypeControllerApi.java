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

import com.platformcommons.platform.service.blockprofile.dto.LocalArtFormTypeDTO;

/**
 * API contract for LocalArtFormTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/local-art-form-types")
@Tag(name = "LocalArtFormType", description = "Operations for LocalArtFormType that delegate to LocalArtFormTypeFacade")
public interface LocalArtFormTypeControllerApi {

    @Operation(summary = "Create LocalArtFormType (delegates to LocalArtFormTypeFacade.save)", tags = {"LocalArtFormType"},
            description = "Creates a new resource using LocalArtFormTypeFacade.save with a LocalArtFormTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "LocalArtFormType payload used for creation", required = true)
            @RequestBody LocalArtFormTypeDTO LocalArtFormTypeDTO);

    @Operation(summary = "Update LocalArtFormType (delegates to LocalArtFormTypeFacade.update)", tags = {"LocalArtFormType"},
            description = "Updates a resource using LocalArtFormTypeFacade.update with a LocalArtFormTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = LocalArtFormTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LocalArtFormTypeDTO> update(
            @Parameter(description = "LocalArtFormType payload used for update", required = true)
            @RequestBody LocalArtFormTypeDTO LocalArtFormTypeDTO);

    @Operation(summary = "Get LocalArtFormTypes page (delegates to LocalArtFormTypeFacade.getAllPage)", tags = {"LocalArtFormType"},
            description = "Returns a paginated list using LocalArtFormTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<LocalArtFormTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get LocalArtFormType by ID (delegates to LocalArtFormTypeFacade.getById)" , tags = {"LocalArtFormType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = LocalArtFormTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<LocalArtFormTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete LocalArtFormType (delegates to LocalArtFormTypeFacade.delete)" , tags = {"LocalArtFormType"})
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

    @Operation(summary = "Get LocalArtFormType by IDs (delegates to LocalArtFormTypeFacade.getAllByIds)" , tags = {"LocalArtFormType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<LocalArtFormTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
