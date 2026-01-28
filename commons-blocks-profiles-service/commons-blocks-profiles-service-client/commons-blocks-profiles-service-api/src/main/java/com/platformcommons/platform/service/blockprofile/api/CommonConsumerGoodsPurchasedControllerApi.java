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

import com.platformcommons.platform.service.blockprofile.dto.CommonConsumerGoodsPurchasedDTO;

/**
 * API contract for CommonConsumerGoodsPurchasedController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/common-consumer-goods-purchaseds")
@Tag(name = "CommonConsumerGoodsPurchased", description = "Operations for CommonConsumerGoodsPurchased that delegate to CommonConsumerGoodsPurchasedFacade")
public interface CommonConsumerGoodsPurchasedControllerApi {

    @Operation(summary = "Create CommonConsumerGoodsPurchased (delegates to CommonConsumerGoodsPurchasedFacade.save)", tags = {"CommonConsumerGoodsPurchased"},
            description = "Creates a new resource using CommonConsumerGoodsPurchasedFacade.save with a CommonConsumerGoodsPurchasedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "CommonConsumerGoodsPurchased payload used for creation", required = true)
            @RequestBody CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO);

    @Operation(summary = "Update CommonConsumerGoodsPurchased (delegates to CommonConsumerGoodsPurchasedFacade.update)", tags = {"CommonConsumerGoodsPurchased"},
            description = "Updates a resource using CommonConsumerGoodsPurchasedFacade.update with a CommonConsumerGoodsPurchasedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonConsumerGoodsPurchasedDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommonConsumerGoodsPurchasedDTO> update(
            @Parameter(description = "CommonConsumerGoodsPurchased payload used for update", required = true)
            @RequestBody CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO);

    @Operation(summary = "Get CommonConsumerGoodsPurchaseds page (delegates to CommonConsumerGoodsPurchasedFacade.getAllPage)", tags = {"CommonConsumerGoodsPurchased"},
            description = "Returns a paginated list using CommonConsumerGoodsPurchasedFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<CommonConsumerGoodsPurchasedDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get CommonConsumerGoodsPurchased by ID (delegates to CommonConsumerGoodsPurchasedFacade.getById)" , tags = {"CommonConsumerGoodsPurchased"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = CommonConsumerGoodsPurchasedDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CommonConsumerGoodsPurchasedDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete CommonConsumerGoodsPurchased (delegates to CommonConsumerGoodsPurchasedFacade.delete)" , tags = {"CommonConsumerGoodsPurchased"})
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

    @Operation(summary = "Get CommonConsumerGoodsPurchased by IDs (delegates to CommonConsumerGoodsPurchasedFacade.getAllByIds)" , tags = {"CommonConsumerGoodsPurchased"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<CommonConsumerGoodsPurchasedDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
