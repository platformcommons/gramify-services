package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.dto.DomainDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DomainFacade {

    Long save(DomainDTO domainDTO );

    DomainDTO update(DomainDTO domainDTO );

    PageDTO<DomainDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    DomainDTO getById(Long id);

    Long createDomain(DomainDTO domainDTO, String parentDomainCode);

    PageDTO<DomainDTO> getSubDomains(Integer page, Integer size, String sortBy, String direction, String parentDomainCode, Integer depth, String context);

    PageDTO<DomainDTO> getDomains(Integer page, Integer size, String context, Boolean isRoot);

    List<DomainDTO> getByCodes(List<String> domainCodes, String context);

    Map<String,DomainDTO> getDomainByCodes(List<String> domainCodes);

    AttachmentDTO createAttachment(Long domainId, MultipartFile file) throws IOException;

    List<AttachmentDTO> getAttachments(Long domainId);

    PageDTO<TagDTO> getTagsForDomain(String domainCode, Integer page, Integer size);

    void createDomainBatch(List<DomainDTO> domainDTOs);

    void addBatchDomainHierarchyBatch(List<Long> childDomainIds, Long parentDomainId);

    BulkUploadRequestDTO uploadSubDomainDataFromExcelFile(Long parentDomainId, MultipartFile file) throws Exception;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    BulkUploadRequestDTO uploadThemesWithUseCases(Long parentDomainId, MultipartFile file) throws Exception;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    BulkUploadRequestDTO uploadUseCases(Long parentDomainId, MultipartFile file) throws Exception;

    BulkUploadRequestDTO updateUseCaseSequence(Long parentDomainId, MultipartFile file) throws Exception;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    BulkUploadRequestDTO uploadThemes(Long parentDomainId, MultipartFile file) throws Exception;
}
