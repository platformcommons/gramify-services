package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.domain.application.RequestForQuotationService;
import com.platformcommons.platform.service.domain.domain.RequestForQuotation;
import com.platformcommons.platform.service.domain.domain.repo.RequestForQuotationRepository;
import com.platformcommons.platform.service.domain.dto.RequestForQuotationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestForQuotationServiceImpl implements RequestForQuotationService {


    @Autowired
    private RequestForQuotationRepository requestForQuotationRepository;

    @Override
    public Long save(RequestForQuotation requestForQuotation) {
        return requestForQuotationRepository.save(requestForQuotation).getId();
    }

    @Override
    public RequestForQuotation getRequestForQuotation(Long id) {

        return requestForQuotationRepository.findById(id)
                                            .orElseThrow( () -> new NotFoundException(
                                                                    String.format("Request for Quotation with id %d not found", id)
                                            ));

    }

    @Override
    public Page<RequestForQuotation> getRequestForQuotationPage(Integer pageNumber, Integer pagesSize, String forEntityId, String forEntityType, String sortBy, String direction) {

        Pageable pageable;
        if( sortBy!=null )
            pageable = PageRequest.of( pageNumber, pagesSize,
                                       JPAUtility.convertToSort( sortBy , direction==null ? "asc" : direction )
            );
        else
            pageable = PageRequest.of( pageNumber, pagesSize );

        return requestForQuotationRepository.getByForEntityIdAndForEntityTypeFilter( forEntityId, forEntityType, pageable );
    }


}
