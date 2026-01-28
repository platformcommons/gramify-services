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

import com.platformcommons.platform.service.blockprofile.dto.VillageCulturalResourceProfileDTO;

/**
 * API contract for VillageCulturalResourceProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-cultural-resource-profiles")
@Tag(name = "VillageCulturalResourceProfile", description = "Operations for VillageCulturalResourceProfile that delegate to VillageCulturalResourceProfileFacade")
public interface VillageCulturalResourceProfileControllerApi {

    @Operation(summary = "Create VillageCulturalResourceProfile (delegates to VillageCulturalResourceProfileFacade.save)", tags = {"VillageCulturalResourceProfile"},
            description = "Creates a new resource using VillageCulturalResourceProfileFacade.save with a VillageCulturalResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageCulturalResourceProfile payload used for creation", required = true)
            @RequestBody VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO);

    @Operation(summary = "Update VillageCulturalResourceProfile (delegates to VillageCulturalResourceProfileFacade.update)", tags = {"VillageCulturalResourceProfile"},
            description = "Updates a resource using VillageCulturalResourceProfileFacade.update with a VillageCulturalResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageCulturalResourceProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageCulturalResourceProfileDTO> update(
            @Parameter(description = "VillageCulturalResourceProfile payload used for update", required = true)
            @RequestBody VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO);

    @Operation(summary = "Get VillageCulturalResourceProfiles page (delegates to VillageCulturalResourceProfileFacade.getAllPage)", tags = {"VillageCulturalResourceProfile"},
            description = "Returns a paginated list using VillageCulturalResourceProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageCulturalResourceProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageCulturalResourceProfile by ID (delegates to VillageCulturalResourceProfileFacade.getById)" , tags = {"VillageCulturalResourceProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageCulturalResourceProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageCulturalResourceProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageCulturalResourceProfile (delegates to VillageCulturalResourceProfileFacade.delete)" , tags = {"VillageCulturalResourceProfile"})
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

    @Operation(summary = "Get VillageCulturalResourceProfile by IDs (delegates to VillageCulturalResourceProfileFacade.getAllByIds)" , tags = {"VillageCulturalResourceProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageCulturalResourceProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
