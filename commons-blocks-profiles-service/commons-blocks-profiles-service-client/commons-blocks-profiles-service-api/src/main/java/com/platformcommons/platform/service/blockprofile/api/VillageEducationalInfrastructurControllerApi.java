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

import com.platformcommons.platform.service.blockprofile.dto.VillageEducationalInfrastructurDTO;

/**
 * API contract for VillageEducationalInfrastructurController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-educational-infrastructurs")
@Tag(name = "VillageEducationalInfrastructur", description = "Operations for VillageEducationalInfrastructur that delegate to VillageEducationalInfrastructurFacade")
public interface VillageEducationalInfrastructurControllerApi {

    @Operation(summary = "Create VillageEducationalInfrastructur (delegates to VillageEducationalInfrastructurFacade.save)", tags = {"VillageEducationalInfrastructur"},
            description = "Creates a new resource using VillageEducationalInfrastructurFacade.save with a VillageEducationalInfrastructurDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageEducationalInfrastructur payload used for creation", required = true)
            @RequestBody VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO);

    @Operation(summary = "Update VillageEducationalInfrastructur (delegates to VillageEducationalInfrastructurFacade.update)", tags = {"VillageEducationalInfrastructur"},
            description = "Updates a resource using VillageEducationalInfrastructurFacade.update with a VillageEducationalInfrastructurDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageEducationalInfrastructurDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageEducationalInfrastructurDTO> update(
            @Parameter(description = "VillageEducationalInfrastructur payload used for update", required = true)
            @RequestBody VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO);

    @Operation(summary = "Get VillageEducationalInfrastructurs page (delegates to VillageEducationalInfrastructurFacade.getAllPage)", tags = {"VillageEducationalInfrastructur"},
            description = "Returns a paginated list using VillageEducationalInfrastructurFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageEducationalInfrastructurDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageEducationalInfrastructur by ID (delegates to VillageEducationalInfrastructurFacade.getById)" , tags = {"VillageEducationalInfrastructur"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageEducationalInfrastructurDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageEducationalInfrastructurDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageEducationalInfrastructur (delegates to VillageEducationalInfrastructurFacade.delete)" , tags = {"VillageEducationalInfrastructur"})
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

    @Operation(summary = "Get VillageEducationalInfrastructur by IDs (delegates to VillageEducationalInfrastructurFacade.getAllByIds)" , tags = {"VillageEducationalInfrastructur"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageEducationalInfrastructurDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
