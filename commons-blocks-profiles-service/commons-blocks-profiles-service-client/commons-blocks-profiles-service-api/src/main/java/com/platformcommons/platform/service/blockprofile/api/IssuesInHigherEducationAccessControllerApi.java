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

import com.platformcommons.platform.service.blockprofile.dto.IssuesInHigherEducationAccessDTO;

/**
 * API contract for IssuesInHigherEducationAccessController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/issues-in-higher-education-accesses")
@Tag(name = "IssuesInHigherEducationAccess", description = "Operations for IssuesInHigherEducationAccess that delegate to IssuesInHigherEducationAccessFacade")
public interface IssuesInHigherEducationAccessControllerApi {

    @Operation(summary = "Create IssuesInHigherEducationAccess (delegates to IssuesInHigherEducationAccessFacade.save)", tags = {"IssuesInHigherEducationAccess"},
            description = "Creates a new resource using IssuesInHigherEducationAccessFacade.save with a IssuesInHigherEducationAccessDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "IssuesInHigherEducationAccess payload used for creation", required = true)
            @RequestBody IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO);

    @Operation(summary = "Update IssuesInHigherEducationAccess (delegates to IssuesInHigherEducationAccessFacade.update)", tags = {"IssuesInHigherEducationAccess"},
            description = "Updates a resource using IssuesInHigherEducationAccessFacade.update with a IssuesInHigherEducationAccessDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = IssuesInHigherEducationAccessDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IssuesInHigherEducationAccessDTO> update(
            @Parameter(description = "IssuesInHigherEducationAccess payload used for update", required = true)
            @RequestBody IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO);

    @Operation(summary = "Get IssuesInHigherEducationAccesss page (delegates to IssuesInHigherEducationAccessFacade.getAllPage)", tags = {"IssuesInHigherEducationAccess"},
            description = "Returns a paginated list using IssuesInHigherEducationAccessFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<IssuesInHigherEducationAccessDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get IssuesInHigherEducationAccess by ID (delegates to IssuesInHigherEducationAccessFacade.getById)" , tags = {"IssuesInHigherEducationAccess"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = IssuesInHigherEducationAccessDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<IssuesInHigherEducationAccessDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete IssuesInHigherEducationAccess (delegates to IssuesInHigherEducationAccessFacade.delete)" , tags = {"IssuesInHigherEducationAccess"})
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

    @Operation(summary = "Get IssuesInHigherEducationAccess by IDs (delegates to IssuesInHigherEducationAccessFacade.getAllByIds)" , tags = {"IssuesInHigherEducationAccess"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<IssuesInHigherEducationAccessDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
