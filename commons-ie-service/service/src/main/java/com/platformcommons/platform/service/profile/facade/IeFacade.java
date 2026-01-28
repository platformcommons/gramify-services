package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.person.dto.IeInfoVO;
import com.platformcommons.platform.service.profile.domain.Ie;
import com.platformcommons.platform.service.profile.dto.IeDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IeFacade {

    IeDTO save(IeDTO ieDTO);

    IeDTO update(IeDTO ieDTO);

    IeDTO patchUpdate(IeDTO ieDTO);

    PageDTO<IeDTO> getAllPage(Integer page, Integer size);

    void delete(Long id, String reason);

    IeDTO getById(Long id);

    IeInfoVO getInfoOfIe(Long id);

    AttachmentDTO uploadQrCodeForIe(Long ieId, MultipartFile file) throws IOException;

    byte[] generateIdCard(Long id) throws IOException;

    AttachmentDTO uploadIeIdCardPathForIe(Long ieId, byte[] bytes) throws IOException;

    void updateIeIcon(Long ieId, String ieIcon);

    List<IeDTO> saveAll(List<IeDTO> ieList);

    List<IeDTO> saveAllWithoutQR(List<IeDTO> ieList);

    void patchIEDataOfExcelUploadAPI(Integer startPage, Integer endPage, Integer size);

    List<byte[]> generateIdCards(Set<Long> ids) throws IOException;

    byte[] downloadCombinedPdfForMultipleIds(Set<Long> ids);

    PageDTO<IeDTO> getIeByWorkforce(@Valid Set<Long> worknodeIds, @Valid Set<Long> workforceIds, Set<String> taggedToServiceAreaCodes, String taggedToServiceAreaType, @NotNull @Valid Integer page, @NotNull @Valid Integer size, @Valid String sortBy, @Valid String direction);
}
