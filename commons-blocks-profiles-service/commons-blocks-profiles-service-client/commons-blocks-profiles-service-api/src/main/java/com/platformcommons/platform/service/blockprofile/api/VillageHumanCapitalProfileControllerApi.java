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

import com.platformcommons.platform.service.blockprofile.dto.VillageHumanCapitalProfileDTO;

/**
 * API contract for VillageHumanCapitalProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-human-capital-profiles")
@Tag(name = "VillageHumanCapitalProfile", description = "Operations for VillageHumanCapitalProfile that delegate to VillageHumanCapitalProfileFacade")
public interface VillageHumanCapitalProfileControllerApi {

    @Operation(summary = "Create VillageHumanCapitalProfile (delegates to VillageHumanCapitalProfileFacade.save)", tags = {"VillageHumanCapitalProfile"},
            description = "Creates a new resource using VillageHumanCapitalProfileFacade.save with a VillageHumanCapitalProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageHumanCapitalProfile payload used for creation", required = true)
            @RequestBody VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO);

    @Operation(summary = "Update VillageHumanCapitalProfile (delegates to VillageHumanCapitalProfileFacade.update)", tags = {"VillageHumanCapitalProfile"},
            description = "Updates a resource using VillageHumanCapitalProfileFacade.update with a VillageHumanCapitalProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageHumanCapitalProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageHumanCapitalProfileDTO> update(
            @Parameter(description = "VillageHumanCapitalProfile payload used for update", required = true)
            @RequestBody VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO);

    @Operation(summary = "Get VillageHumanCapitalProfiles page (delegates to VillageHumanCapitalProfileFacade.getAllPage)", tags = {"VillageHumanCapitalProfile"},
            description = "Returns a paginated list using VillageHumanCapitalProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageHumanCapitalProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageHumanCapitalProfile by ID (delegates to VillageHumanCapitalProfileFacade.getById)" , tags = {"VillageHumanCapitalProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageHumanCapitalProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageHumanCapitalProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageHumanCapitalProfile (delegates to VillageHumanCapitalProfileFacade.delete)" , tags = {"VillageHumanCapitalProfile"})
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

    @Operation(summary = "Get VillageHumanCapitalProfile by IDs (delegates to VillageHumanCapitalProfileFacade.getAllByIds)" , tags = {"VillageHumanCapitalProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageHumanCapitalProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
