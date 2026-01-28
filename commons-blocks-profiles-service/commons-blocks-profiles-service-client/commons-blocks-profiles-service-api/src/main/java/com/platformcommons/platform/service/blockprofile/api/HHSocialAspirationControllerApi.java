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

import com.platformcommons.platform.service.blockprofile.dto.HHSocialAspirationDTO;

/**
 * API contract for HHSocialAspirationController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-social-aspirations")
@Tag(name = "HHSocialAspiration", description = "Operations for HHSocialAspiration that delegate to HHSocialAspirationFacade")
public interface HHSocialAspirationControllerApi {

    @Operation(summary = "Create HHSocialAspiration (delegates to HHSocialAspirationFacade.save)", tags = {"HHSocialAspiration"},
            description = "Creates a new resource using HHSocialAspirationFacade.save with a HHSocialAspirationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHSocialAspiration payload used for creation", required = true)
            @RequestBody HHSocialAspirationDTO HHSocialAspirationDTO);

    @Operation(summary = "Update HHSocialAspiration (delegates to HHSocialAspirationFacade.update)", tags = {"HHSocialAspiration"},
            description = "Updates a resource using HHSocialAspirationFacade.update with a HHSocialAspirationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHSocialAspirationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHSocialAspirationDTO> update(
            @Parameter(description = "HHSocialAspiration payload used for update", required = true)
            @RequestBody HHSocialAspirationDTO HHSocialAspirationDTO);

    @Operation(summary = "Get HHSocialAspirations page (delegates to HHSocialAspirationFacade.getAllPage)", tags = {"HHSocialAspiration"},
            description = "Returns a paginated list using HHSocialAspirationFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHSocialAspirationDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHSocialAspiration by ID (delegates to HHSocialAspirationFacade.getById)" , tags = {"HHSocialAspiration"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHSocialAspirationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHSocialAspirationDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHSocialAspiration (delegates to HHSocialAspirationFacade.delete)" , tags = {"HHSocialAspiration"})
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

    @Operation(summary = "Get HHSocialAspiration by IDs (delegates to HHSocialAspirationFacade.getAllByIds)" , tags = {"HHSocialAspiration"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHSocialAspirationDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
