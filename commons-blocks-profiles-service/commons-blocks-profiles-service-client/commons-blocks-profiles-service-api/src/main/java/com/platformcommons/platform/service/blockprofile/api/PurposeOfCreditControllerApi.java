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

import com.platformcommons.platform.service.blockprofile.dto.PurposeOfCreditDTO;

/**
 * API contract for PurposeOfCreditController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/purpose-of-credits")
@Tag(name = "PurposeOfCredit", description = "Operations for PurposeOfCredit that delegate to PurposeOfCreditFacade")
public interface PurposeOfCreditControllerApi {

    @Operation(summary = "Create PurposeOfCredit (delegates to PurposeOfCreditFacade.save)", tags = {"PurposeOfCredit"},
            description = "Creates a new resource using PurposeOfCreditFacade.save with a PurposeOfCreditDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "PurposeOfCredit payload used for creation", required = true)
            @RequestBody PurposeOfCreditDTO PurposeOfCreditDTO);

    @Operation(summary = "Update PurposeOfCredit (delegates to PurposeOfCreditFacade.update)", tags = {"PurposeOfCredit"},
            description = "Updates a resource using PurposeOfCreditFacade.update with a PurposeOfCreditDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = PurposeOfCreditDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PurposeOfCreditDTO> update(
            @Parameter(description = "PurposeOfCredit payload used for update", required = true)
            @RequestBody PurposeOfCreditDTO PurposeOfCreditDTO);

    @Operation(summary = "Get PurposeOfCredits page (delegates to PurposeOfCreditFacade.getAllPage)", tags = {"PurposeOfCredit"},
            description = "Returns a paginated list using PurposeOfCreditFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<PurposeOfCreditDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get PurposeOfCredit by ID (delegates to PurposeOfCreditFacade.getById)" , tags = {"PurposeOfCredit"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = PurposeOfCreditDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<PurposeOfCreditDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete PurposeOfCredit (delegates to PurposeOfCreditFacade.delete)" , tags = {"PurposeOfCredit"})
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

    @Operation(summary = "Get PurposeOfCredit by IDs (delegates to PurposeOfCreditFacade.getAllByIds)" , tags = {"PurposeOfCredit"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<PurposeOfCreditDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
