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

import com.platformcommons.platform.service.blockprofile.dto.VillageLivestockProfileDTO;

/**
 * API contract for VillageLivestockProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-livestock-profiles")
@Tag(name = "VillageLivestockProfile", description = "Operations for VillageLivestockProfile that delegate to VillageLivestockProfileFacade")
public interface VillageLivestockProfileControllerApi {

    @Operation(summary = "Create VillageLivestockProfile (delegates to VillageLivestockProfileFacade.save)", tags = {"VillageLivestockProfile"},
            description = "Creates a new resource using VillageLivestockProfileFacade.save with a VillageLivestockProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageLivestockProfile payload used for creation", required = true)
            @RequestBody VillageLivestockProfileDTO VillageLivestockProfileDTO);

    @Operation(summary = "Update VillageLivestockProfile (delegates to VillageLivestockProfileFacade.update)", tags = {"VillageLivestockProfile"},
            description = "Updates a resource using VillageLivestockProfileFacade.update with a VillageLivestockProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageLivestockProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageLivestockProfileDTO> update(
            @Parameter(description = "VillageLivestockProfile payload used for update", required = true)
            @RequestBody VillageLivestockProfileDTO VillageLivestockProfileDTO);

    @Operation(summary = "Get VillageLivestockProfiles page (delegates to VillageLivestockProfileFacade.getAllPage)", tags = {"VillageLivestockProfile"},
            description = "Returns a paginated list using VillageLivestockProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageLivestockProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageLivestockProfile by ID (delegates to VillageLivestockProfileFacade.getById)" , tags = {"VillageLivestockProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageLivestockProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageLivestockProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageLivestockProfile (delegates to VillageLivestockProfileFacade.delete)" , tags = {"VillageLivestockProfile"})
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

    @Operation(summary = "Get VillageLivestockProfile by IDs (delegates to VillageLivestockProfileFacade.getAllByIds)" , tags = {"VillageLivestockProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageLivestockProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
