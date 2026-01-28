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

import com.platformcommons.platform.service.blockprofile.dto.WhereFarmersBuyInputDTO;

/**
 * API contract for WhereFarmersBuyInputController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/where-farmers-buy-inputs")
@Tag(name = "WhereFarmersBuyInput", description = "Operations for WhereFarmersBuyInput that delegate to WhereFarmersBuyInputFacade")
public interface WhereFarmersBuyInputControllerApi {

    @Operation(summary = "Create WhereFarmersBuyInput (delegates to WhereFarmersBuyInputFacade.save)", tags = {"WhereFarmersBuyInput"},
            description = "Creates a new resource using WhereFarmersBuyInputFacade.save with a WhereFarmersBuyInputDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "WhereFarmersBuyInput payload used for creation", required = true)
            @RequestBody WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO);

    @Operation(summary = "Update WhereFarmersBuyInput (delegates to WhereFarmersBuyInputFacade.update)", tags = {"WhereFarmersBuyInput"},
            description = "Updates a resource using WhereFarmersBuyInputFacade.update with a WhereFarmersBuyInputDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = WhereFarmersBuyInputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WhereFarmersBuyInputDTO> update(
            @Parameter(description = "WhereFarmersBuyInput payload used for update", required = true)
            @RequestBody WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO);

    @Operation(summary = "Get WhereFarmersBuyInputs page (delegates to WhereFarmersBuyInputFacade.getAllPage)", tags = {"WhereFarmersBuyInput"},
            description = "Returns a paginated list using WhereFarmersBuyInputFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<WhereFarmersBuyInputDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get WhereFarmersBuyInput by ID (delegates to WhereFarmersBuyInputFacade.getById)" , tags = {"WhereFarmersBuyInput"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = WhereFarmersBuyInputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<WhereFarmersBuyInputDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete WhereFarmersBuyInput (delegates to WhereFarmersBuyInputFacade.delete)" , tags = {"WhereFarmersBuyInput"})
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

    @Operation(summary = "Get WhereFarmersBuyInput by IDs (delegates to WhereFarmersBuyInputFacade.getAllByIds)" , tags = {"WhereFarmersBuyInput"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<WhereFarmersBuyInputDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
