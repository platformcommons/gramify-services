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

import com.platformcommons.platform.service.blockprofile.dto.SeasonalityOfSurplusDTO;

/**
 * API contract for SeasonalityOfSurplusController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/seasonality-of-surpluses")
@Tag(name = "SeasonalityOfSurplus", description = "Operations for SeasonalityOfSurplus that delegate to SeasonalityOfSurplusFacade")
public interface SeasonalityOfSurplusControllerApi {

    @Operation(summary = "Create SeasonalityOfSurplus (delegates to SeasonalityOfSurplusFacade.save)", tags = {"SeasonalityOfSurplus"},
            description = "Creates a new resource using SeasonalityOfSurplusFacade.save with a SeasonalityOfSurplusDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "SeasonalityOfSurplus payload used for creation", required = true)
            @RequestBody SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO);

    @Operation(summary = "Update SeasonalityOfSurplus (delegates to SeasonalityOfSurplusFacade.update)", tags = {"SeasonalityOfSurplus"},
            description = "Updates a resource using SeasonalityOfSurplusFacade.update with a SeasonalityOfSurplusDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = SeasonalityOfSurplusDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SeasonalityOfSurplusDTO> update(
            @Parameter(description = "SeasonalityOfSurplus payload used for update", required = true)
            @RequestBody SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO);

    @Operation(summary = "Get SeasonalityOfSurpluss page (delegates to SeasonalityOfSurplusFacade.getAllPage)", tags = {"SeasonalityOfSurplus"},
            description = "Returns a paginated list using SeasonalityOfSurplusFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<SeasonalityOfSurplusDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get SeasonalityOfSurplus by ID (delegates to SeasonalityOfSurplusFacade.getById)" , tags = {"SeasonalityOfSurplus"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = SeasonalityOfSurplusDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<SeasonalityOfSurplusDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete SeasonalityOfSurplus (delegates to SeasonalityOfSurplusFacade.delete)" , tags = {"SeasonalityOfSurplus"})
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

    @Operation(summary = "Get SeasonalityOfSurplus by IDs (delegates to SeasonalityOfSurplusFacade.getAllByIds)" , tags = {"SeasonalityOfSurplus"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<SeasonalityOfSurplusDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
