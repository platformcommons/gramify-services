package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.person.dto.IeInfoVO;
import com.platformcommons.platform.service.profile.application.IeService;
import com.platformcommons.platform.service.profile.application.PersonService;
import com.platformcommons.platform.service.profile.application.utility.MultipleIeCardUtil;
import com.platformcommons.platform.service.profile.domain.DeliveryMode;
import com.platformcommons.platform.service.profile.domain.Ie;
import com.platformcommons.platform.service.profile.domain.Person;
import com.platformcommons.platform.service.profile.domain.PersonFamily;
import com.platformcommons.platform.service.profile.domain.repo.IeRepository;
import com.platformcommons.platform.service.profile.facade.assembler.DeliveryModeDTOAssembler;
import com.platformcommons.platform.service.profile.facade.assembler.IeDeliveryModeDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class IeServiceImpl implements IeService {

    @Autowired
    private IeRepository repository;

    @Autowired
    private DeliveryModeDTOAssembler deliveryModeDTOAssembler;

    @Autowired
    private IeDeliveryModeDTOAssembler ieDeliveryModeDTOAssembler;

    @Autowired
    private PersonService personService;

    @Autowired
    private MultipleIeCardUtil multipleIeCardUtil;

    @Override
    public Ie save(Ie ie ){
        return repository.save(ie);
    }

    @Override
    public Optional<Ie> findByUuid(String uuid) {
        return repository.findByUuid(uuid);
    }

    @Override
    public Ie update(Ie ie) {
       Ie fetchedIe = repository.findById(ie.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Ie with  %d  not found",ie.getId())));

       fetchedIe.update(ie);
       return repository.save(fetchedIe);
    }

    @Override
    public Ie patchUpdate(Ie ie) {
        Ie fetchedIe = repository.findById(ie.getId())
                .orElseThrow(()-> new NotFoundException(String.format("Request Ie with  %d  not found",ie.getId())));

        fetchedIe.patch(ie);
        return repository.save(fetchedIe);
    }
    @Override
    public boolean existByUuids(Set<String> set) {
        return repository.existsByUuidIn(set);
    }

    @Override
    public List<Ie> saveAll(List<Ie> ies) {
        return repository.saveAll(ies);
    }
    @Override
    public Ie getIeByMobile(String mobile) {
        return repository.findByMobile(mobile)
                .orElseThrow(()-> new NotFoundException(String.format("Request Ie with mobile %s not found",mobile)));
    }


    @Override
    public Page<Ie> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Ie fetchedIe = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Ie with  %d  not found",id)));
        fetchedIe.deactivate(reason);
        repository.save(fetchedIe);
    }

    @Override
    public Ie getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Ie with id %d not found",id)));
    }

    @Override
    public IeInfoVO getInfoOfIe(Long id) {
        Optional<IeInfoVO> optIeInfoVO = repository.findInfoOfIeById(id);
        if(optIeInfoVO.isPresent()){
            Set<DeliveryMode> deliveryModes = repository.findDeliveryModesByIeId(id);
            optIeInfoVO.get().setDeliveryModeList(ieDeliveryModeDTOAssembler.toDTOs(deliveryModes));
        }
        else {
            throw  new NotFoundException(String.format("Request Ie with id %d not found",id));
        }
        return optIeInfoVO.get();
    }


    @Override
    public Ie updateQrCodeAttachmentPath(Long ieId, String qrCodeAttachmentPath) {
        Ie fetchedIe = this.getById(ieId);
        fetchedIe.setQrCodeAttachmentPath(qrCodeAttachmentPath);
        return repository.save(fetchedIe);
    }

    @Override
    public Ie updateIeIdCardAttachmentPath(Long ieId, String ieIdCardPath) {
        Ie fetchedIe = this.getById(ieId);
        fetchedIe.setIdCardAttachmentPath(ieIdCardPath);
        return repository.save(fetchedIe);
    }

    @Override
    public Ie updateIeIcon(Long ieId, String ieIcon) {
        Ie ie = this.getById(ieId);
        ie.setIconPic(ieIcon);
        return repository.save(ie);
    }


    @Override
    public void patchIEDataOfExcelUploadAPI(Integer startPage,Integer endPage, Integer size) {
        startPage =  startPage!=null ? startPage: 0;
        boolean hasNext;
        size = size!=null && size!=0 ? size : 100;
        do {
            Page<Person> personPage = personService.getByPage(startPage, size);
            List<Person> personList = personPage.getContent();
            for (Person person : personList) {
                final String fatherName = person.getPersonProfile().getMiddleName();
                final String spouseName = person.getPersonProfile().getSpouseName();
                Set<PersonFamily> personFamilies = person.getPersonFamilyList() != null ? person.getPersonFamilyList() : new HashSet<>();

                Predicate<PersonFamily> fatherPredicate =  s -> s.getRelationship() != null && s.getRelationship().equals("RELATIONSHIP.FATHER");
                Predicate<PersonFamily> spousePredicate = s -> s.getRelationship() != null && s.getRelationship().equals("RELATIONSHIP.SPOUSE");
                if(fatherName!=null && !fatherName.trim().isEmpty() && person.getPersonFamilyList().stream().noneMatch(fatherPredicate)) {
                    personFamilies.add(PersonFamily.builder()
                            .id(0L)
                            .memberName(fatherName)
                            .gender("GENDER.MALE")
                            .personId(person)
                            .relationship("RELATIONSHIP.FATHER")
                            .build());
                }
                if(spouseName!=null && !spouseName.trim().isEmpty() && person.getPersonFamilyList().stream().noneMatch(spousePredicate)){
                    personFamilies.add(PersonFamily.builder()
                            .id(0L)
                            .memberName(spouseName)
                            .personId(person)
                            .relationship("RELATIONSHIP.SPOUSE")
                            .build());
                }
                person.getPersonProfile().setMiddleName(null);
                person.setPersonFamilyList(personFamilies);
                personService.save(person);
            }
            hasNext=personPage.hasNext();
            startPage++;
        }while(hasNext && ( endPage == null || startPage<=endPage ));

    }

    @Override
    public byte[] downloadCombinedPdfForMultipleIds(Set<Long> ids) {
         List<Ie> ieList = ids.stream()
            .map(this::getById)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

            if (ieList.isEmpty()) {
              return null;
             }

            List<byte[]> pdfList = ieList.stream()
            .map(Ie::getIdCardAttachmentPath)
            .filter(Objects::nonNull)
            .map(multipleIeCardUtil::getPdfBytesFromPath)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

            if (pdfList.isEmpty()) {
            return null;
            }

            ieList.forEach(ie -> {
            if (ie.getIdCardAttachmentPath() == null || ie.getIdCardAttachmentPath().isEmpty()) {
                throw new InvalidInputException(" IdCard not generated yet for Ie id : " +ie.getId());
            }
            });

           try {
                return multipleIeCardUtil.mergePdfsUsingUtility(pdfList);
           } catch (IOException e) {
               e.printStackTrace();
                return null;
           }
    }

    @Override
    public Page<Ie> getIeByWorkforce(Set<Long> worknodeIds, Set<Long> workforceIds, Set<String> taggedToServiceAreaCodes, String taggedToServiceAreaType, Integer page, Integer size, String sortBy, String direction) {
        return repository.findByWorkforce(worknodeIds, workforceIds, taggedToServiceAreaCodes,taggedToServiceAreaType, PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
    }

}
