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

import com.platformcommons.platform.service.blockprofile.dto.VillageEnvironmentalRiskProfileDTO;

/**
 * API contract for VillageEnvironmentalRiskProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-environmental-risk-profiles")
@Tag(name = "VillageEnvironmentalRiskProfile", description = "Operations for VillageEnvironmentalRiskProfile that delegate to VillageEnvironmentalRiskProfileFacade")
public interface VillageEnvironmentalRiskProfileControllerApi {

    @Operation(summary = "Create VillageEnvironmentalRiskProfile (delegates to VillageEnvironmentalRiskProfileFacade.save)", tags = {"VillageEnvironmentalRiskProfile"},
            description = "Creates a new resource using VillageEnvironmentalRiskProfileFacade.save with a VillageEnvironmentalRiskProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageEnvironmentalRiskProfile payload used for creation", required = true)
            @RequestBody VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO);

    @Operation(summary = "Update VillageEnvironmentalRiskProfile (delegates to VillageEnvironmentalRiskProfileFacade.update)", tags = {"VillageEnvironmentalRiskProfile"},
            description = "Updates a resource using VillageEnvironmentalRiskProfileFacade.update with a VillageEnvironmentalRiskProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageEnvironmentalRiskProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageEnvironmentalRiskProfileDTO> update(
            @Parameter(description = "VillageEnvironmentalRiskProfile payload used for update", required = true)
            @RequestBody VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO);

    @Operation(summary = "Get VillageEnvironmentalRiskProfiles page (delegates to VillageEnvironmentalRiskProfileFacade.getAllPage)", tags = {"VillageEnvironmentalRiskProfile"},
            description = "Returns a paginated list using VillageEnvironmentalRiskProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageEnvironmentalRiskProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageEnvironmentalRiskProfile by ID (delegates to VillageEnvironmentalRiskProfileFacade.getById)" , tags = {"VillageEnvironmentalRiskProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageEnvironmentalRiskProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageEnvironmentalRiskProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageEnvironmentalRiskProfile (delegates to VillageEnvironmentalRiskProfileFacade.delete)" , tags = {"VillageEnvironmentalRiskProfile"})
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

    @Operation(summary = "Get VillageEnvironmentalRiskProfile by IDs (delegates to VillageEnvironmentalRiskProfileFacade.getAllByIds)" , tags = {"VillageEnvironmentalRiskProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageEnvironmentalRiskProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
