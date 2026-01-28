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

import com.platformcommons.platform.service.blockprofile.dto.VillageTransportConnectivityIssDTO;

/**
 * API contract for VillageTransportConnectivityIssController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-transport-connectivity-isses")
@Tag(name = "VillageTransportConnectivityIss", description = "Operations for VillageTransportConnectivityIss that delegate to VillageTransportConnectivityIssFacade")
public interface VillageTransportConnectivityIssControllerApi {

    @Operation(summary = "Create VillageTransportConnectivityIss (delegates to VillageTransportConnectivityIssFacade.save)", tags = {"VillageTransportConnectivityIss"},
            description = "Creates a new resource using VillageTransportConnectivityIssFacade.save with a VillageTransportConnectivityIssDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageTransportConnectivityIss payload used for creation", required = true)
            @RequestBody VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO);

    @Operation(summary = "Update VillageTransportConnectivityIss (delegates to VillageTransportConnectivityIssFacade.update)", tags = {"VillageTransportConnectivityIss"},
            description = "Updates a resource using VillageTransportConnectivityIssFacade.update with a VillageTransportConnectivityIssDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageTransportConnectivityIssDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageTransportConnectivityIssDTO> update(
            @Parameter(description = "VillageTransportConnectivityIss payload used for update", required = true)
            @RequestBody VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO);

    @Operation(summary = "Get VillageTransportConnectivityIsss page (delegates to VillageTransportConnectivityIssFacade.getAllPage)", tags = {"VillageTransportConnectivityIss"},
            description = "Returns a paginated list using VillageTransportConnectivityIssFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageTransportConnectivityIssDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageTransportConnectivityIss by ID (delegates to VillageTransportConnectivityIssFacade.getById)" , tags = {"VillageTransportConnectivityIss"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageTransportConnectivityIssDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageTransportConnectivityIssDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageTransportConnectivityIss (delegates to VillageTransportConnectivityIssFacade.delete)" , tags = {"VillageTransportConnectivityIss"})
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

    @Operation(summary = "Get VillageTransportConnectivityIss by IDs (delegates to VillageTransportConnectivityIssFacade.getAllByIds)" , tags = {"VillageTransportConnectivityIss"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageTransportConnectivityIssDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
