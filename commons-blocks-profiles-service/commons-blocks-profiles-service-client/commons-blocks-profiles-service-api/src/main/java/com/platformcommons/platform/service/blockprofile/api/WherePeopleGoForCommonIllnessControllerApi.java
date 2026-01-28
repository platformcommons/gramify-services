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

import com.platformcommons.platform.service.blockprofile.dto.WherePeopleGoForCommonIllnessDTO;

/**
 * API contract for WherePeopleGoForCommonIllnessController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/where-people-go-for-common-illnesses")
@Tag(name = "WherePeopleGoForCommonIllness", description = "Operations for WherePeopleGoForCommonIllness that delegate to WherePeopleGoForCommonIllnessFacade")
public interface WherePeopleGoForCommonIllnessControllerApi {

    @Operation(summary = "Create WherePeopleGoForCommonIllness (delegates to WherePeopleGoForCommonIllnessFacade.save)", tags = {"WherePeopleGoForCommonIllness"},
            description = "Creates a new resource using WherePeopleGoForCommonIllnessFacade.save with a WherePeopleGoForCommonIllnessDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "WherePeopleGoForCommonIllness payload used for creation", required = true)
            @RequestBody WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO);

    @Operation(summary = "Update WherePeopleGoForCommonIllness (delegates to WherePeopleGoForCommonIllnessFacade.update)", tags = {"WherePeopleGoForCommonIllness"},
            description = "Updates a resource using WherePeopleGoForCommonIllnessFacade.update with a WherePeopleGoForCommonIllnessDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = WherePeopleGoForCommonIllnessDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WherePeopleGoForCommonIllnessDTO> update(
            @Parameter(description = "WherePeopleGoForCommonIllness payload used for update", required = true)
            @RequestBody WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO);

    @Operation(summary = "Get WherePeopleGoForCommonIllnesss page (delegates to WherePeopleGoForCommonIllnessFacade.getAllPage)", tags = {"WherePeopleGoForCommonIllness"},
            description = "Returns a paginated list using WherePeopleGoForCommonIllnessFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<WherePeopleGoForCommonIllnessDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get WherePeopleGoForCommonIllness by ID (delegates to WherePeopleGoForCommonIllnessFacade.getById)" , tags = {"WherePeopleGoForCommonIllness"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = WherePeopleGoForCommonIllnessDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<WherePeopleGoForCommonIllnessDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete WherePeopleGoForCommonIllness (delegates to WherePeopleGoForCommonIllnessFacade.delete)" , tags = {"WherePeopleGoForCommonIllness"})
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

    @Operation(summary = "Get WherePeopleGoForCommonIllness by IDs (delegates to WherePeopleGoForCommonIllnessFacade.getAllByIds)" , tags = {"WherePeopleGoForCommonIllness"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<WherePeopleGoForCommonIllnessDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
