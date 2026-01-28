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

import com.platformcommons.platform.service.blockprofile.dto.MainSkilledTradesPresentDTO;

/**
 * API contract for MainSkilledTradesPresentController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/main-skilled-trades-presents")
@Tag(name = "MainSkilledTradesPresent", description = "Operations for MainSkilledTradesPresent that delegate to MainSkilledTradesPresentFacade")
public interface MainSkilledTradesPresentControllerApi {

    @Operation(summary = "Create MainSkilledTradesPresent (delegates to MainSkilledTradesPresentFacade.save)", tags = {"MainSkilledTradesPresent"},
            description = "Creates a new resource using MainSkilledTradesPresentFacade.save with a MainSkilledTradesPresentDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "MainSkilledTradesPresent payload used for creation", required = true)
            @RequestBody MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO);

    @Operation(summary = "Update MainSkilledTradesPresent (delegates to MainSkilledTradesPresentFacade.update)", tags = {"MainSkilledTradesPresent"},
            description = "Updates a resource using MainSkilledTradesPresentFacade.update with a MainSkilledTradesPresentDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = MainSkilledTradesPresentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MainSkilledTradesPresentDTO> update(
            @Parameter(description = "MainSkilledTradesPresent payload used for update", required = true)
            @RequestBody MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO);

    @Operation(summary = "Get MainSkilledTradesPresents page (delegates to MainSkilledTradesPresentFacade.getAllPage)", tags = {"MainSkilledTradesPresent"},
            description = "Returns a paginated list using MainSkilledTradesPresentFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<MainSkilledTradesPresentDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get MainSkilledTradesPresent by ID (delegates to MainSkilledTradesPresentFacade.getById)" , tags = {"MainSkilledTradesPresent"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = MainSkilledTradesPresentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<MainSkilledTradesPresentDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete MainSkilledTradesPresent (delegates to MainSkilledTradesPresentFacade.delete)" , tags = {"MainSkilledTradesPresent"})
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

    @Operation(summary = "Get MainSkilledTradesPresent by IDs (delegates to MainSkilledTradesPresentFacade.getAllByIds)" , tags = {"MainSkilledTradesPresent"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<MainSkilledTradesPresentDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
