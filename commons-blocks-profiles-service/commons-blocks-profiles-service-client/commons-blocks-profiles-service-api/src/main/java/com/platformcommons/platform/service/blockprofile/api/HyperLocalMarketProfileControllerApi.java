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

import com.platformcommons.platform.service.blockprofile.dto.HyperLocalMarketProfileDTO;

/**
 * API contract for HyperLocalMarketProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hyper-local-market-profiles")
@Tag(name = "HyperLocalMarketProfile", description = "Operations for HyperLocalMarketProfile that delegate to HyperLocalMarketProfileFacade")
public interface HyperLocalMarketProfileControllerApi {

    @Operation(summary = "Create HyperLocalMarketProfile (delegates to HyperLocalMarketProfileFacade.save)", tags = {"HyperLocalMarketProfile"},
            description = "Creates a new resource using HyperLocalMarketProfileFacade.save with a HyperLocalMarketProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HyperLocalMarketProfile payload used for creation", required = true)
            @RequestBody HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO);

    @Operation(summary = "Update HyperLocalMarketProfile (delegates to HyperLocalMarketProfileFacade.update)", tags = {"HyperLocalMarketProfile"},
            description = "Updates a resource using HyperLocalMarketProfileFacade.update with a HyperLocalMarketProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HyperLocalMarketProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HyperLocalMarketProfileDTO> update(
            @Parameter(description = "HyperLocalMarketProfile payload used for update", required = true)
            @RequestBody HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO);

    @Operation(summary = "Get HyperLocalMarketProfiles page (delegates to HyperLocalMarketProfileFacade.getAllPage)", tags = {"HyperLocalMarketProfile"},
            description = "Returns a paginated list using HyperLocalMarketProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HyperLocalMarketProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HyperLocalMarketProfile by ID (delegates to HyperLocalMarketProfileFacade.getById)" , tags = {"HyperLocalMarketProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HyperLocalMarketProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HyperLocalMarketProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HyperLocalMarketProfile (delegates to HyperLocalMarketProfileFacade.delete)" , tags = {"HyperLocalMarketProfile"})
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

    @Operation(summary = "Get HyperLocalMarketProfile by IDs (delegates to HyperLocalMarketProfileFacade.getAllByIds)" , tags = {"HyperLocalMarketProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HyperLocalMarketProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
