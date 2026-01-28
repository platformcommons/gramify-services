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

import com.platformcommons.platform.service.blockprofile.dto.VillageCommunicationInfrastructDTO;

/**
 * API contract for VillageCommunicationInfrastructController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-communication-infrastructs")
@Tag(name = "VillageCommunicationInfrastruct", description = "Operations for VillageCommunicationInfrastruct that delegate to VillageCommunicationInfrastructFacade")
public interface VillageCommunicationInfrastructControllerApi {

    @Operation(summary = "Create VillageCommunicationInfrastruct (delegates to VillageCommunicationInfrastructFacade.save)", tags = {"VillageCommunicationInfrastruct"},
            description = "Creates a new resource using VillageCommunicationInfrastructFacade.save with a VillageCommunicationInfrastructDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageCommunicationInfrastruct payload used for creation", required = true)
            @RequestBody VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO);

    @Operation(summary = "Update VillageCommunicationInfrastruct (delegates to VillageCommunicationInfrastructFacade.update)", tags = {"VillageCommunicationInfrastruct"},
            description = "Updates a resource using VillageCommunicationInfrastructFacade.update with a VillageCommunicationInfrastructDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageCommunicationInfrastructDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageCommunicationInfrastructDTO> update(
            @Parameter(description = "VillageCommunicationInfrastruct payload used for update", required = true)
            @RequestBody VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO);

    @Operation(summary = "Get VillageCommunicationInfrastructs page (delegates to VillageCommunicationInfrastructFacade.getAllPage)", tags = {"VillageCommunicationInfrastruct"},
            description = "Returns a paginated list using VillageCommunicationInfrastructFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageCommunicationInfrastructDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageCommunicationInfrastruct by ID (delegates to VillageCommunicationInfrastructFacade.getById)" , tags = {"VillageCommunicationInfrastruct"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageCommunicationInfrastructDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageCommunicationInfrastructDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageCommunicationInfrastruct (delegates to VillageCommunicationInfrastructFacade.delete)" , tags = {"VillageCommunicationInfrastruct"})
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

    @Operation(summary = "Get VillageCommunicationInfrastruct by IDs (delegates to VillageCommunicationInfrastructFacade.getAllByIds)" , tags = {"VillageCommunicationInfrastruct"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageCommunicationInfrastructDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
