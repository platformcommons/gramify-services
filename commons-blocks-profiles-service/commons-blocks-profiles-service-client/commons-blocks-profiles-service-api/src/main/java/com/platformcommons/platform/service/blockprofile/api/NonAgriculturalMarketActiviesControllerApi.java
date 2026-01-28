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

import com.platformcommons.platform.service.blockprofile.dto.NonAgriculturalMarketActiviesDTO;

/**
 * API contract for NonAgriculturalMarketActiviesController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/non-agricultural-market-activieses")
@Tag(name = "NonAgriculturalMarketActivies", description = "Operations for NonAgriculturalMarketActivies that delegate to NonAgriculturalMarketActiviesFacade")
public interface NonAgriculturalMarketActiviesControllerApi {

    @Operation(summary = "Create NonAgriculturalMarketActivies (delegates to NonAgriculturalMarketActiviesFacade.save)", tags = {"NonAgriculturalMarketActivies"},
            description = "Creates a new resource using NonAgriculturalMarketActiviesFacade.save with a NonAgriculturalMarketActiviesDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "NonAgriculturalMarketActivies payload used for creation", required = true)
            @RequestBody NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO);

    @Operation(summary = "Update NonAgriculturalMarketActivies (delegates to NonAgriculturalMarketActiviesFacade.update)", tags = {"NonAgriculturalMarketActivies"},
            description = "Updates a resource using NonAgriculturalMarketActiviesFacade.update with a NonAgriculturalMarketActiviesDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = NonAgriculturalMarketActiviesDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NonAgriculturalMarketActiviesDTO> update(
            @Parameter(description = "NonAgriculturalMarketActivies payload used for update", required = true)
            @RequestBody NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO);

    @Operation(summary = "Get NonAgriculturalMarketActiviess page (delegates to NonAgriculturalMarketActiviesFacade.getAllPage)", tags = {"NonAgriculturalMarketActivies"},
            description = "Returns a paginated list using NonAgriculturalMarketActiviesFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<NonAgriculturalMarketActiviesDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get NonAgriculturalMarketActivies by ID (delegates to NonAgriculturalMarketActiviesFacade.getById)" , tags = {"NonAgriculturalMarketActivies"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = NonAgriculturalMarketActiviesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<NonAgriculturalMarketActiviesDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete NonAgriculturalMarketActivies (delegates to NonAgriculturalMarketActiviesFacade.delete)" , tags = {"NonAgriculturalMarketActivies"})
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

    @Operation(summary = "Get NonAgriculturalMarketActivies by IDs (delegates to NonAgriculturalMarketActiviesFacade.getAllByIds)" , tags = {"NonAgriculturalMarketActivies"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<NonAgriculturalMarketActiviesDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
