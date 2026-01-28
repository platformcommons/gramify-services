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

import com.platformcommons.platform.service.blockprofile.dto.NicheProductBuyerTypeDTO;

/**
 * API contract for NicheProductBuyerTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/niche-product-buyer-types")
@Tag(name = "NicheProductBuyerType", description = "Operations for NicheProductBuyerType that delegate to NicheProductBuyerTypeFacade")
public interface NicheProductBuyerTypeControllerApi {

    @Operation(summary = "Create NicheProductBuyerType (delegates to NicheProductBuyerTypeFacade.save)", tags = {"NicheProductBuyerType"},
            description = "Creates a new resource using NicheProductBuyerTypeFacade.save with a NicheProductBuyerTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "NicheProductBuyerType payload used for creation", required = true)
            @RequestBody NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO);

    @Operation(summary = "Update NicheProductBuyerType (delegates to NicheProductBuyerTypeFacade.update)", tags = {"NicheProductBuyerType"},
            description = "Updates a resource using NicheProductBuyerTypeFacade.update with a NicheProductBuyerTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = NicheProductBuyerTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NicheProductBuyerTypeDTO> update(
            @Parameter(description = "NicheProductBuyerType payload used for update", required = true)
            @RequestBody NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO);

    @Operation(summary = "Get NicheProductBuyerTypes page (delegates to NicheProductBuyerTypeFacade.getAllPage)", tags = {"NicheProductBuyerType"},
            description = "Returns a paginated list using NicheProductBuyerTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<NicheProductBuyerTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get NicheProductBuyerType by ID (delegates to NicheProductBuyerTypeFacade.getById)" , tags = {"NicheProductBuyerType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = NicheProductBuyerTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<NicheProductBuyerTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete NicheProductBuyerType (delegates to NicheProductBuyerTypeFacade.delete)" , tags = {"NicheProductBuyerType"})
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

    @Operation(summary = "Get NicheProductBuyerType by IDs (delegates to NicheProductBuyerTypeFacade.getAllByIds)" , tags = {"NicheProductBuyerType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<NicheProductBuyerTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
