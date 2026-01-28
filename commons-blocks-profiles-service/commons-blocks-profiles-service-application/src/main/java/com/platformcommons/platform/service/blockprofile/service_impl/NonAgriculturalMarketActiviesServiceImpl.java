package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.NonAgriculturalMarketActiviesService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j

public class NonAgriculturalMarketActiviesServiceImpl implements NonAgriculturalMarketActiviesService {

    private final NonAgriculturalMarketActiviesDTOAssembler assembler;
    private final NonAgriculturalMarketActiviesRepository repository;


    private final OtherIndustryTypeDTOAssembler assemblerOtherIndustryType;
    private final OtherIndustryTypeRepository repositoryOtherIndustryType;


    public NonAgriculturalMarketActiviesServiceImpl(
        OtherIndustryTypeDTOAssembler assemblerOtherIndustryType,  OtherIndustryTypeRepository repositoryOtherIndustryType,
        NonAgriculturalMarketActiviesRepository repository, NonAgriculturalMarketActiviesDTOAssembler assembler) {
        this.assemblerOtherIndustryType = assemblerOtherIndustryType;
        this.repositoryOtherIndustryType = repositoryOtherIndustryType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new NonAgriculturalMarketActivies.
     *
     * @param NonAgriculturalMarketActivies the NonAgriculturalMarketActivies DTO to save
     * @return the saved NonAgriculturalMarketActivies DTO
     */
    @Transactional
    @Override
    public NonAgriculturalMarketActiviesDTO save(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActivies) {
        log.debug("Entry - save(NonAgriculturalMarketActivies={})", NonAgriculturalMarketActivies);
        NonAgriculturalMarketActivies = preHookSave(NonAgriculturalMarketActivies);
        NonAgriculturalMarketActivies entity = assembler.fromDTO(NonAgriculturalMarketActivies);
        NonAgriculturalMarketActiviesDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing NonAgriculturalMarketActivies.
     *
     * @param NonAgriculturalMarketActivies the NonAgriculturalMarketActivies DTO to update
     * @return the updated NonAgriculturalMarketActivies DTO
     * @throws NotFoundException if the NonAgriculturalMarketActivies is not found
     */
    @Transactional
    @Override
    public NonAgriculturalMarketActiviesDTO update(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActivies) {
        log.debug("Entry - update(NonAgriculturalMarketActivies={})", NonAgriculturalMarketActivies);
        NonAgriculturalMarketActivies = preHookUpdate(NonAgriculturalMarketActivies);
        NonAgriculturalMarketActivies saved = repository.findById(NonAgriculturalMarketActivies.getId()).orElseThrow(() -> new NotFoundException("NonAgriculturalMarketActivies not found"));
        saved.update(assembler.fromDTO(NonAgriculturalMarketActivies));
        saved = repository.save(saved);
        NonAgriculturalMarketActiviesDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of NonAgriculturalMarketActiviess.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing NonAgriculturalMarketActivies DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<NonAgriculturalMarketActiviesDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<NonAgriculturalMarketActivies> result = repository.findAll(PageRequest.of(page, size));
        Set<NonAgriculturalMarketActiviesDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<NonAgriculturalMarketActiviesDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a NonAgriculturalMarketActivies by ID.
     *
     * @param id     the ID of the NonAgriculturalMarketActivies to delete
     * @param reason the reason for deletion
     * @return the deleted NonAgriculturalMarketActivies DTO
     * @throws NotFoundException if the NonAgriculturalMarketActivies is not found
     */
    @Transactional
    @Override
    public NonAgriculturalMarketActiviesDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        NonAgriculturalMarketActivies saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NonAgriculturalMarketActivies not found"));
        saved.deactivate(reason);
        repository.save(saved);
        NonAgriculturalMarketActiviesDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a NonAgriculturalMarketActivies by ID.
     *
     * @param id the ID of the NonAgriculturalMarketActivies to retrieve
     * @return the NonAgriculturalMarketActivies DTO
     * @throws NotFoundException if the NonAgriculturalMarketActivies is not found
     */
    @Transactional(readOnly = true)
    @Override
    public NonAgriculturalMarketActiviesDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        NonAgriculturalMarketActivies saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NonAgriculturalMarketActivies not found"));
        NonAgriculturalMarketActiviesDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<NonAgriculturalMarketActiviesDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<NonAgriculturalMarketActivies> savedData = repository.findAllById(ids);
        Set<NonAgriculturalMarketActiviesDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of otherIndustryTypeList to a NonAgriculturalMarketActivies identified by their ID.
      *
      * @param id               The ID of the NonAgriculturalMarketActivies to add hobbies to
      * @param otherIndustryTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addOtherIndustryTypeToNonAgriculturalMarketActivies(Long id, List<OtherIndustryTypeDTO> otherIndustryTypeList){
         NonAgriculturalMarketActivies saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NonAgriculturalMarketActivies not found"));
         if(otherIndustryTypeList != null && !otherIndustryTypeList.isEmpty()) {

             Set<OtherIndustryType> toBeSavedList = otherIndustryTypeList.stream().map(it-> assemblerOtherIndustryType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setNonAgriculturalMarketActivies(saved));
             repositoryOtherIndustryType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected NonAgriculturalMarketActiviesDTO postHookSave(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected NonAgriculturalMarketActiviesDTO preHookSave(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected NonAgriculturalMarketActiviesDTO postHookUpdate(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected NonAgriculturalMarketActiviesDTO preHookUpdate(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO) {
        return NonAgriculturalMarketActiviesDTO;
    }

    protected NonAgriculturalMarketActiviesDTO postHookDelete(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NonAgriculturalMarketActiviesDTO postHookGetById(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected PageDTO<NonAgriculturalMarketActiviesDTO> postHookGetAll(PageDTO<NonAgriculturalMarketActiviesDTO> result) {
        return result;
    }




}
