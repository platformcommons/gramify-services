package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.UseCase;
import com.platformcommons.platform.service.domain.domain.vo.UseCaseExcelVO;
import com.platformcommons.platform.service.domain.domain.vo.UseCaseVO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface UseCaseService {

    UseCase save(UseCase useCase );

    UseCase saveWithoutInit(UseCase useCase);

    UseCase update(UseCase useCase );

    Page<UseCase> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    UseCase getById(Long id);

    void mapUseCaseToDomains(Long useCaseId, List<String> domainCodes);

    void mapUseCaseToTags(Long useCaseId, List<Long> tagIds);

    void mapUseCaseToApps(Long useCaseId, List<Long> appIds);

    Page <Domain> getDomainsByUseCaseId(Long useCaseId,Integer page, Integer size);


    List<UseCaseVO> getUseCaseByIds(List<Long> useCaseIds);

    Attachment createAttachment(Long useCaseId, MultipartFile file) throws IOException;

    List<AttachmentDTO> getAttachments(Long useCaseId);

    void existsById(Long useCaseId);

    Page<Tag> getTagsForUseCase(Long useCaseId, Integer page, Integer size, Sort sort);

    List<UseCase> createUseCaseBatch(LinkedList<UseCase> useCases);

    List<UseCase> findAllByCode(Set<String> sets);


    UseCase findByCode(String code);

    List<UseCase> findAllById(Set<Long> collect);
}
