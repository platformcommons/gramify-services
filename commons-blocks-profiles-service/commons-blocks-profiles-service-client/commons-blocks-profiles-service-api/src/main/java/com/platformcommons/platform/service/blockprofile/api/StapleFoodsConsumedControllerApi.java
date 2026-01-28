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

import com.platformcommons.platform.service.blockprofile.dto.StapleFoodsConsumedDTO;

/**
 * API contract for StapleFoodsConsumedController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/staple-foods-consumeds")
@Tag(name = "StapleFoodsConsumed", description = "Operations for StapleFoodsConsumed that delegate to StapleFoodsConsumedFacade")
public interface StapleFoodsConsumedControllerApi {

    @Operation(summary = "Create StapleFoodsConsumed (delegates to StapleFoodsConsumedFacade.save)", tags = {"StapleFoodsConsumed"},
            description = "Creates a new resource using StapleFoodsConsumedFacade.save with a StapleFoodsConsumedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "StapleFoodsConsumed payload used for creation", required = true)
            @RequestBody StapleFoodsConsumedDTO StapleFoodsConsumedDTO);

    @Operation(summary = "Update StapleFoodsConsumed (delegates to StapleFoodsConsumedFacade.update)", tags = {"StapleFoodsConsumed"},
            description = "Updates a resource using StapleFoodsConsumedFacade.update with a StapleFoodsConsumedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = StapleFoodsConsumedDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StapleFoodsConsumedDTO> update(
            @Parameter(description = "StapleFoodsConsumed payload used for update", required = true)
            @RequestBody StapleFoodsConsumedDTO StapleFoodsConsumedDTO);

    @Operation(summary = "Get StapleFoodsConsumeds page (delegates to StapleFoodsConsumedFacade.getAllPage)", tags = {"StapleFoodsConsumed"},
            description = "Returns a paginated list using StapleFoodsConsumedFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<StapleFoodsConsumedDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get StapleFoodsConsumed by ID (delegates to StapleFoodsConsumedFacade.getById)" , tags = {"StapleFoodsConsumed"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = StapleFoodsConsumedDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<StapleFoodsConsumedDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete StapleFoodsConsumed (delegates to StapleFoodsConsumedFacade.delete)" , tags = {"StapleFoodsConsumed"})
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

    @Operation(summary = "Get StapleFoodsConsumed by IDs (delegates to StapleFoodsConsumedFacade.getAllByIds)" , tags = {"StapleFoodsConsumed"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<StapleFoodsConsumedDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
