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

import com.platformcommons.platform.service.blockprofile.dto.MajorFestivalDTO;

/**
 * API contract for MajorFestivalController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/major-festivals")
@Tag(name = "MajorFestival", description = "Operations for MajorFestival that delegate to MajorFestivalFacade")
public interface MajorFestivalControllerApi {

    @Operation(summary = "Create MajorFestival (delegates to MajorFestivalFacade.save)", tags = {"MajorFestival"},
            description = "Creates a new resource using MajorFestivalFacade.save with a MajorFestivalDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "MajorFestival payload used for creation", required = true)
            @RequestBody MajorFestivalDTO MajorFestivalDTO);

    @Operation(summary = "Update MajorFestival (delegates to MajorFestivalFacade.update)", tags = {"MajorFestival"},
            description = "Updates a resource using MajorFestivalFacade.update with a MajorFestivalDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = MajorFestivalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MajorFestivalDTO> update(
            @Parameter(description = "MajorFestival payload used for update", required = true)
            @RequestBody MajorFestivalDTO MajorFestivalDTO);

    @Operation(summary = "Get MajorFestivals page (delegates to MajorFestivalFacade.getAllPage)", tags = {"MajorFestival"},
            description = "Returns a paginated list using MajorFestivalFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<MajorFestivalDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get MajorFestival by ID (delegates to MajorFestivalFacade.getById)" , tags = {"MajorFestival"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = MajorFestivalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<MajorFestivalDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete MajorFestival (delegates to MajorFestivalFacade.delete)" , tags = {"MajorFestival"})
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

    @Operation(summary = "Get MajorFestival by IDs (delegates to MajorFestivalFacade.getAllByIds)" , tags = {"MajorFestival"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<MajorFestivalDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
