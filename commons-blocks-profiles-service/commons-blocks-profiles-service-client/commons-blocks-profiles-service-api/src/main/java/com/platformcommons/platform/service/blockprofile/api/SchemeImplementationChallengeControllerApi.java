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

import com.platformcommons.platform.service.blockprofile.dto.SchemeImplementationChallengeDTO;

/**
 * API contract for SchemeImplementationChallengeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/scheme-implementation-challenges")
@Tag(name = "SchemeImplementationChallenge", description = "Operations for SchemeImplementationChallenge that delegate to SchemeImplementationChallengeFacade")
public interface SchemeImplementationChallengeControllerApi {

    @Operation(summary = "Create SchemeImplementationChallenge (delegates to SchemeImplementationChallengeFacade.save)", tags = {"SchemeImplementationChallenge"},
            description = "Creates a new resource using SchemeImplementationChallengeFacade.save with a SchemeImplementationChallengeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "SchemeImplementationChallenge payload used for creation", required = true)
            @RequestBody SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO);

    @Operation(summary = "Update SchemeImplementationChallenge (delegates to SchemeImplementationChallengeFacade.update)", tags = {"SchemeImplementationChallenge"},
            description = "Updates a resource using SchemeImplementationChallengeFacade.update with a SchemeImplementationChallengeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = SchemeImplementationChallengeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SchemeImplementationChallengeDTO> update(
            @Parameter(description = "SchemeImplementationChallenge payload used for update", required = true)
            @RequestBody SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO);

    @Operation(summary = "Get SchemeImplementationChallenges page (delegates to SchemeImplementationChallengeFacade.getAllPage)", tags = {"SchemeImplementationChallenge"},
            description = "Returns a paginated list using SchemeImplementationChallengeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<SchemeImplementationChallengeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get SchemeImplementationChallenge by ID (delegates to SchemeImplementationChallengeFacade.getById)" , tags = {"SchemeImplementationChallenge"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = SchemeImplementationChallengeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<SchemeImplementationChallengeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete SchemeImplementationChallenge (delegates to SchemeImplementationChallengeFacade.delete)" , tags = {"SchemeImplementationChallenge"})
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

    @Operation(summary = "Get SchemeImplementationChallenge by IDs (delegates to SchemeImplementationChallengeFacade.getAllByIds)" , tags = {"SchemeImplementationChallenge"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<SchemeImplementationChallengeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
