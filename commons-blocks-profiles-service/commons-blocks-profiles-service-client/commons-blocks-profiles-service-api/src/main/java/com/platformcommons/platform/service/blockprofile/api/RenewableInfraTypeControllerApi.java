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

import com.platformcommons.platform.service.blockprofile.dto.RenewableInfraTypeDTO;

/**
 * API contract for RenewableInfraTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/renewable-infra-types")
@Tag(name = "RenewableInfraType", description = "Operations for RenewableInfraType that delegate to RenewableInfraTypeFacade")
public interface RenewableInfraTypeControllerApi {

    @Operation(summary = "Create RenewableInfraType (delegates to RenewableInfraTypeFacade.save)", tags = {"RenewableInfraType"},
            description = "Creates a new resource using RenewableInfraTypeFacade.save with a RenewableInfraTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "RenewableInfraType payload used for creation", required = true)
            @RequestBody RenewableInfraTypeDTO RenewableInfraTypeDTO);

    @Operation(summary = "Update RenewableInfraType (delegates to RenewableInfraTypeFacade.update)", tags = {"RenewableInfraType"},
            description = "Updates a resource using RenewableInfraTypeFacade.update with a RenewableInfraTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = RenewableInfraTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RenewableInfraTypeDTO> update(
            @Parameter(description = "RenewableInfraType payload used for update", required = true)
            @RequestBody RenewableInfraTypeDTO RenewableInfraTypeDTO);

    @Operation(summary = "Get RenewableInfraTypes page (delegates to RenewableInfraTypeFacade.getAllPage)", tags = {"RenewableInfraType"},
            description = "Returns a paginated list using RenewableInfraTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<RenewableInfraTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get RenewableInfraType by ID (delegates to RenewableInfraTypeFacade.getById)" , tags = {"RenewableInfraType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = RenewableInfraTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<RenewableInfraTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete RenewableInfraType (delegates to RenewableInfraTypeFacade.delete)" , tags = {"RenewableInfraType"})
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

    @Operation(summary = "Get RenewableInfraType by IDs (delegates to RenewableInfraTypeFacade.getAllByIds)" , tags = {"RenewableInfraType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<RenewableInfraTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
