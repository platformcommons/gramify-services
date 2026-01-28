package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.person.dto.IeInfoVO;
import com.platformcommons.platform.service.profile.domain.Ie;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IeService {

    Ie save(Ie ie );

    Ie update(Ie ie );

    Ie patchUpdate(Ie ie );

    Page<Ie> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Ie getById(Long id);
    
    IeInfoVO getInfoOfIe(Long id);

    Ie updateQrCodeAttachmentPath(Long ieId, String qrCodeAttachmentPath);

    Ie updateIeIdCardAttachmentPath(Long ieId, String ieIdCardPath);

    Ie updateIeIcon(Long ieId, String ieIcon);

    Optional<Ie> findByUuid(String uuid);

    boolean existByUuids(Set<String> set);

    List<Ie> saveAll(List<Ie> ies);

    Ie getIeByMobile(String mobile);

    void patchIEDataOfExcelUploadAPI(Integer page,Integer endPage, Integer size);

    byte[] downloadCombinedPdfForMultipleIds(Set<Long> ids);

    Page<Ie> getIeByWorkforce(Set<Long> worknodeIds, Set<Long> workforceIds, Set<String> taggedToServiceAreaCodes, String taggedToServiceAreaType, Integer page, Integer size, String sortBy, String direction);
}
