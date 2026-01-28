package com.platformcommons.platform.service.profile.facade.impl;

import com.netflix.discovery.converters.Auto;
import com.platformcommons.platform.service.profile.application.IEIdCardTemplateService;
import com.platformcommons.platform.service.profile.domain.IEIdCardTemplate;
import com.platformcommons.platform.service.profile.dto.IEIdCardTemplateDTO;
import com.platformcommons.platform.service.profile.facade.IEIdCardTemplateFacade;
import com.platformcommons.platform.service.profile.facade.assembler.IEIdCardTemplateDTOAssembler;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.Map;

@Component
@Transactional
public class IEIdCardTemplateFacadeImpl implements IEIdCardTemplateFacade {

    @Autowired
    private IEIdCardTemplateService service;

    @Autowired
    private IEIdCardTemplateDTOAssembler assembler;

    @Autowired
    private VelocityEngine velocityEngine;


    @Override
    public Long save(IEIdCardTemplateDTO ieIdCardTemplateDTO) {
        IEIdCardTemplate ieIdCardTemplate = assembler.fromDTO(ieIdCardTemplateDTO);
        Long savedEntityId = service.save(ieIdCardTemplate);
        return savedEntityId;
    }

    @Override
    public IEIdCardTemplateDTO update(IEIdCardTemplateDTO ieIdCardTemplateDTO) {
        return assembler.toDTO(service.update(assembler.fromDTO(ieIdCardTemplateDTO)));
    }

    @Override
    public void delete(Long id, String reason) {
        service.delete(id,reason);
    }

    @Override
    public IEIdCardTemplateDTO getById(Long id) {
      return  assembler.toDTO(service.getById(id));
    }

    @Override
    public byte[] generateIdCard(Map<String, Object> params) {
        String type = "DEFAULT1";
        String templateData = service.getTemplateByType(type);
        VelocityContext context = new VelocityContext(params);
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "IDCardTemplate", templateData);

        return service.convertHtmlToPdf(writer.toString());
    }


//    @Override
//    public byte[] generatePdfById(Long id) {
//
//        Map<String, Object> model = new HashMap<>();
//        model.put("profilePic", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzCW8ayM9K_iNzX81NSjgpGcl30jDvsTSiIg&s");
//        model.put("empName", "Manish Kumar Choudhary");
//        model.put("officeAddress", "Gulbarga, Karnataka");
//        model.put("idNumber", "10324 01428 03472 817");
//        model.put("homeAddressBold", "Village Naganoor panchayat Naganoor");
//        model.put("homeAddress", "Gulbarga, 100000, Karnataka");
//        model.put("commansfarmImg", "https://dev-attachment-ms.s3.ap-south-1.amazonaws.com/1/id_card/1717063325498-left-left.png");
//        model.put("vrattiImg", "https://dev-attachment-ms.s3.ap-south-1.amazonaws.com/1/id_card/1717063391296-left-right.png");
//        model.put("mobileNo", "9611475027");
//        model.put("gender", "MALE");
//        model.put("address", "Village Naganoor panchayat Naganoor, Yadgir, 585216, Karnataka");
//        model.put("qrCode", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxJi90KPkuiXC6t0nd_3O7chNuLbDaY2ZZyA&s");
//        model.put("rightPic", "https://dev-attachment-ms.s3.ap-south-1.amazonaws.com/1/id_card/1717063268948-right_side.png");
//
//        String templateData = service.getTemplateDataById(id);
//        VelocityContext context = new VelocityContext(model);
//        StringWriter writer = new StringWriter();
//        velocityEngine.evaluate(context, writer, "IDCardTemplate", templateData);
//
//        return service.convertHtmlToPdf(writer.toString());
//
//    }

}
