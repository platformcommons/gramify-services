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

import com.platformcommons.platform.service.blockprofile.dto.MainReligiousPlaceDTO;

/**
 * API contract for MainReligiousPlaceController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/main-religious-places")
@Tag(name = "MainReligiousPlace", description = "Operations for MainReligiousPlace that delegate to MainReligiousPlaceFacade")
public interface MainReligiousPlaceControllerApi {

    @Operation(summary = "Create MainReligiousPlace (delegates to MainReligiousPlaceFacade.save)", tags = {"MainReligiousPlace"},
            description = "Creates a new resource using MainReligiousPlaceFacade.save with a MainReligiousPlaceDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "MainReligiousPlace payload used for creation", required = true)
            @RequestBody MainReligiousPlaceDTO MainReligiousPlaceDTO);

    @Operation(summary = "Update MainReligiousPlace (delegates to MainReligiousPlaceFacade.update)", tags = {"MainReligiousPlace"},
            description = "Updates a resource using MainReligiousPlaceFacade.update with a MainReligiousPlaceDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = MainReligiousPlaceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MainReligiousPlaceDTO> update(
            @Parameter(description = "MainReligiousPlace payload used for update", required = true)
            @RequestBody MainReligiousPlaceDTO MainReligiousPlaceDTO);

    @Operation(summary = "Get MainReligiousPlaces page (delegates to MainReligiousPlaceFacade.getAllPage)", tags = {"MainReligiousPlace"},
            description = "Returns a paginated list using MainReligiousPlaceFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<MainReligiousPlaceDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get MainReligiousPlace by ID (delegates to MainReligiousPlaceFacade.getById)" , tags = {"MainReligiousPlace"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = MainReligiousPlaceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<MainReligiousPlaceDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete MainReligiousPlace (delegates to MainReligiousPlaceFacade.delete)" , tags = {"MainReligiousPlace"})
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

    @Operation(summary = "Get MainReligiousPlace by IDs (delegates to MainReligiousPlaceFacade.getAllByIds)" , tags = {"MainReligiousPlace"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<MainReligiousPlaceDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
