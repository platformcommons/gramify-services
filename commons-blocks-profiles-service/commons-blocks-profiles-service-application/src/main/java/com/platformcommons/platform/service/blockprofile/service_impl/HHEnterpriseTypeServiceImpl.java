package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHEnterpriseTypeService;


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

public class HHEnterpriseTypeServiceImpl implements HHEnterpriseTypeService {

    private final HHEnterpriseTypeDTOAssembler assembler;
    private final HHEnterpriseTypeRepository repository;


    public HHEnterpriseTypeServiceImpl(
        HHEnterpriseTypeRepository repository, HHEnterpriseTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHEnterpriseType.
     *
     * @param HHEnterpriseType the HHEnterpriseType DTO to save
     * @return the saved HHEnterpriseType DTO
     */
    @Transactional
    @Override
    public HHEnterpriseTypeDTO save(HHEnterpriseTypeDTO HHEnterpriseType) {
        log.debug("Entry - save(HHEnterpriseType={})", HHEnterpriseType);
        HHEnterpriseType = preHookSave(HHEnterpriseType);
        HHEnterpriseType entity = assembler.fromDTO(HHEnterpriseType);
        HHEnterpriseTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHEnterpriseType.
     *
     * @param HHEnterpriseType the HHEnterpriseType DTO to update
     * @return the updated HHEnterpriseType DTO
     * @throws NotFoundException if the HHEnterpriseType is not found
     */
    @Transactional
    @Override
    public HHEnterpriseTypeDTO update(HHEnterpriseTypeDTO HHEnterpriseType) {
        log.debug("Entry - update(HHEnterpriseType={})", HHEnterpriseType);
        HHEnterpriseType = preHookUpdate(HHEnterpriseType);
        HHEnterpriseType saved = repository.findById(HHEnterpriseType.getId()).orElseThrow(() -> new NotFoundException("HHEnterpriseType not found"));
        saved.update(assembler.fromDTO(HHEnterpriseType));
        saved = repository.save(saved);
        HHEnterpriseTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHEnterpriseTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHEnterpriseType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHEnterpriseTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHEnterpriseType> result = repository.findAll(PageRequest.of(page, size));
        Set<HHEnterpriseTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHEnterpriseTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHEnterpriseType by ID.
     *
     * @param id     the ID of the HHEnterpriseType to delete
     * @param reason the reason for deletion
     * @return the deleted HHEnterpriseType DTO
     * @throws NotFoundException if the HHEnterpriseType is not found
     */
    @Transactional
    @Override
    public HHEnterpriseTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHEnterpriseType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEnterpriseType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHEnterpriseTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHEnterpriseType by ID.
     *
     * @param id the ID of the HHEnterpriseType to retrieve
     * @return the HHEnterpriseType DTO
     * @throws NotFoundException if the HHEnterpriseType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHEnterpriseTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEnterpriseType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEnterpriseType not found"));
        HHEnterpriseTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHEnterpriseTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHEnterpriseType> savedData = repository.findAllById(ids);
        Set<HHEnterpriseTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEnterpriseTypeDTO postHookSave(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected HHEnterpriseTypeDTO preHookSave(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected HHEnterpriseTypeDTO postHookUpdate(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected HHEnterpriseTypeDTO preHookUpdate(HHEnterpriseTypeDTO HHEnterpriseTypeDTO) {
        return HHEnterpriseTypeDTO;
    }

    protected HHEnterpriseTypeDTO postHookDelete(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEnterpriseTypeDTO postHookGetById(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHEnterpriseTypeDTO> postHookGetAll(PageDTO<HHEnterpriseTypeDTO> result) {
        return result;
    }




}
