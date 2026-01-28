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

import com.platformcommons.platform.service.blockprofile.dto.VillageDigitalConnectivityProfiDTO;

/**
 * API contract for VillageDigitalConnectivityProfiController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-digital-connectivity-profis")
@Tag(name = "VillageDigitalConnectivityProfi", description = "Operations for VillageDigitalConnectivityProfi that delegate to VillageDigitalConnectivityProfiFacade")
public interface VillageDigitalConnectivityProfiControllerApi {

    @Operation(summary = "Create VillageDigitalConnectivityProfi (delegates to VillageDigitalConnectivityProfiFacade.save)", tags = {"VillageDigitalConnectivityProfi"},
            description = "Creates a new resource using VillageDigitalConnectivityProfiFacade.save with a VillageDigitalConnectivityProfiDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageDigitalConnectivityProfi payload used for creation", required = true)
            @RequestBody VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO);

    @Operation(summary = "Update VillageDigitalConnectivityProfi (delegates to VillageDigitalConnectivityProfiFacade.update)", tags = {"VillageDigitalConnectivityProfi"},
            description = "Updates a resource using VillageDigitalConnectivityProfiFacade.update with a VillageDigitalConnectivityProfiDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageDigitalConnectivityProfiDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageDigitalConnectivityProfiDTO> update(
            @Parameter(description = "VillageDigitalConnectivityProfi payload used for update", required = true)
            @RequestBody VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO);

    @Operation(summary = "Get VillageDigitalConnectivityProfis page (delegates to VillageDigitalConnectivityProfiFacade.getAllPage)", tags = {"VillageDigitalConnectivityProfi"},
            description = "Returns a paginated list using VillageDigitalConnectivityProfiFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageDigitalConnectivityProfiDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageDigitalConnectivityProfi by ID (delegates to VillageDigitalConnectivityProfiFacade.getById)" , tags = {"VillageDigitalConnectivityProfi"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageDigitalConnectivityProfiDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageDigitalConnectivityProfiDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageDigitalConnectivityProfi (delegates to VillageDigitalConnectivityProfiFacade.delete)" , tags = {"VillageDigitalConnectivityProfi"})
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

    @Operation(summary = "Get VillageDigitalConnectivityProfi by IDs (delegates to VillageDigitalConnectivityProfiFacade.getAllByIds)" , tags = {"VillageDigitalConnectivityProfi"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageDigitalConnectivityProfiDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
