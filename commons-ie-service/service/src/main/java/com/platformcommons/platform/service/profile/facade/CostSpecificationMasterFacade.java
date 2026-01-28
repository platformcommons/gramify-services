package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.dto.CostSpecificationMasterDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CostSpecificationMasterFacade {
    void delete(Long id, String reason);

    CostSpecificationMasterDTO getById(Long id);

    PageDTO<CostSpecificationMasterDTO> getAllPage(Integer page, Integer size);

    Long save(CostSpecificationMasterDTO body);

    CostSpecificationMasterDTO update(CostSpecificationMasterDTO body);

    PageDTO<CostSpecificationMasterDTO> getCostSpecificationMasterByContext(String context,String contextId, Integer page, Integer size);

    void createBulkCostSpecificationMaster(MultipartFile file) throws Exception;
}
