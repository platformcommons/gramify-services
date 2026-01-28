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

import com.platformcommons.platform.service.blockprofile.dto.MainNicheMarketDTO;

/**
 * API contract for MainNicheMarketController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/main-niche-markets")
@Tag(name = "MainNicheMarket", description = "Operations for MainNicheMarket that delegate to MainNicheMarketFacade")
public interface MainNicheMarketControllerApi {

    @Operation(summary = "Create MainNicheMarket (delegates to MainNicheMarketFacade.save)", tags = {"MainNicheMarket"},
            description = "Creates a new resource using MainNicheMarketFacade.save with a MainNicheMarketDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "MainNicheMarket payload used for creation", required = true)
            @RequestBody MainNicheMarketDTO MainNicheMarketDTO);

    @Operation(summary = "Update MainNicheMarket (delegates to MainNicheMarketFacade.update)", tags = {"MainNicheMarket"},
            description = "Updates a resource using MainNicheMarketFacade.update with a MainNicheMarketDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = MainNicheMarketDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MainNicheMarketDTO> update(
            @Parameter(description = "MainNicheMarket payload used for update", required = true)
            @RequestBody MainNicheMarketDTO MainNicheMarketDTO);

    @Operation(summary = "Get MainNicheMarkets page (delegates to MainNicheMarketFacade.getAllPage)", tags = {"MainNicheMarket"},
            description = "Returns a paginated list using MainNicheMarketFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<MainNicheMarketDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get MainNicheMarket by ID (delegates to MainNicheMarketFacade.getById)" , tags = {"MainNicheMarket"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = MainNicheMarketDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<MainNicheMarketDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete MainNicheMarket (delegates to MainNicheMarketFacade.delete)" , tags = {"MainNicheMarket"})
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

    @Operation(summary = "Get MainNicheMarket by IDs (delegates to MainNicheMarketFacade.getAllByIds)" , tags = {"MainNicheMarket"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<MainNicheMarketDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
