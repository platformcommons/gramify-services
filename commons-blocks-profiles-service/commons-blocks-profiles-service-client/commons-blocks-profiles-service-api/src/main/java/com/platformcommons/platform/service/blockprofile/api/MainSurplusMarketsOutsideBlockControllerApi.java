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

import com.platformcommons.platform.service.blockprofile.dto.MainSurplusMarketsOutsideBlockDTO;

/**
 * API contract for MainSurplusMarketsOutsideBlockController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/main-surplus-markets-outside-blocks")
@Tag(name = "MainSurplusMarketsOutsideBlock", description = "Operations for MainSurplusMarketsOutsideBlock that delegate to MainSurplusMarketsOutsideBlockFacade")
public interface MainSurplusMarketsOutsideBlockControllerApi {

    @Operation(summary = "Create MainSurplusMarketsOutsideBlock (delegates to MainSurplusMarketsOutsideBlockFacade.save)", tags = {"MainSurplusMarketsOutsideBlock"},
            description = "Creates a new resource using MainSurplusMarketsOutsideBlockFacade.save with a MainSurplusMarketsOutsideBlockDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "MainSurplusMarketsOutsideBlock payload used for creation", required = true)
            @RequestBody MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO);

    @Operation(summary = "Update MainSurplusMarketsOutsideBlock (delegates to MainSurplusMarketsOutsideBlockFacade.update)", tags = {"MainSurplusMarketsOutsideBlock"},
            description = "Updates a resource using MainSurplusMarketsOutsideBlockFacade.update with a MainSurplusMarketsOutsideBlockDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = MainSurplusMarketsOutsideBlockDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MainSurplusMarketsOutsideBlockDTO> update(
            @Parameter(description = "MainSurplusMarketsOutsideBlock payload used for update", required = true)
            @RequestBody MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO);

    @Operation(summary = "Get MainSurplusMarketsOutsideBlocks page (delegates to MainSurplusMarketsOutsideBlockFacade.getAllPage)", tags = {"MainSurplusMarketsOutsideBlock"},
            description = "Returns a paginated list using MainSurplusMarketsOutsideBlockFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<MainSurplusMarketsOutsideBlockDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get MainSurplusMarketsOutsideBlock by ID (delegates to MainSurplusMarketsOutsideBlockFacade.getById)" , tags = {"MainSurplusMarketsOutsideBlock"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = MainSurplusMarketsOutsideBlockDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<MainSurplusMarketsOutsideBlockDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete MainSurplusMarketsOutsideBlock (delegates to MainSurplusMarketsOutsideBlockFacade.delete)" , tags = {"MainSurplusMarketsOutsideBlock"})
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

    @Operation(summary = "Get MainSurplusMarketsOutsideBlock by IDs (delegates to MainSurplusMarketsOutsideBlockFacade.getAllByIds)" , tags = {"MainSurplusMarketsOutsideBlock"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<MainSurplusMarketsOutsideBlockDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
