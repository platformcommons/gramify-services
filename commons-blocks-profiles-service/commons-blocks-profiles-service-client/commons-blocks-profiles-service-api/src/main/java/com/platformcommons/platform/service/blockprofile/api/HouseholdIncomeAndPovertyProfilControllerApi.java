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

import com.platformcommons.platform.service.blockprofile.dto.HouseholdIncomeAndPovertyProfilDTO;

/**
 * API contract for HouseholdIncomeAndPovertyProfilController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/household-income-and-poverty-profils")
@Tag(name = "HouseholdIncomeAndPovertyProfil", description = "Operations for HouseholdIncomeAndPovertyProfil that delegate to HouseholdIncomeAndPovertyProfilFacade")
public interface HouseholdIncomeAndPovertyProfilControllerApi {

    @Operation(summary = "Create HouseholdIncomeAndPovertyProfil (delegates to HouseholdIncomeAndPovertyProfilFacade.save)", tags = {"HouseholdIncomeAndPovertyProfil"},
            description = "Creates a new resource using HouseholdIncomeAndPovertyProfilFacade.save with a HouseholdIncomeAndPovertyProfilDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HouseholdIncomeAndPovertyProfil payload used for creation", required = true)
            @RequestBody HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO);

    @Operation(summary = "Update HouseholdIncomeAndPovertyProfil (delegates to HouseholdIncomeAndPovertyProfilFacade.update)", tags = {"HouseholdIncomeAndPovertyProfil"},
            description = "Updates a resource using HouseholdIncomeAndPovertyProfilFacade.update with a HouseholdIncomeAndPovertyProfilDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HouseholdIncomeAndPovertyProfilDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HouseholdIncomeAndPovertyProfilDTO> update(
            @Parameter(description = "HouseholdIncomeAndPovertyProfil payload used for update", required = true)
            @RequestBody HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO);

    @Operation(summary = "Get HouseholdIncomeAndPovertyProfils page (delegates to HouseholdIncomeAndPovertyProfilFacade.getAllPage)", tags = {"HouseholdIncomeAndPovertyProfil"},
            description = "Returns a paginated list using HouseholdIncomeAndPovertyProfilFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HouseholdIncomeAndPovertyProfilDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HouseholdIncomeAndPovertyProfil by ID (delegates to HouseholdIncomeAndPovertyProfilFacade.getById)" , tags = {"HouseholdIncomeAndPovertyProfil"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HouseholdIncomeAndPovertyProfilDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HouseholdIncomeAndPovertyProfilDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HouseholdIncomeAndPovertyProfil (delegates to HouseholdIncomeAndPovertyProfilFacade.delete)" , tags = {"HouseholdIncomeAndPovertyProfil"})
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

    @Operation(summary = "Get HouseholdIncomeAndPovertyProfil by IDs (delegates to HouseholdIncomeAndPovertyProfilFacade.getAllByIds)" , tags = {"HouseholdIncomeAndPovertyProfil"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HouseholdIncomeAndPovertyProfilDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
