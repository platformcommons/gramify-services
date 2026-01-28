package com.platformcommons.platform.service.profile.application.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.profile.application.IEIdCardTemplateService;
import com.platformcommons.platform.service.profile.domain.IEIdCardTemplate;
import com.platformcommons.platform.service.profile.domain.repo.IEIdCardTemplateNonMTRepository;
import com.platformcommons.platform.service.profile.domain.repo.IEIdCardTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;


@Service
public class IEIdCardTemplateServiceImpl implements IEIdCardTemplateService {

    @Autowired
    private IEIdCardTemplateRepository repository;

    @Autowired
    private IEIdCardTemplateNonMTRepository ieIdCardTemplateNonMTRepository;


    @Override
    public Long save(IEIdCardTemplate ieIdCardTemplate) {
        return repository.save(ieIdCardTemplate).getId();
    }

    @Override
    public IEIdCardTemplate update(IEIdCardTemplate updatedIeIdCardTemplate) {
        IEIdCardTemplate existingTemplate = repository.findById(updatedIeIdCardTemplate.getId())
                .orElseThrow(() -> new NotFoundException("IEIdCardTemplate with ID " + updatedIeIdCardTemplate.getId() + " not found"));
        existingTemplate.patch(updatedIeIdCardTemplate);
        return repository.save(existingTemplate);
    }

    @Override
    public void delete(Long id, String reason) {
        IEIdCardTemplate ieIdCardTemplate = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Request Ie with  %d  not found",id)));
        ieIdCardTemplate.deactivate(reason);
        repository.save(ieIdCardTemplate);
    }

    @Override
    public IEIdCardTemplate getById(Long id) {
       return repository.getById(id);
    }


//    @Override
//    public byte[] convertHtmlToPdf(String templateData, String baseUrl) {
//        try {
//            org.jsoup.nodes.Document jsoupDocument = Jsoup.parse(templateData);
//            jsoupDocument.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
//            W3CDom w3cDom = new W3CDom();
//            Document document = w3cDom.fromJsoup(jsoupDocument);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.withW3cDocument(document, baseUrl);
//            builder.toStream(outputStream);
//            builder.run();
//
//            return outputStream.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new byte[0];
//        }
//    }

    @Override
    public byte[] convertHtmlToPdf(String templateData) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ConverterProperties converterProperties = new ConverterProperties();

            HtmlConverter.convertToPdf(templateData, outputStream, converterProperties);

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    @Override
    public String getTemplateByType(String aDefault) {
       return ieIdCardTemplateNonMTRepository.findByType(aDefault).get().getTemplateData();
    }

}
