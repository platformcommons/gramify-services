package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.client.RequestForQuotationAPI;
import com.platformcommons.platform.service.domain.dto.RequestForQuotationDTO;
import com.platformcommons.platform.service.domain.facade.RequestForQuotationFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag( name = "RequestForQuotation" )
public class RequestForQuotationController implements RequestForQuotationAPI {


    @Autowired
    private RequestForQuotationFacade requestForQuotationFacade;


    @Override
    public ResponseEntity<RequestForQuotationDTO> getRequestForQuotation(Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body( requestForQuotationFacade.getRequestForQuotation( id ));
    }

    @Override
    public ResponseEntity<PageDTO<RequestForQuotationDTO>> getRequestForQuotationPage(Integer pageNumber, Integer pagesSize,
                                                                                      String forEntityId, String forEntityName,
                                                                                      String sortBy, String direction)
    {
        return ResponseEntity.status(HttpStatus.OK).body( requestForQuotationFacade.getRequestForQuotationPage( pageNumber, pagesSize, forEntityId, forEntityName, sortBy, direction ) );
    }

    @Override
    public ResponseEntity<Long> postRequestForQuotation(RequestForQuotationDTO body) {
        return ResponseEntity.status( HttpStatus.CREATED )
                             .body( requestForQuotationFacade.save( body ) );
    }

}
