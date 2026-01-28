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

import com.platformcommons.platform.service.blockprofile.dto.SupportNeededForNicheGrowthDTO;

/**
 * API contract for SupportNeededForNicheGrowthController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/support-needed-for-niche-growths")
@Tag(name = "SupportNeededForNicheGrowth", description = "Operations for SupportNeededForNicheGrowth that delegate to SupportNeededForNicheGrowthFacade")
public interface SupportNeededForNicheGrowthControllerApi {

    @Operation(summary = "Create SupportNeededForNicheGrowth (delegates to SupportNeededForNicheGrowthFacade.save)", tags = {"SupportNeededForNicheGrowth"},
            description = "Creates a new resource using SupportNeededForNicheGrowthFacade.save with a SupportNeededForNicheGrowthDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "SupportNeededForNicheGrowth payload used for creation", required = true)
            @RequestBody SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO);

    @Operation(summary = "Update SupportNeededForNicheGrowth (delegates to SupportNeededForNicheGrowthFacade.update)", tags = {"SupportNeededForNicheGrowth"},
            description = "Updates a resource using SupportNeededForNicheGrowthFacade.update with a SupportNeededForNicheGrowthDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = SupportNeededForNicheGrowthDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SupportNeededForNicheGrowthDTO> update(
            @Parameter(description = "SupportNeededForNicheGrowth payload used for update", required = true)
            @RequestBody SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO);

    @Operation(summary = "Get SupportNeededForNicheGrowths page (delegates to SupportNeededForNicheGrowthFacade.getAllPage)", tags = {"SupportNeededForNicheGrowth"},
            description = "Returns a paginated list using SupportNeededForNicheGrowthFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<SupportNeededForNicheGrowthDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get SupportNeededForNicheGrowth by ID (delegates to SupportNeededForNicheGrowthFacade.getById)" , tags = {"SupportNeededForNicheGrowth"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = SupportNeededForNicheGrowthDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<SupportNeededForNicheGrowthDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete SupportNeededForNicheGrowth (delegates to SupportNeededForNicheGrowthFacade.delete)" , tags = {"SupportNeededForNicheGrowth"})
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

    @Operation(summary = "Get SupportNeededForNicheGrowth by IDs (delegates to SupportNeededForNicheGrowthFacade.getAllByIds)" , tags = {"SupportNeededForNicheGrowth"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<SupportNeededForNicheGrowthDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
