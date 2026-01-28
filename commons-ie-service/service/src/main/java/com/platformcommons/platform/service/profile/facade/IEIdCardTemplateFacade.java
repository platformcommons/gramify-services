package com.platformcommons.platform.service.profile.facade;


import com.platformcommons.platform.service.profile.dto.IEIdCardTemplateDTO;

import java.util.Map;

public interface IEIdCardTemplateFacade {

    Long save(IEIdCardTemplateDTO ieIdCardTemplateDTO );

    IEIdCardTemplateDTO update(IEIdCardTemplateDTO ieIdCardTemplateDTO );

    void delete(Long id,String reason);

    IEIdCardTemplateDTO getById(Long id);

    byte[] generateIdCard(Map<String, Object> params);
}
