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

import com.platformcommons.platform.service.blockprofile.dto.HouseholdFinancialBehaviourDTO;

/**
 * API contract for HouseholdFinancialBehaviourController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/household-financial-behaviours")
@Tag(name = "HouseholdFinancialBehaviour", description = "Operations for HouseholdFinancialBehaviour that delegate to HouseholdFinancialBehaviourFacade")
public interface HouseholdFinancialBehaviourControllerApi {

    @Operation(summary = "Create HouseholdFinancialBehaviour (delegates to HouseholdFinancialBehaviourFacade.save)", tags = {"HouseholdFinancialBehaviour"},
            description = "Creates a new resource using HouseholdFinancialBehaviourFacade.save with a HouseholdFinancialBehaviourDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HouseholdFinancialBehaviour payload used for creation", required = true)
            @RequestBody HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO);

    @Operation(summary = "Update HouseholdFinancialBehaviour (delegates to HouseholdFinancialBehaviourFacade.update)", tags = {"HouseholdFinancialBehaviour"},
            description = "Updates a resource using HouseholdFinancialBehaviourFacade.update with a HouseholdFinancialBehaviourDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HouseholdFinancialBehaviourDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HouseholdFinancialBehaviourDTO> update(
            @Parameter(description = "HouseholdFinancialBehaviour payload used for update", required = true)
            @RequestBody HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO);

    @Operation(summary = "Get HouseholdFinancialBehaviours page (delegates to HouseholdFinancialBehaviourFacade.getAllPage)", tags = {"HouseholdFinancialBehaviour"},
            description = "Returns a paginated list using HouseholdFinancialBehaviourFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HouseholdFinancialBehaviourDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HouseholdFinancialBehaviour by ID (delegates to HouseholdFinancialBehaviourFacade.getById)" , tags = {"HouseholdFinancialBehaviour"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HouseholdFinancialBehaviourDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HouseholdFinancialBehaviourDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HouseholdFinancialBehaviour (delegates to HouseholdFinancialBehaviourFacade.delete)" , tags = {"HouseholdFinancialBehaviour"})
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

    @Operation(summary = "Get HouseholdFinancialBehaviour by IDs (delegates to HouseholdFinancialBehaviourFacade.getAllByIds)" , tags = {"HouseholdFinancialBehaviour"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HouseholdFinancialBehaviourDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
