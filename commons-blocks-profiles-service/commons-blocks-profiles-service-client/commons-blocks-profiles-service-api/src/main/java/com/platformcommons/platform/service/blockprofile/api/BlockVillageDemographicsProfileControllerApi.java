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

import com.platformcommons.platform.service.blockprofile.dto.BlockVillageDemographicsProfileDTO;

/**
 * API contract for BlockVillageDemographicsProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/block-village-demographics-profiles")
@Tag(name = "BlockVillageDemographicsProfile", description = "Operations for BlockVillageDemographicsProfile that delegate to BlockVillageDemographicsProfileFacade")
public interface BlockVillageDemographicsProfileControllerApi {

    @Operation(summary = "Create BlockVillageDemographicsProfile (delegates to BlockVillageDemographicsProfileFacade.save)", tags = {"BlockVillageDemographicsProfile"},
            description = "Creates a new resource using BlockVillageDemographicsProfileFacade.save with a BlockVillageDemographicsProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "BlockVillageDemographicsProfile payload used for creation", required = true)
            @RequestBody BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfileDTO);

    @Operation(summary = "Update BlockVillageDemographicsProfile (delegates to BlockVillageDemographicsProfileFacade.update)", tags = {"BlockVillageDemographicsProfile"},
            description = "Updates a resource using BlockVillageDemographicsProfileFacade.update with a BlockVillageDemographicsProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = BlockVillageDemographicsProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BlockVillageDemographicsProfileDTO> update(
            @Parameter(description = "BlockVillageDemographicsProfile payload used for update", required = true)
            @RequestBody BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfileDTO);

    @Operation(summary = "Get BlockVillageDemographicsProfiles page (delegates to BlockVillageDemographicsProfileFacade.getAllPage)", tags = {"BlockVillageDemographicsProfile"},
            description = "Returns a paginated list using BlockVillageDemographicsProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<BlockVillageDemographicsProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get BlockVillageDemographicsProfile by ID (delegates to BlockVillageDemographicsProfileFacade.getById)" , tags = {"BlockVillageDemographicsProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = BlockVillageDemographicsProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<BlockVillageDemographicsProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete BlockVillageDemographicsProfile (delegates to BlockVillageDemographicsProfileFacade.delete)" , tags = {"BlockVillageDemographicsProfile"})
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

    @Operation(summary = "Get BlockVillageDemographicsProfile by IDs (delegates to BlockVillageDemographicsProfileFacade.getAllByIds)" , tags = {"BlockVillageDemographicsProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<BlockVillageDemographicsProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
