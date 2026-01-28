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

import com.platformcommons.platform.service.blockprofile.dto.VillageMineralAndBiodiversityPrDTO;

/**
 * API contract for VillageMineralAndBiodiversityPrController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-mineral-and-biodiversity-prs")
@Tag(name = "VillageMineralAndBiodiversityPr", description = "Operations for VillageMineralAndBiodiversityPr that delegate to VillageMineralAndBiodiversityPrFacade")
public interface VillageMineralAndBiodiversityPrControllerApi {

    @Operation(summary = "Create VillageMineralAndBiodiversityPr (delegates to VillageMineralAndBiodiversityPrFacade.save)", tags = {"VillageMineralAndBiodiversityPr"},
            description = "Creates a new resource using VillageMineralAndBiodiversityPrFacade.save with a VillageMineralAndBiodiversityPrDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageMineralAndBiodiversityPr payload used for creation", required = true)
            @RequestBody VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO);

    @Operation(summary = "Update VillageMineralAndBiodiversityPr (delegates to VillageMineralAndBiodiversityPrFacade.update)", tags = {"VillageMineralAndBiodiversityPr"},
            description = "Updates a resource using VillageMineralAndBiodiversityPrFacade.update with a VillageMineralAndBiodiversityPrDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageMineralAndBiodiversityPrDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageMineralAndBiodiversityPrDTO> update(
            @Parameter(description = "VillageMineralAndBiodiversityPr payload used for update", required = true)
            @RequestBody VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO);

    @Operation(summary = "Get VillageMineralAndBiodiversityPrs page (delegates to VillageMineralAndBiodiversityPrFacade.getAllPage)", tags = {"VillageMineralAndBiodiversityPr"},
            description = "Returns a paginated list using VillageMineralAndBiodiversityPrFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageMineralAndBiodiversityPrDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageMineralAndBiodiversityPr by ID (delegates to VillageMineralAndBiodiversityPrFacade.getById)" , tags = {"VillageMineralAndBiodiversityPr"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageMineralAndBiodiversityPrDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageMineralAndBiodiversityPrDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageMineralAndBiodiversityPr (delegates to VillageMineralAndBiodiversityPrFacade.delete)" , tags = {"VillageMineralAndBiodiversityPr"})
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

    @Operation(summary = "Get VillageMineralAndBiodiversityPr by IDs (delegates to VillageMineralAndBiodiversityPrFacade.getAllByIds)" , tags = {"VillageMineralAndBiodiversityPr"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageMineralAndBiodiversityPrDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
