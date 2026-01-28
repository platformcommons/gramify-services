package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.EmergingEnterpriseTypeService;


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

public class EmergingEnterpriseTypeServiceImpl implements EmergingEnterpriseTypeService {

    private final EmergingEnterpriseTypeDTOAssembler assembler;
    private final EmergingEnterpriseTypeRepository repository;


    public EmergingEnterpriseTypeServiceImpl(
        EmergingEnterpriseTypeRepository repository, EmergingEnterpriseTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new EmergingEnterpriseType.
     *
     * @param EmergingEnterpriseType the EmergingEnterpriseType DTO to save
     * @return the saved EmergingEnterpriseType DTO
     */
    @Transactional
    @Override
    public EmergingEnterpriseTypeDTO save(EmergingEnterpriseTypeDTO EmergingEnterpriseType) {
        log.debug("Entry - save(EmergingEnterpriseType={})", EmergingEnterpriseType);
        EmergingEnterpriseType = preHookSave(EmergingEnterpriseType);
        EmergingEnterpriseType entity = assembler.fromDTO(EmergingEnterpriseType);
        EmergingEnterpriseTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing EmergingEnterpriseType.
     *
     * @param EmergingEnterpriseType the EmergingEnterpriseType DTO to update
     * @return the updated EmergingEnterpriseType DTO
     * @throws NotFoundException if the EmergingEnterpriseType is not found
     */
    @Transactional
    @Override
    public EmergingEnterpriseTypeDTO update(EmergingEnterpriseTypeDTO EmergingEnterpriseType) {
        log.debug("Entry - update(EmergingEnterpriseType={})", EmergingEnterpriseType);
        EmergingEnterpriseType = preHookUpdate(EmergingEnterpriseType);
        EmergingEnterpriseType saved = repository.findById(EmergingEnterpriseType.getId()).orElseThrow(() -> new NotFoundException("EmergingEnterpriseType not found"));
        saved.update(assembler.fromDTO(EmergingEnterpriseType));
        saved = repository.save(saved);
        EmergingEnterpriseTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of EmergingEnterpriseTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing EmergingEnterpriseType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<EmergingEnterpriseTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<EmergingEnterpriseType> result = repository.findAll(PageRequest.of(page, size));
        Set<EmergingEnterpriseTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<EmergingEnterpriseTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a EmergingEnterpriseType by ID.
     *
     * @param id     the ID of the EmergingEnterpriseType to delete
     * @param reason the reason for deletion
     * @return the deleted EmergingEnterpriseType DTO
     * @throws NotFoundException if the EmergingEnterpriseType is not found
     */
    @Transactional
    @Override
    public EmergingEnterpriseTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        EmergingEnterpriseType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("EmergingEnterpriseType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        EmergingEnterpriseTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a EmergingEnterpriseType by ID.
     *
     * @param id the ID of the EmergingEnterpriseType to retrieve
     * @return the EmergingEnterpriseType DTO
     * @throws NotFoundException if the EmergingEnterpriseType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public EmergingEnterpriseTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        EmergingEnterpriseType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("EmergingEnterpriseType not found"));
        EmergingEnterpriseTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<EmergingEnterpriseTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<EmergingEnterpriseType> savedData = repository.findAllById(ids);
        Set<EmergingEnterpriseTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected EmergingEnterpriseTypeDTO postHookSave(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected EmergingEnterpriseTypeDTO preHookSave(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected EmergingEnterpriseTypeDTO postHookUpdate(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected EmergingEnterpriseTypeDTO preHookUpdate(EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO) {
        return EmergingEnterpriseTypeDTO;
    }

    protected EmergingEnterpriseTypeDTO postHookDelete(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected EmergingEnterpriseTypeDTO postHookGetById(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected PageDTO<EmergingEnterpriseTypeDTO> postHookGetAll(PageDTO<EmergingEnterpriseTypeDTO> result) {
        return result;
    }




}
