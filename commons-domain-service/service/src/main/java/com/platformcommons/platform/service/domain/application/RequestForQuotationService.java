package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.RequestForQuotation;
import com.platformcommons.platform.service.domain.dto.RequestForQuotationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.data.domain.Page;

public interface RequestForQuotationService {

    Long save( RequestForQuotation requestForQuotation );

    RequestForQuotation getRequestForQuotation(Long id);

    Page<RequestForQuotation> getRequestForQuotationPage(Integer pageNumber, Integer pagesSize, String forEntityId, String forEntityName, String sortBy, String direction);
}
