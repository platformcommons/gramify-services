package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHEmploymentTypeService;


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

public class HHEmploymentTypeServiceImpl implements HHEmploymentTypeService {

    private final HHEmploymentTypeDTOAssembler assembler;
    private final HHEmploymentTypeRepository repository;


    public HHEmploymentTypeServiceImpl(
        HHEmploymentTypeRepository repository, HHEmploymentTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHEmploymentType.
     *
     * @param HHEmploymentType the HHEmploymentType DTO to save
     * @return the saved HHEmploymentType DTO
     */
    @Transactional
    @Override
    public HHEmploymentTypeDTO save(HHEmploymentTypeDTO HHEmploymentType) {
        log.debug("Entry - save(HHEmploymentType={})", HHEmploymentType);
        HHEmploymentType = preHookSave(HHEmploymentType);
        HHEmploymentType entity = assembler.fromDTO(HHEmploymentType);
        HHEmploymentTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHEmploymentType.
     *
     * @param HHEmploymentType the HHEmploymentType DTO to update
     * @return the updated HHEmploymentType DTO
     * @throws NotFoundException if the HHEmploymentType is not found
     */
    @Transactional
    @Override
    public HHEmploymentTypeDTO update(HHEmploymentTypeDTO HHEmploymentType) {
        log.debug("Entry - update(HHEmploymentType={})", HHEmploymentType);
        HHEmploymentType = preHookUpdate(HHEmploymentType);
        HHEmploymentType saved = repository.findById(HHEmploymentType.getId()).orElseThrow(() -> new NotFoundException("HHEmploymentType not found"));
        saved.update(assembler.fromDTO(HHEmploymentType));
        saved = repository.save(saved);
        HHEmploymentTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHEmploymentTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHEmploymentType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHEmploymentTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHEmploymentType> result = repository.findAll(PageRequest.of(page, size));
        Set<HHEmploymentTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHEmploymentTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHEmploymentType by ID.
     *
     * @param id     the ID of the HHEmploymentType to delete
     * @param reason the reason for deletion
     * @return the deleted HHEmploymentType DTO
     * @throws NotFoundException if the HHEmploymentType is not found
     */
    @Transactional
    @Override
    public HHEmploymentTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHEmploymentType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEmploymentType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHEmploymentTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHEmploymentType by ID.
     *
     * @param id the ID of the HHEmploymentType to retrieve
     * @return the HHEmploymentType DTO
     * @throws NotFoundException if the HHEmploymentType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHEmploymentTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEmploymentType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEmploymentType not found"));
        HHEmploymentTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHEmploymentTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHEmploymentType> savedData = repository.findAllById(ids);
        Set<HHEmploymentTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEmploymentTypeDTO postHookSave(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected HHEmploymentTypeDTO preHookSave(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected HHEmploymentTypeDTO postHookUpdate(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected HHEmploymentTypeDTO preHookUpdate(HHEmploymentTypeDTO HHEmploymentTypeDTO) {
        return HHEmploymentTypeDTO;
    }

    protected HHEmploymentTypeDTO postHookDelete(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEmploymentTypeDTO postHookGetById(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHEmploymentTypeDTO> postHookGetAll(PageDTO<HHEmploymentTypeDTO> result) {
        return result;
    }




}
