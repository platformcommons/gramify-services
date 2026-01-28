package com.platformcommons.platform.service.profile.application;


import com.platformcommons.platform.service.profile.domain.IEIdCardTemplate;


public interface IEIdCardTemplateService {

    Long save(IEIdCardTemplate ieIdCardTemplate);

    IEIdCardTemplate update(IEIdCardTemplate ieIdCardTemplate);

    void delete(Long id,String reason);

    IEIdCardTemplate getById(Long id);

    byte[] convertHtmlToPdf(String templateData);

    String getTemplateByType(String aDefault);
}
