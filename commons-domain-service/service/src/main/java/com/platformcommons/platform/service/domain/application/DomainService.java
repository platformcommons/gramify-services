package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.domain.Domain;

import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.vo.DomainVO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface DomainService {

    Domain save(Domain domain );

    Domain update(Domain domain );

    Page<Domain> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Domain getById(Long id);

    Set<Domain> getDomainsByIds(Set<Long> ids);


    Page<Domain> getSubDomains(Integer page, Integer size, String sortBy, String direction, String parentDomainCode, Integer depth, String context);

    Page<Domain> getDomains(Integer page, Integer size, String context, Boolean isRoot);

    Domain getDomainByCode(String parentDomainCode, String context);

    List<DomainVO> getByCodes(List<String> domainCodes);



    List<Domain> getDomainsByCodes(List<String> domainCodes, String context);

    Attachment createAttachment(Long domainId, MultipartFile file) throws IOException;

    List<AttachmentDTO> getAttachments(Long domainId);

    void existsById(Long domainId);

    Page<Tag> getTagsForDomain(String domainCode, Integer page, Integer size);

    List<Domain> createBatch(LinkedList<Domain> domains);
    List<Domain> getFindAllDomainsByCodes(Set<String> sets);
}
