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

import com.platformcommons.platform.service.blockprofile.dto.VillageSoilTypeDTO;

/**
 * API contract for VillageSoilTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-soil-types")
@Tag(name = "VillageSoilType", description = "Operations for VillageSoilType that delegate to VillageSoilTypeFacade")
public interface VillageSoilTypeControllerApi {

    @Operation(summary = "Create VillageSoilType (delegates to VillageSoilTypeFacade.save)", tags = {"VillageSoilType"},
            description = "Creates a new resource using VillageSoilTypeFacade.save with a VillageSoilTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageSoilType payload used for creation", required = true)
            @RequestBody VillageSoilTypeDTO VillageSoilTypeDTO);

    @Operation(summary = "Update VillageSoilType (delegates to VillageSoilTypeFacade.update)", tags = {"VillageSoilType"},
            description = "Updates a resource using VillageSoilTypeFacade.update with a VillageSoilTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageSoilTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageSoilTypeDTO> update(
            @Parameter(description = "VillageSoilType payload used for update", required = true)
            @RequestBody VillageSoilTypeDTO VillageSoilTypeDTO);

    @Operation(summary = "Get VillageSoilTypes page (delegates to VillageSoilTypeFacade.getAllPage)", tags = {"VillageSoilType"},
            description = "Returns a paginated list using VillageSoilTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageSoilTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageSoilType by ID (delegates to VillageSoilTypeFacade.getById)" , tags = {"VillageSoilType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageSoilTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageSoilTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageSoilType (delegates to VillageSoilTypeFacade.delete)" , tags = {"VillageSoilType"})
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

    @Operation(summary = "Get VillageSoilType by IDs (delegates to VillageSoilTypeFacade.getAllByIds)" , tags = {"VillageSoilType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageSoilTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
