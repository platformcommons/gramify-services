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

import com.platformcommons.platform.service.blockprofile.dto.CentralSchemeListDTO;

/**
 * API contract for CentralSchemeListController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/central-scheme-lists")
@Tag(name = "CentralSchemeList", description = "Operations for CentralSchemeList that delegate to CentralSchemeListFacade")
public interface CentralSchemeListControllerApi {

    @Operation(summary = "Create CentralSchemeList (delegates to CentralSchemeListFacade.save)", tags = {"CentralSchemeList"},
            description = "Creates a new resource using CentralSchemeListFacade.save with a CentralSchemeListDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "CentralSchemeList payload used for creation", required = true)
            @RequestBody CentralSchemeListDTO CentralSchemeListDTO);

    @Operation(summary = "Update CentralSchemeList (delegates to CentralSchemeListFacade.update)", tags = {"CentralSchemeList"},
            description = "Updates a resource using CentralSchemeListFacade.update with a CentralSchemeListDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = CentralSchemeListDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CentralSchemeListDTO> update(
            @Parameter(description = "CentralSchemeList payload used for update", required = true)
            @RequestBody CentralSchemeListDTO CentralSchemeListDTO);

    @Operation(summary = "Get CentralSchemeLists page (delegates to CentralSchemeListFacade.getAllPage)", tags = {"CentralSchemeList"},
            description = "Returns a paginated list using CentralSchemeListFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<CentralSchemeListDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get CentralSchemeList by ID (delegates to CentralSchemeListFacade.getById)" , tags = {"CentralSchemeList"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = CentralSchemeListDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CentralSchemeListDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete CentralSchemeList (delegates to CentralSchemeListFacade.delete)" , tags = {"CentralSchemeList"})
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

    @Operation(summary = "Get CentralSchemeList by IDs (delegates to CentralSchemeListFacade.getAllByIds)" , tags = {"CentralSchemeList"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<CentralSchemeListDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
