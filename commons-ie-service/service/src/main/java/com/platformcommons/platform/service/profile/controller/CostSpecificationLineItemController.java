package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.client.CostSpecificationLineItemAPI;
import com.platformcommons.platform.service.profile.dto.CostSpecificationLineItemDTO;
import com.platformcommons.platform.service.profile.facade.CostSpecificationLineItemFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "CostSpecificationLineItem")
public class CostSpecificationLineItemController implements CostSpecificationLineItemAPI {

    @Autowired
    private CostSpecificationLineItemFacade costSpecificationLineItemFacade;

    @Override
    public ResponseEntity<Void> deleteCostSpecificationLineItem(Long id, String reason) {
        costSpecificationLineItemFacade.delete(id, reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<CostSpecificationLineItemDTO> getCostSpecificationLineItem(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(costSpecificationLineItemFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<CostSpecificationLineItemDTO>> getCostSpecificationLineItemPage(Integer page, Integer size) {
        PageDTO<CostSpecificationLineItemDTO> result = costSpecificationLineItemFacade.getAllPage(page, size);
        return ResponseEntity.status(result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<CostSpecificationLineItemDTO> putCostSpecificationLineItem(CostSpecificationLineItemDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(costSpecificationLineItemFacade.update(body));
    }

}
