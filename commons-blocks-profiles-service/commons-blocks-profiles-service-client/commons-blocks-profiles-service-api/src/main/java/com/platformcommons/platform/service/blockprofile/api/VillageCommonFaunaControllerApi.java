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

import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFaunaDTO;

/**
 * API contract for VillageCommonFaunaController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-common-faunas")
@Tag(name = "VillageCommonFauna", description = "Operations for VillageCommonFauna that delegate to VillageCommonFaunaFacade")
public interface VillageCommonFaunaControllerApi {

    @Operation(summary = "Create VillageCommonFauna (delegates to VillageCommonFaunaFacade.save)", tags = {"VillageCommonFauna"},
            description = "Creates a new resource using VillageCommonFaunaFacade.save with a VillageCommonFaunaDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageCommonFauna payload used for creation", required = true)
            @RequestBody VillageCommonFaunaDTO VillageCommonFaunaDTO);

    @Operation(summary = "Update VillageCommonFauna (delegates to VillageCommonFaunaFacade.update)", tags = {"VillageCommonFauna"},
            description = "Updates a resource using VillageCommonFaunaFacade.update with a VillageCommonFaunaDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageCommonFaunaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageCommonFaunaDTO> update(
            @Parameter(description = "VillageCommonFauna payload used for update", required = true)
            @RequestBody VillageCommonFaunaDTO VillageCommonFaunaDTO);

    @Operation(summary = "Get VillageCommonFaunas page (delegates to VillageCommonFaunaFacade.getAllPage)", tags = {"VillageCommonFauna"},
            description = "Returns a paginated list using VillageCommonFaunaFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageCommonFaunaDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageCommonFauna by ID (delegates to VillageCommonFaunaFacade.getById)" , tags = {"VillageCommonFauna"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageCommonFaunaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageCommonFaunaDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageCommonFauna (delegates to VillageCommonFaunaFacade.delete)" , tags = {"VillageCommonFauna"})
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

    @Operation(summary = "Get VillageCommonFauna by IDs (delegates to VillageCommonFaunaFacade.getAllByIds)" , tags = {"VillageCommonFauna"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageCommonFaunaDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
