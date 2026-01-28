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

import com.platformcommons.platform.service.blockprofile.dto.VillageMarketProfileDTO;

/**
 * API contract for VillageMarketProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-market-profiles")
@Tag(name = "VillageMarketProfile", description = "Operations for VillageMarketProfile that delegate to VillageMarketProfileFacade")
public interface VillageMarketProfileControllerApi {

    @Operation(summary = "Create VillageMarketProfile (delegates to VillageMarketProfileFacade.save)", tags = {"VillageMarketProfile"},
            description = "Creates a new resource using VillageMarketProfileFacade.save with a VillageMarketProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageMarketProfile payload used for creation", required = true)
            @RequestBody VillageMarketProfileDTO VillageMarketProfileDTO);

    @Operation(summary = "Update VillageMarketProfile (delegates to VillageMarketProfileFacade.update)", tags = {"VillageMarketProfile"},
            description = "Updates a resource using VillageMarketProfileFacade.update with a VillageMarketProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageMarketProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageMarketProfileDTO> update(
            @Parameter(description = "VillageMarketProfile payload used for update", required = true)
            @RequestBody VillageMarketProfileDTO VillageMarketProfileDTO);

    @Operation(summary = "Get VillageMarketProfiles page (delegates to VillageMarketProfileFacade.getAllPage)", tags = {"VillageMarketProfile"},
            description = "Returns a paginated list using VillageMarketProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageMarketProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageMarketProfile by ID (delegates to VillageMarketProfileFacade.getById)" , tags = {"VillageMarketProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageMarketProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageMarketProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageMarketProfile (delegates to VillageMarketProfileFacade.delete)" , tags = {"VillageMarketProfile"})
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

    @Operation(summary = "Get VillageMarketProfile by IDs (delegates to VillageMarketProfileFacade.getAllByIds)" , tags = {"VillageMarketProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageMarketProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
