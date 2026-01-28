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

import com.platformcommons.platform.service.blockprofile.dto.WherePeopleGoForRepairsDTO;

/**
 * API contract for WherePeopleGoForRepairsController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/where-people-go-for-repairses")
@Tag(name = "WherePeopleGoForRepairs", description = "Operations for WherePeopleGoForRepairs that delegate to WherePeopleGoForRepairsFacade")
public interface WherePeopleGoForRepairsControllerApi {

    @Operation(summary = "Create WherePeopleGoForRepairs (delegates to WherePeopleGoForRepairsFacade.save)", tags = {"WherePeopleGoForRepairs"},
            description = "Creates a new resource using WherePeopleGoForRepairsFacade.save with a WherePeopleGoForRepairsDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "WherePeopleGoForRepairs payload used for creation", required = true)
            @RequestBody WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO);

    @Operation(summary = "Update WherePeopleGoForRepairs (delegates to WherePeopleGoForRepairsFacade.update)", tags = {"WherePeopleGoForRepairs"},
            description = "Updates a resource using WherePeopleGoForRepairsFacade.update with a WherePeopleGoForRepairsDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = WherePeopleGoForRepairsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WherePeopleGoForRepairsDTO> update(
            @Parameter(description = "WherePeopleGoForRepairs payload used for update", required = true)
            @RequestBody WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO);

    @Operation(summary = "Get WherePeopleGoForRepairss page (delegates to WherePeopleGoForRepairsFacade.getAllPage)", tags = {"WherePeopleGoForRepairs"},
            description = "Returns a paginated list using WherePeopleGoForRepairsFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<WherePeopleGoForRepairsDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get WherePeopleGoForRepairs by ID (delegates to WherePeopleGoForRepairsFacade.getById)" , tags = {"WherePeopleGoForRepairs"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = WherePeopleGoForRepairsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<WherePeopleGoForRepairsDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete WherePeopleGoForRepairs (delegates to WherePeopleGoForRepairsFacade.delete)" , tags = {"WherePeopleGoForRepairs"})
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

    @Operation(summary = "Get WherePeopleGoForRepairs by IDs (delegates to WherePeopleGoForRepairsFacade.getAllByIds)" , tags = {"WherePeopleGoForRepairs"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<WherePeopleGoForRepairsDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
