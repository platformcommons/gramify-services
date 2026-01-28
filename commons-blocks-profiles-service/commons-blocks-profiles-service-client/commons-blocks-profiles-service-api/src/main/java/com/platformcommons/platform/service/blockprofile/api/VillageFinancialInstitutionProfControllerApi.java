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

import com.platformcommons.platform.service.blockprofile.dto.VillageFinancialInstitutionProfDTO;

/**
 * API contract for VillageFinancialInstitutionProfController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-financial-institution-profs")
@Tag(name = "VillageFinancialInstitutionProf", description = "Operations for VillageFinancialInstitutionProf that delegate to VillageFinancialInstitutionProfFacade")
public interface VillageFinancialInstitutionProfControllerApi {

    @Operation(summary = "Create VillageFinancialInstitutionProf (delegates to VillageFinancialInstitutionProfFacade.save)", tags = {"VillageFinancialInstitutionProf"},
            description = "Creates a new resource using VillageFinancialInstitutionProfFacade.save with a VillageFinancialInstitutionProfDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageFinancialInstitutionProf payload used for creation", required = true)
            @RequestBody VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO);

    @Operation(summary = "Update VillageFinancialInstitutionProf (delegates to VillageFinancialInstitutionProfFacade.update)", tags = {"VillageFinancialInstitutionProf"},
            description = "Updates a resource using VillageFinancialInstitutionProfFacade.update with a VillageFinancialInstitutionProfDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageFinancialInstitutionProfDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageFinancialInstitutionProfDTO> update(
            @Parameter(description = "VillageFinancialInstitutionProf payload used for update", required = true)
            @RequestBody VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO);

    @Operation(summary = "Get VillageFinancialInstitutionProfs page (delegates to VillageFinancialInstitutionProfFacade.getAllPage)", tags = {"VillageFinancialInstitutionProf"},
            description = "Returns a paginated list using VillageFinancialInstitutionProfFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageFinancialInstitutionProfDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageFinancialInstitutionProf by ID (delegates to VillageFinancialInstitutionProfFacade.getById)" , tags = {"VillageFinancialInstitutionProf"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageFinancialInstitutionProfDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageFinancialInstitutionProfDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageFinancialInstitutionProf (delegates to VillageFinancialInstitutionProfFacade.delete)" , tags = {"VillageFinancialInstitutionProf"})
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

    @Operation(summary = "Get VillageFinancialInstitutionProf by IDs (delegates to VillageFinancialInstitutionProfFacade.getAllByIds)" , tags = {"VillageFinancialInstitutionProf"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageFinancialInstitutionProfDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
