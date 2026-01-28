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

import com.platformcommons.platform.service.blockprofile.dto.RawMaterialsNeededForIndustryDTO;

/**
 * API contract for RawMaterialsNeededForIndustryController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/raw-materials-needed-for-industries")
@Tag(name = "RawMaterialsNeededForIndustry", description = "Operations for RawMaterialsNeededForIndustry that delegate to RawMaterialsNeededForIndustryFacade")
public interface RawMaterialsNeededForIndustryControllerApi {

    @Operation(summary = "Create RawMaterialsNeededForIndustry (delegates to RawMaterialsNeededForIndustryFacade.save)", tags = {"RawMaterialsNeededForIndustry"},
            description = "Creates a new resource using RawMaterialsNeededForIndustryFacade.save with a RawMaterialsNeededForIndustryDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "RawMaterialsNeededForIndustry payload used for creation", required = true)
            @RequestBody RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO);

    @Operation(summary = "Update RawMaterialsNeededForIndustry (delegates to RawMaterialsNeededForIndustryFacade.update)", tags = {"RawMaterialsNeededForIndustry"},
            description = "Updates a resource using RawMaterialsNeededForIndustryFacade.update with a RawMaterialsNeededForIndustryDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = RawMaterialsNeededForIndustryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RawMaterialsNeededForIndustryDTO> update(
            @Parameter(description = "RawMaterialsNeededForIndustry payload used for update", required = true)
            @RequestBody RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO);

    @Operation(summary = "Get RawMaterialsNeededForIndustrys page (delegates to RawMaterialsNeededForIndustryFacade.getAllPage)", tags = {"RawMaterialsNeededForIndustry"},
            description = "Returns a paginated list using RawMaterialsNeededForIndustryFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<RawMaterialsNeededForIndustryDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get RawMaterialsNeededForIndustry by ID (delegates to RawMaterialsNeededForIndustryFacade.getById)" , tags = {"RawMaterialsNeededForIndustry"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = RawMaterialsNeededForIndustryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<RawMaterialsNeededForIndustryDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete RawMaterialsNeededForIndustry (delegates to RawMaterialsNeededForIndustryFacade.delete)" , tags = {"RawMaterialsNeededForIndustry"})
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

    @Operation(summary = "Get RawMaterialsNeededForIndustry by IDs (delegates to RawMaterialsNeededForIndustryFacade.getAllByIds)" , tags = {"RawMaterialsNeededForIndustry"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<RawMaterialsNeededForIndustryDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
