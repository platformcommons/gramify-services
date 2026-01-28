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

import com.platformcommons.platform.service.blockprofile.dto.VillageHorticultureProfileDTO;

/**
 * API contract for VillageHorticultureProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-horticulture-profiles")
@Tag(name = "VillageHorticultureProfile", description = "Operations for VillageHorticultureProfile that delegate to VillageHorticultureProfileFacade")
public interface VillageHorticultureProfileControllerApi {

    @Operation(summary = "Create VillageHorticultureProfile (delegates to VillageHorticultureProfileFacade.save)", tags = {"VillageHorticultureProfile"},
            description = "Creates a new resource using VillageHorticultureProfileFacade.save with a VillageHorticultureProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageHorticultureProfile payload used for creation", required = true)
            @RequestBody VillageHorticultureProfileDTO VillageHorticultureProfileDTO);

    @Operation(summary = "Update VillageHorticultureProfile (delegates to VillageHorticultureProfileFacade.update)", tags = {"VillageHorticultureProfile"},
            description = "Updates a resource using VillageHorticultureProfileFacade.update with a VillageHorticultureProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageHorticultureProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageHorticultureProfileDTO> update(
            @Parameter(description = "VillageHorticultureProfile payload used for update", required = true)
            @RequestBody VillageHorticultureProfileDTO VillageHorticultureProfileDTO);

    @Operation(summary = "Get VillageHorticultureProfiles page (delegates to VillageHorticultureProfileFacade.getAllPage)", tags = {"VillageHorticultureProfile"},
            description = "Returns a paginated list using VillageHorticultureProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageHorticultureProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageHorticultureProfile by ID (delegates to VillageHorticultureProfileFacade.getById)" , tags = {"VillageHorticultureProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageHorticultureProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageHorticultureProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageHorticultureProfile (delegates to VillageHorticultureProfileFacade.delete)" , tags = {"VillageHorticultureProfile"})
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

    @Operation(summary = "Get VillageHorticultureProfile by IDs (delegates to VillageHorticultureProfileFacade.getAllByIds)" , tags = {"VillageHorticultureProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageHorticultureProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
