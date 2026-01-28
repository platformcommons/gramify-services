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

import com.platformcommons.platform.service.blockprofile.dto.VillageSurplusProduceProfileDTO;

/**
 * API contract for VillageSurplusProduceProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-surplus-produce-profiles")
@Tag(name = "VillageSurplusProduceProfile", description = "Operations for VillageSurplusProduceProfile that delegate to VillageSurplusProduceProfileFacade")
public interface VillageSurplusProduceProfileControllerApi {

    @Operation(summary = "Create VillageSurplusProduceProfile (delegates to VillageSurplusProduceProfileFacade.save)", tags = {"VillageSurplusProduceProfile"},
            description = "Creates a new resource using VillageSurplusProduceProfileFacade.save with a VillageSurplusProduceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageSurplusProduceProfile payload used for creation", required = true)
            @RequestBody VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO);

    @Operation(summary = "Update VillageSurplusProduceProfile (delegates to VillageSurplusProduceProfileFacade.update)", tags = {"VillageSurplusProduceProfile"},
            description = "Updates a resource using VillageSurplusProduceProfileFacade.update with a VillageSurplusProduceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageSurplusProduceProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageSurplusProduceProfileDTO> update(
            @Parameter(description = "VillageSurplusProduceProfile payload used for update", required = true)
            @RequestBody VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO);

    @Operation(summary = "Get VillageSurplusProduceProfiles page (delegates to VillageSurplusProduceProfileFacade.getAllPage)", tags = {"VillageSurplusProduceProfile"},
            description = "Returns a paginated list using VillageSurplusProduceProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageSurplusProduceProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageSurplusProduceProfile by ID (delegates to VillageSurplusProduceProfileFacade.getById)" , tags = {"VillageSurplusProduceProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageSurplusProduceProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageSurplusProduceProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageSurplusProduceProfile (delegates to VillageSurplusProduceProfileFacade.delete)" , tags = {"VillageSurplusProduceProfile"})
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

    @Operation(summary = "Get VillageSurplusProduceProfile by IDs (delegates to VillageSurplusProduceProfileFacade.getAllByIds)" , tags = {"VillageSurplusProduceProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageSurplusProduceProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
