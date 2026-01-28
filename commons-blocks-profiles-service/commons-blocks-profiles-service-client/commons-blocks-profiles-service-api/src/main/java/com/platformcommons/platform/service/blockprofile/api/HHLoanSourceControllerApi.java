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

import com.platformcommons.platform.service.blockprofile.dto.HHLoanSourceDTO;

/**
 * API contract for HHLoanSourceController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-loan-sources")
@Tag(name = "HHLoanSource", description = "Operations for HHLoanSource that delegate to HHLoanSourceFacade")
public interface HHLoanSourceControllerApi {

    @Operation(summary = "Create HHLoanSource (delegates to HHLoanSourceFacade.save)", tags = {"HHLoanSource"},
            description = "Creates a new resource using HHLoanSourceFacade.save with a HHLoanSourceDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHLoanSource payload used for creation", required = true)
            @RequestBody HHLoanSourceDTO HHLoanSourceDTO);

    @Operation(summary = "Update HHLoanSource (delegates to HHLoanSourceFacade.update)", tags = {"HHLoanSource"},
            description = "Updates a resource using HHLoanSourceFacade.update with a HHLoanSourceDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHLoanSourceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHLoanSourceDTO> update(
            @Parameter(description = "HHLoanSource payload used for update", required = true)
            @RequestBody HHLoanSourceDTO HHLoanSourceDTO);

    @Operation(summary = "Get HHLoanSources page (delegates to HHLoanSourceFacade.getAllPage)", tags = {"HHLoanSource"},
            description = "Returns a paginated list using HHLoanSourceFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHLoanSourceDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHLoanSource by ID (delegates to HHLoanSourceFacade.getById)" , tags = {"HHLoanSource"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHLoanSourceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHLoanSourceDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHLoanSource (delegates to HHLoanSourceFacade.delete)" , tags = {"HHLoanSource"})
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

    @Operation(summary = "Get HHLoanSource by IDs (delegates to HHLoanSourceFacade.getAllByIds)" , tags = {"HHLoanSource"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHLoanSourceDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
