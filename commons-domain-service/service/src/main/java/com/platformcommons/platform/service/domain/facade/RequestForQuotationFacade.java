package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.dto.RequestForQuotationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

public interface RequestForQuotationFacade {

    RequestForQuotationDTO getRequestForQuotation( Long id );
    Long save( RequestForQuotationDTO requestForQuotationDTO );

    PageDTO<RequestForQuotationDTO> getRequestForQuotationPage(Integer pageNumber, Integer pagesSize, String forEntityId, String forEntityName, String sortBy, String direction);
}
