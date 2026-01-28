package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.base.BaseDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.market.dto.TraderDTO;
import com.platformcommons.platform.service.person.dto.IeInfoVO;
import com.platformcommons.platform.service.profile.application.IeService;
import com.platformcommons.platform.service.profile.application.utility.BASE64DecodedMultipartFile;
import com.platformcommons.platform.service.profile.application.utility.QRCodeUtil;
import com.platformcommons.platform.service.profile.application.utility.impl.IEIDCardUtility;
import com.platformcommons.platform.service.profile.domain.Ie;
import com.platformcommons.platform.service.profile.dto.IeDTO;
import com.platformcommons.platform.service.profile.facade.IEIdCardTemplateFacade;
import com.platformcommons.platform.service.profile.facade.IeFacade;
import com.platformcommons.platform.service.profile.facade.assembler.AttachmentAssembler;
import com.platformcommons.platform.service.profile.facade.assembler.IeDTOAssembler;
import com.platformcommons.platform.service.profile.facade.client.MarketClient;
import com.platformcommons.platform.service.profile.messaging.producer.IeDTOEventProducer;
import com.platformcommons.platform.service.worknode.client.WorknodeClient;
import com.platformcommons.platform.service.worknode.dto.WorknodeAddressDTO;
import com.platformcommons.platform.service.worknode.dto.WorknodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class IeFacadeImpl implements IeFacade {

    @Autowired
    private IeService service;

    @Autowired
    private IeDTOAssembler assembler;

    @Autowired
    private AttachmentAssembler attachmentAssembler;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private QRCodeUtil qrCodeUtil;

    @Autowired
    private IEIdCardTemplateFacade ieIdCardTemplateFacade;

    @Autowired
    private IEIDCardUtility ieidCardUtility;

    @Autowired
    private WorknodeClient worknodeClient;

    @Autowired
    private MarketClient marketClient;

    @Autowired
    private IeDTOEventProducer ieDTOEventProducer;

    public static final String FILE_FORMAT_PDF_MIME = "application/pdf";
    public static final String FILE_FORMAT_PDF_EXTENTION = ".pdf";

    @Value("${commons.service.generate-qr:true}")
    private Boolean generateQR;

    public static final String ENTITY_TYPE_IE = "ENTITY_TYPE.IE";
    public static final String QR_CODE_ATTACHMENT_KIND = "ATTACHMENT_KIND.QR_CODE";
    public static final String ID_CARD_ATTACHMENT_KIND = "ATTACHMENT_KIND.ID_CARD";

    @Override
    public IeDTO save(IeDTO ieDTO ){
        Optional<Ie> ie = service.findByUuid(ieDTO.getUuid());
        if(ie.isPresent()){
            throw new DuplicateResourceException("Duplicate UUID found");
        }
        Ie savedIE = service.save(assembler.fromDTO(ieDTO));
        if(generateQR!=null && generateQR.equals(Boolean.TRUE)) {
            String qrCodePath = qrCodeUtil.getQRCodeLink(savedIE.getId().toString(), savedIE.getId(), ENTITY_TYPE_IE,
                    QR_CODE_ATTACHMENT_KIND, savedIE.getId().toString(), Boolean.TRUE);
            this.updateQrCodeAttachmentPath(savedIE.getId(), qrCodePath);
        }
        IeDTO savedIeDTO = assembler.toDTO(savedIE);
        ieDTOEventProducer.createdIeDto(savedIeDTO);
        return savedIeDTO;
    }

    @Override
    public IeDTO update(IeDTO ieDTO ){
        IeDTO updatedIeDTO = assembler.toDTO(service.update(assembler.fromDTO(ieDTO)));
        ieDTOEventProducer.updatedIeDto(updatedIeDTO);
        return updatedIeDTO;
    }

    @Override
    public IeDTO patchUpdate(IeDTO ieDTO) {
        IeDTO updatedIeDTO = assembler.toDTO(service.patchUpdate(assembler.fromDTO(ieDTO)));
        ieDTOEventProducer.updatedIeDto(updatedIeDTO);
        return updatedIeDTO;
    }

    @Override
    public PageDTO<IeDTO> getAllPage(Integer page, Integer size){
        Page<Ie> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public IeDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public IeInfoVO getInfoOfIe(Long id) {
        return service.getInfoOfIe(id);
    }

    @Override
    public AttachmentDTO uploadQrCodeForIe(Long ieId, MultipartFile file) throws IOException {
        AttachmentDTO attachmentDTO= attachmentAssembler.toDto(attachmentService
                .uploadAttachment(file, attachmentBuilder(ieId,ENTITY_TYPE_IE ,
                        QR_CODE_ATTACHMENT_KIND, ieId.toString(),
                        null, null)));
        String qrCodePath= attachmentDTO.getCompleteURL();
        updateQrCodeAttachmentPath(ieId,qrCodePath);
        return  attachmentDTO;
    }

    @Override
    public byte[] generateIdCard(Long id) throws IOException {
        Ie ie = service.getById(id);

        if(ie.getQrCodeAttachmentPath() == null){
            return null;
        }

        String profilePic = ie.getIconPic() != null && !ie.getIconPic().isEmpty() ? ie.getIconPic() : IEIDCardUtility.DEFAULT_PROFILE_PIC;

        ResponseEntity<WorknodeDTO> workNodeDTO = worknodeClient.getWorknode(ie.getTaggedToWorknodeId());
        Set<WorknodeAddressDTO> worknodeAddressList = Objects.requireNonNull(workNodeDTO.getBody()).getWorknodeAddressList();

        ResponseEntity<TraderDTO> traderDTO = marketClient.getCurrentTenantTrader(PlatformSecurityUtil.getToken());
        String bannerImage = Objects.requireNonNull(traderDTO.getBody()).getBannerImage();

        Map<String, Object> params = new LinkedHashMap<>();
        params.put(IEIDCardUtility.PROFILE_PIC, profilePic);
        params.put(IEIDCardUtility.NAME, ieidCardUtility.getIeName(ie.getPerson().getPersonProfile()));
        params.put(IEIDCardUtility.ID_NUMBER,ie.getId());
        params.put(IEIDCardUtility.OFFICE_ADDRESS, ieidCardUtility.getOfficeAddress(worknodeAddressList));
        params.put(IEIDCardUtility.FARMER_SHORT_ADDRESS, ieidCardUtility.getFarmerShortAddress(ie.getPerson().getAddressList()));
        params.put(IEIDCardUtility.FARMER_FULL_ADDRESS, ieidCardUtility.getFarmerFullAddress(ie.getPerson().getAddressList()));
        params.put(IEIDCardUtility.MOBILE_NUMBER,ieidCardUtility.getMobileNumber(ie.getPerson().getContactList()));
        params.put(IEIDCardUtility.GENDER, ieidCardUtility.getGender(ie.getPerson().getPersonProfile()));
        params.put(IEIDCardUtility.QR_CODE, ie.getQrCodeAttachmentPath());
        params.put(IEIDCardUtility.BANNER_IMAGE, bannerImage);
//        params.put(IEIDCardUtility.HOME_ADDRESS, ieidCardUtility.getHomeAddress(ie.getPerson().getAddressList()));

        byte[] bytes = ieIdCardTemplateFacade.generateIdCard(params);
        uploadIeIdCardPathForIe(id, bytes);
        return bytes;
    }

    @Override
    public AttachmentDTO uploadIeIdCardPathForIe(Long ieId, byte[] bytes) throws IOException {
        MultipartFile multipartFile = new BASE64DecodedMultipartFile(bytes, ieId + FILE_FORMAT_PDF_EXTENTION,
                FILE_FORMAT_PDF_MIME);
        AttachmentDTO attachmentDTO = attachmentAssembler.toDto(attachmentService
                .uploadAttachment(multipartFile, attachmentBuilder(ieId, ENTITY_TYPE_IE,
                        ID_CARD_ATTACHMENT_KIND,ieId.toString(),
                        null, null)));
        String ieIdCardPath = attachmentDTO.getCompleteURL();
        updateIeIdCardAttachmentPath(ieId, ieIdCardPath);
        return attachmentDTO;
    }

    @Override
    public void updateIeIcon(Long ieId, String ieIcon) {
        Ie ie = service.updateIeIcon(ieId,ieIcon);
    }

    public void updateQrCodeAttachmentPath(Long ieId, String qrCodePath) {
        Ie ie = service.updateQrCodeAttachmentPath(ieId,qrCodePath);
    }

    private void updateIeIdCardAttachmentPath(Long ieId, String ieIdCardPath) {
        Ie ie = service.updateIeIdCardAttachmentPath(ieId,ieIdCardPath);
    }

    private Attachment attachmentBuilder(Long entityId, String entityType, String attachmentKind,
                                         String attachmentKindIdentifier, String attachmentKindMeta, Long sequence) {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType);
        attachment.setAttachmentKind(attachmentKind);
        attachment.setAttachmentKindIdentifier(attachmentKindIdentifier);
        attachment.setSequence(sequence);
        attachment.setAttachmentKindMeta(attachmentKindMeta);
        attachment.setPublic(Boolean.TRUE);
        return attachment;
    }

    @Override
    public List<IeDTO> saveAll(List<IeDTO> ieList) {
        Set<String> set = ieList.stream().map(BaseDTO::getUuid).filter(Objects::nonNull).collect(Collectors.toSet());
        if(!set.isEmpty()){
            if(service.existByUuids(set)) throw new DuplicateResourceException("Duplicate UUID found");
        }

        List<Ie> savedIEs = service.saveAll(assembler.fromDTOs(ieList));
        for (Ie savedIE : savedIEs) {
            if(generateQR!=null && generateQR.equals(Boolean.TRUE)) {
                String qrCodePath = qrCodeUtil.getQRCodeLink(savedIE.getId().toString(), savedIE.getId(), ENTITY_TYPE_IE,QR_CODE_ATTACHMENT_KIND, savedIE.getId().toString(), Boolean.TRUE);
                this.updateQrCodeAttachmentPath(savedIE.getId(), qrCodePath);
            }
        }
        List<IeDTO> savedIeDTOList = assembler.toDTOs(savedIEs);
        savedIeDTOList.forEach(ieDTO -> ieDTOEventProducer.createdIeDto(ieDTO));
        return assembler.toDTOs(savedIEs);
    }


    @Override
    public List<IeDTO> saveAllWithoutQR(List<IeDTO> ieList) {
        Set<String> set = ieList.stream().map(BaseDTO::getUuid).filter(Objects::nonNull).collect(Collectors.toSet());
        if(!set.isEmpty()){
            if(service.existByUuids(set)) throw new DuplicateResourceException("Duplicate UUID found");
        }
        List<Ie> savedIEs = service.saveAll(assembler.fromDTOs(ieList));
        return assembler.toDTOs(savedIEs);
    }

    @Override
    public void patchIEDataOfExcelUploadAPI(Integer startPage,Integer endPage, Integer size) {
        service.patchIEDataOfExcelUploadAPI(startPage,endPage,size);
    }

    @Override
    public List<byte[]> generateIdCards(Set<Long> ids) throws IOException {
        List<byte[]> pdfList = new ArrayList<>();

        for (Long id : ids) {
            byte[] pdfBytes = generateIdCard(id);
            if (pdfBytes != null) {
                pdfList.add(pdfBytes);
            } else {
                throw new RuntimeException("ID Card could not be generated for Ie id : " + id);
            }
        }
        return pdfList;
    }


    @Override
    public byte[] downloadCombinedPdfForMultipleIds(Set<Long> ids) {
        return service.downloadCombinedPdfForMultipleIds(ids);
    }

    @Override
    public PageDTO<IeDTO> getIeByWorkforce(Set<Long> worknodeIds, Set<Long> workforceIds, Set<String> taggedToServiceAreaCodes, String taggedToServiceAreaType, Integer page, Integer size, String sortBy, String direction) {
        Page<Ie> iePage = service.getIeByWorkforce(worknodeIds,workforceIds,taggedToServiceAreaCodes, taggedToServiceAreaType,page,size,sortBy,direction);
        return new PageDTO<>(iePage.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), iePage.hasNext(),iePage.getTotalElements());
    }

}
