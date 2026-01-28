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

import com.platformcommons.platform.service.blockprofile.dto.CroppingSeasonDTO;

/**
 * API contract for CroppingSeasonController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/cropping-seasons")
@Tag(name = "CroppingSeason", description = "Operations for CroppingSeason that delegate to CroppingSeasonFacade")
public interface CroppingSeasonControllerApi {

    @Operation(summary = "Create CroppingSeason (delegates to CroppingSeasonFacade.save)", tags = {"CroppingSeason"},
            description = "Creates a new resource using CroppingSeasonFacade.save with a CroppingSeasonDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "CroppingSeason payload used for creation", required = true)
            @RequestBody CroppingSeasonDTO CroppingSeasonDTO);

    @Operation(summary = "Update CroppingSeason (delegates to CroppingSeasonFacade.update)", tags = {"CroppingSeason"},
            description = "Updates a resource using CroppingSeasonFacade.update with a CroppingSeasonDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = CroppingSeasonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CroppingSeasonDTO> update(
            @Parameter(description = "CroppingSeason payload used for update", required = true)
            @RequestBody CroppingSeasonDTO CroppingSeasonDTO);

    @Operation(summary = "Get CroppingSeasons page (delegates to CroppingSeasonFacade.getAllPage)", tags = {"CroppingSeason"},
            description = "Returns a paginated list using CroppingSeasonFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<CroppingSeasonDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get CroppingSeason by ID (delegates to CroppingSeasonFacade.getById)" , tags = {"CroppingSeason"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = CroppingSeasonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CroppingSeasonDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete CroppingSeason (delegates to CroppingSeasonFacade.delete)" , tags = {"CroppingSeason"})
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

    @Operation(summary = "Get CroppingSeason by IDs (delegates to CroppingSeasonFacade.getAllByIds)" , tags = {"CroppingSeason"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<CroppingSeasonDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
