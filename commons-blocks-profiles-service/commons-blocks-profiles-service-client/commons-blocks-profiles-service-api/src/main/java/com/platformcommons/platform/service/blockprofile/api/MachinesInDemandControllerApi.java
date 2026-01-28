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

import com.platformcommons.platform.service.blockprofile.dto.MachinesInDemandDTO;

/**
 * API contract for MachinesInDemandController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/machines-in-demands")
@Tag(name = "MachinesInDemand", description = "Operations for MachinesInDemand that delegate to MachinesInDemandFacade")
public interface MachinesInDemandControllerApi {

    @Operation(summary = "Create MachinesInDemand (delegates to MachinesInDemandFacade.save)", tags = {"MachinesInDemand"},
            description = "Creates a new resource using MachinesInDemandFacade.save with a MachinesInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "MachinesInDemand payload used for creation", required = true)
            @RequestBody MachinesInDemandDTO MachinesInDemandDTO);

    @Operation(summary = "Update MachinesInDemand (delegates to MachinesInDemandFacade.update)", tags = {"MachinesInDemand"},
            description = "Updates a resource using MachinesInDemandFacade.update with a MachinesInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = MachinesInDemandDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MachinesInDemandDTO> update(
            @Parameter(description = "MachinesInDemand payload used for update", required = true)
            @RequestBody MachinesInDemandDTO MachinesInDemandDTO);

    @Operation(summary = "Get MachinesInDemands page (delegates to MachinesInDemandFacade.getAllPage)", tags = {"MachinesInDemand"},
            description = "Returns a paginated list using MachinesInDemandFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<MachinesInDemandDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get MachinesInDemand by ID (delegates to MachinesInDemandFacade.getById)" , tags = {"MachinesInDemand"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = MachinesInDemandDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<MachinesInDemandDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete MachinesInDemand (delegates to MachinesInDemandFacade.delete)" , tags = {"MachinesInDemand"})
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

    @Operation(summary = "Get MachinesInDemand by IDs (delegates to MachinesInDemandFacade.getAllByIds)" , tags = {"MachinesInDemand"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<MachinesInDemandDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
