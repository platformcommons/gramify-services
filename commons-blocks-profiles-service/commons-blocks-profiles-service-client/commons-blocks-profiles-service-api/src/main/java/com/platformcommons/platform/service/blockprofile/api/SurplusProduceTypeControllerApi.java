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

import com.platformcommons.platform.service.blockprofile.dto.SurplusProduceTypeDTO;

/**
 * API contract for SurplusProduceTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/surplus-produce-types")
@Tag(name = "SurplusProduceType", description = "Operations for SurplusProduceType that delegate to SurplusProduceTypeFacade")
public interface SurplusProduceTypeControllerApi {

    @Operation(summary = "Create SurplusProduceType (delegates to SurplusProduceTypeFacade.save)", tags = {"SurplusProduceType"},
            description = "Creates a new resource using SurplusProduceTypeFacade.save with a SurplusProduceTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "SurplusProduceType payload used for creation", required = true)
            @RequestBody SurplusProduceTypeDTO SurplusProduceTypeDTO);

    @Operation(summary = "Update SurplusProduceType (delegates to SurplusProduceTypeFacade.update)", tags = {"SurplusProduceType"},
            description = "Updates a resource using SurplusProduceTypeFacade.update with a SurplusProduceTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = SurplusProduceTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SurplusProduceTypeDTO> update(
            @Parameter(description = "SurplusProduceType payload used for update", required = true)
            @RequestBody SurplusProduceTypeDTO SurplusProduceTypeDTO);

    @Operation(summary = "Get SurplusProduceTypes page (delegates to SurplusProduceTypeFacade.getAllPage)", tags = {"SurplusProduceType"},
            description = "Returns a paginated list using SurplusProduceTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<SurplusProduceTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get SurplusProduceType by ID (delegates to SurplusProduceTypeFacade.getById)" , tags = {"SurplusProduceType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = SurplusProduceTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<SurplusProduceTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete SurplusProduceType (delegates to SurplusProduceTypeFacade.delete)" , tags = {"SurplusProduceType"})
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

    @Operation(summary = "Get SurplusProduceType by IDs (delegates to SurplusProduceTypeFacade.getAllByIds)" , tags = {"SurplusProduceType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<SurplusProduceTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
