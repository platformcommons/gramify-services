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

import com.platformcommons.platform.service.blockprofile.dto.VillageSkillShortageTypeDTO;

/**
 * API contract for VillageSkillShortageTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-skill-shortage-types")
@Tag(name = "VillageSkillShortageType", description = "Operations for VillageSkillShortageType that delegate to VillageSkillShortageTypeFacade")
public interface VillageSkillShortageTypeControllerApi {

    @Operation(summary = "Create VillageSkillShortageType (delegates to VillageSkillShortageTypeFacade.save)", tags = {"VillageSkillShortageType"},
            description = "Creates a new resource using VillageSkillShortageTypeFacade.save with a VillageSkillShortageTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageSkillShortageType payload used for creation", required = true)
            @RequestBody VillageSkillShortageTypeDTO VillageSkillShortageTypeDTO);

    @Operation(summary = "Update VillageSkillShortageType (delegates to VillageSkillShortageTypeFacade.update)", tags = {"VillageSkillShortageType"},
            description = "Updates a resource using VillageSkillShortageTypeFacade.update with a VillageSkillShortageTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageSkillShortageTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageSkillShortageTypeDTO> update(
            @Parameter(description = "VillageSkillShortageType payload used for update", required = true)
            @RequestBody VillageSkillShortageTypeDTO VillageSkillShortageTypeDTO);

    @Operation(summary = "Get VillageSkillShortageTypes page (delegates to VillageSkillShortageTypeFacade.getAllPage)", tags = {"VillageSkillShortageType"},
            description = "Returns a paginated list using VillageSkillShortageTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageSkillShortageTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageSkillShortageType by ID (delegates to VillageSkillShortageTypeFacade.getById)" , tags = {"VillageSkillShortageType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageSkillShortageTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageSkillShortageTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageSkillShortageType (delegates to VillageSkillShortageTypeFacade.delete)" , tags = {"VillageSkillShortageType"})
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

    @Operation(summary = "Get VillageSkillShortageType by IDs (delegates to VillageSkillShortageTypeFacade.getAllByIds)" , tags = {"VillageSkillShortageType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageSkillShortageTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
