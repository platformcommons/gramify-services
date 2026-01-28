package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.vo.UseCaseVO;
import com.platformcommons.platform.service.domain.dto.DomainDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.UseCaseDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface UseCaseFacade {

    Long save(UseCaseDTO useCaseDTO );

    UseCaseDTO update(UseCaseDTO useCaseDTO );

    PageDTO<UseCaseDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    UseCaseDTO getById(Long id);

    void mapUseCaseToDomains(Long useCaseId, List<String> domainCodes);

    void mapUseCaseToTags(Long useCaseId, List<Long> tagIds);

    void mapUseCaseToApps(Long useCaseId, List<Long> appIds);

    PageDTO<DomainDTO> getDomainsByUseCaseId(Long useCaseId,Integer page, Integer size);


    List<UseCaseVO> getUseCaseByIds(List<Long> useCaseIds);

    AttachmentDTO createAttachment(Long useCaseId, MultipartFile file) throws IOException;

    List<AttachmentDTO> getAttachments(Long useCaseId);

    PageDTO<TagDTO> getTagsForUseCase(Long useCaseId, Integer page, Integer size, String sortBy, String direction);

    void createUseCaseBatch(List<UseCaseDTO> useCaseDTOs);

    BulkUploadRequestDTO uploadUsecase(MultipartFile file) throws Exception;
}
