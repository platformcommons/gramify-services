package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.application.RequestForQuotationService;
import com.platformcommons.platform.service.domain.domain.RequestForQuotation;
import com.platformcommons.platform.service.domain.dto.RequestForQuotationDTO;
import com.platformcommons.platform.service.domain.facade.RequestForQuotationFacade;
import com.platformcommons.platform.service.domain.facade.assembler.RequestForQuotationAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class RequestForQuotationFacadeImpl implements RequestForQuotationFacade {

    @Autowired
    private  RequestForQuotationAssembler requestForQuotationAssembler;

    @Autowired
    private RequestForQuotationService requestForQuotationService;


    @Override
    public RequestForQuotationDTO getRequestForQuotation(Long id) {
        return requestForQuotationAssembler.toDTO( requestForQuotationService.getRequestForQuotation( id ) );
    }

    @Override
    public Long save(RequestForQuotationDTO requestForQuotationDTO) {
        return requestForQuotationService.save( requestForQuotationAssembler.fromDTO( requestForQuotationDTO ) );
    }

    @Override
    public PageDTO<RequestForQuotationDTO> getRequestForQuotationPage(Integer pageNumber, Integer pagesSize, String forEntityId, String forEntityName, String sortBy, String direction) {
        Page<RequestForQuotation> requestForQuotations = requestForQuotationService.getRequestForQuotationPage( pageNumber, pagesSize, forEntityId, forEntityName, sortBy, direction );

        return new PageDTO<>( requestForQuotations.stream()
                                                  .map( requestForQuotationAssembler::toDTO )
                                                  .collect( Collectors.toCollection(LinkedHashSet::new) ),
                              requestForQuotations.hasNext()
                            );
    }

}
