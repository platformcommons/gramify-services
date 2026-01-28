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

import com.platformcommons.platform.service.blockprofile.dto.PesticidesInDemandDTO;

/**
 * API contract for PesticidesInDemandController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/pesticides-in-demands")
@Tag(name = "PesticidesInDemand", description = "Operations for PesticidesInDemand that delegate to PesticidesInDemandFacade")
public interface PesticidesInDemandControllerApi {

    @Operation(summary = "Create PesticidesInDemand (delegates to PesticidesInDemandFacade.save)", tags = {"PesticidesInDemand"},
            description = "Creates a new resource using PesticidesInDemandFacade.save with a PesticidesInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "PesticidesInDemand payload used for creation", required = true)
            @RequestBody PesticidesInDemandDTO PesticidesInDemandDTO);

    @Operation(summary = "Update PesticidesInDemand (delegates to PesticidesInDemandFacade.update)", tags = {"PesticidesInDemand"},
            description = "Updates a resource using PesticidesInDemandFacade.update with a PesticidesInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = PesticidesInDemandDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PesticidesInDemandDTO> update(
            @Parameter(description = "PesticidesInDemand payload used for update", required = true)
            @RequestBody PesticidesInDemandDTO PesticidesInDemandDTO);

    @Operation(summary = "Get PesticidesInDemands page (delegates to PesticidesInDemandFacade.getAllPage)", tags = {"PesticidesInDemand"},
            description = "Returns a paginated list using PesticidesInDemandFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<PesticidesInDemandDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get PesticidesInDemand by ID (delegates to PesticidesInDemandFacade.getById)" , tags = {"PesticidesInDemand"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = PesticidesInDemandDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<PesticidesInDemandDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete PesticidesInDemand (delegates to PesticidesInDemandFacade.delete)" , tags = {"PesticidesInDemand"})
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

    @Operation(summary = "Get PesticidesInDemand by IDs (delegates to PesticidesInDemandFacade.getAllByIds)" , tags = {"PesticidesInDemand"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<PesticidesInDemandDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
