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

import com.platformcommons.platform.service.blockprofile.dto.VillageLandResourceProfileDTO;

/**
 * API contract for VillageLandResourceProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-land-resource-profiles")
@Tag(name = "VillageLandResourceProfile", description = "Operations for VillageLandResourceProfile that delegate to VillageLandResourceProfileFacade")
public interface VillageLandResourceProfileControllerApi {

    @Operation(summary = "Create VillageLandResourceProfile (delegates to VillageLandResourceProfileFacade.save)", tags = {"VillageLandResourceProfile"},
            description = "Creates a new resource using VillageLandResourceProfileFacade.save with a VillageLandResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageLandResourceProfile payload used for creation", required = true)
            @RequestBody VillageLandResourceProfileDTO VillageLandResourceProfileDTO);

    @Operation(summary = "Update VillageLandResourceProfile (delegates to VillageLandResourceProfileFacade.update)", tags = {"VillageLandResourceProfile"},
            description = "Updates a resource using VillageLandResourceProfileFacade.update with a VillageLandResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageLandResourceProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageLandResourceProfileDTO> update(
            @Parameter(description = "VillageLandResourceProfile payload used for update", required = true)
            @RequestBody VillageLandResourceProfileDTO VillageLandResourceProfileDTO);

    @Operation(summary = "Get VillageLandResourceProfiles page (delegates to VillageLandResourceProfileFacade.getAllPage)", tags = {"VillageLandResourceProfile"},
            description = "Returns a paginated list using VillageLandResourceProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageLandResourceProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageLandResourceProfile by ID (delegates to VillageLandResourceProfileFacade.getById)" , tags = {"VillageLandResourceProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageLandResourceProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageLandResourceProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageLandResourceProfile (delegates to VillageLandResourceProfileFacade.delete)" , tags = {"VillageLandResourceProfile"})
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

    @Operation(summary = "Get VillageLandResourceProfile by IDs (delegates to VillageLandResourceProfileFacade.getAllByIds)" , tags = {"VillageLandResourceProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageLandResourceProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
