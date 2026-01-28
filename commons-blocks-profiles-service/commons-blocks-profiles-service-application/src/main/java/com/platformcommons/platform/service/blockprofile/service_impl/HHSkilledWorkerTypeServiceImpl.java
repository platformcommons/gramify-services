package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHSkilledWorkerTypeService;


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

public class HHSkilledWorkerTypeServiceImpl implements HHSkilledWorkerTypeService {

    private final HHSkilledWorkerTypeDTOAssembler assembler;
    private final HHSkilledWorkerTypeRepository repository;


    public HHSkilledWorkerTypeServiceImpl(
        HHSkilledWorkerTypeRepository repository, HHSkilledWorkerTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHSkilledWorkerType.
     *
     * @param HHSkilledWorkerType the HHSkilledWorkerType DTO to save
     * @return the saved HHSkilledWorkerType DTO
     */
    @Transactional
    @Override
    public HHSkilledWorkerTypeDTO save(HHSkilledWorkerTypeDTO HHSkilledWorkerType) {
        log.debug("Entry - save(HHSkilledWorkerType={})", HHSkilledWorkerType);
        HHSkilledWorkerType = preHookSave(HHSkilledWorkerType);
        HHSkilledWorkerType entity = assembler.fromDTO(HHSkilledWorkerType);
        HHSkilledWorkerTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHSkilledWorkerType.
     *
     * @param HHSkilledWorkerType the HHSkilledWorkerType DTO to update
     * @return the updated HHSkilledWorkerType DTO
     * @throws NotFoundException if the HHSkilledWorkerType is not found
     */
    @Transactional
    @Override
    public HHSkilledWorkerTypeDTO update(HHSkilledWorkerTypeDTO HHSkilledWorkerType) {
        log.debug("Entry - update(HHSkilledWorkerType={})", HHSkilledWorkerType);
        HHSkilledWorkerType = preHookUpdate(HHSkilledWorkerType);
        HHSkilledWorkerType saved = repository.findById(HHSkilledWorkerType.getId()).orElseThrow(() -> new NotFoundException("HHSkilledWorkerType not found"));
        saved.update(assembler.fromDTO(HHSkilledWorkerType));
        saved = repository.save(saved);
        HHSkilledWorkerTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHSkilledWorkerTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHSkilledWorkerType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHSkilledWorkerTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHSkilledWorkerType> result = repository.findAll(PageRequest.of(page, size));
        Set<HHSkilledWorkerTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHSkilledWorkerTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHSkilledWorkerType by ID.
     *
     * @param id     the ID of the HHSkilledWorkerType to delete
     * @param reason the reason for deletion
     * @return the deleted HHSkilledWorkerType DTO
     * @throws NotFoundException if the HHSkilledWorkerType is not found
     */
    @Transactional
    @Override
    public HHSkilledWorkerTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHSkilledWorkerType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSkilledWorkerType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHSkilledWorkerTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHSkilledWorkerType by ID.
     *
     * @param id the ID of the HHSkilledWorkerType to retrieve
     * @return the HHSkilledWorkerType DTO
     * @throws NotFoundException if the HHSkilledWorkerType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHSkilledWorkerTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHSkilledWorkerType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSkilledWorkerType not found"));
        HHSkilledWorkerTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHSkilledWorkerTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHSkilledWorkerType> savedData = repository.findAllById(ids);
        Set<HHSkilledWorkerTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHSkilledWorkerTypeDTO postHookSave(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected HHSkilledWorkerTypeDTO preHookSave(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected HHSkilledWorkerTypeDTO postHookUpdate(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected HHSkilledWorkerTypeDTO preHookUpdate(HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO) {
        return HHSkilledWorkerTypeDTO;
    }

    protected HHSkilledWorkerTypeDTO postHookDelete(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHSkilledWorkerTypeDTO postHookGetById(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHSkilledWorkerTypeDTO> postHookGetAll(PageDTO<HHSkilledWorkerTypeDTO> result) {
        return result;
    }




}
