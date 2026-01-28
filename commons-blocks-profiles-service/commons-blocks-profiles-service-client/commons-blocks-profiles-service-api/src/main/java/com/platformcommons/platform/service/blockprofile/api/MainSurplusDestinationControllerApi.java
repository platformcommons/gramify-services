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

import com.platformcommons.platform.service.blockprofile.dto.MainSurplusDestinationDTO;

/**
 * API contract for MainSurplusDestinationController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/main-surplus-destinations")
@Tag(name = "MainSurplusDestination", description = "Operations for MainSurplusDestination that delegate to MainSurplusDestinationFacade")
public interface MainSurplusDestinationControllerApi {

    @Operation(summary = "Create MainSurplusDestination (delegates to MainSurplusDestinationFacade.save)", tags = {"MainSurplusDestination"},
            description = "Creates a new resource using MainSurplusDestinationFacade.save with a MainSurplusDestinationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "MainSurplusDestination payload used for creation", required = true)
            @RequestBody MainSurplusDestinationDTO MainSurplusDestinationDTO);

    @Operation(summary = "Update MainSurplusDestination (delegates to MainSurplusDestinationFacade.update)", tags = {"MainSurplusDestination"},
            description = "Updates a resource using MainSurplusDestinationFacade.update with a MainSurplusDestinationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = MainSurplusDestinationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MainSurplusDestinationDTO> update(
            @Parameter(description = "MainSurplusDestination payload used for update", required = true)
            @RequestBody MainSurplusDestinationDTO MainSurplusDestinationDTO);

    @Operation(summary = "Get MainSurplusDestinations page (delegates to MainSurplusDestinationFacade.getAllPage)", tags = {"MainSurplusDestination"},
            description = "Returns a paginated list using MainSurplusDestinationFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<MainSurplusDestinationDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get MainSurplusDestination by ID (delegates to MainSurplusDestinationFacade.getById)" , tags = {"MainSurplusDestination"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = MainSurplusDestinationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<MainSurplusDestinationDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete MainSurplusDestination (delegates to MainSurplusDestinationFacade.delete)" , tags = {"MainSurplusDestination"})
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

    @Operation(summary = "Get MainSurplusDestination by IDs (delegates to MainSurplusDestinationFacade.getAllByIds)" , tags = {"MainSurplusDestination"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<MainSurplusDestinationDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
