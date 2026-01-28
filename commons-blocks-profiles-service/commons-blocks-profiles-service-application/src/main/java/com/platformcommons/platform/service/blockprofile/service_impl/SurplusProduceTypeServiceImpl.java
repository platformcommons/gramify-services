package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.SurplusProduceTypeService;


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

public class SurplusProduceTypeServiceImpl implements SurplusProduceTypeService {

    private final SurplusProduceTypeDTOAssembler assembler;
    private final SurplusProduceTypeRepository repository;


    public SurplusProduceTypeServiceImpl(
        SurplusProduceTypeRepository repository, SurplusProduceTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new SurplusProduceType.
     *
     * @param SurplusProduceType the SurplusProduceType DTO to save
     * @return the saved SurplusProduceType DTO
     */
    @Transactional
    @Override
    public SurplusProduceTypeDTO save(SurplusProduceTypeDTO SurplusProduceType) {
        log.debug("Entry - save(SurplusProduceType={})", SurplusProduceType);
        SurplusProduceType = preHookSave(SurplusProduceType);
        SurplusProduceType entity = assembler.fromDTO(SurplusProduceType);
        SurplusProduceTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing SurplusProduceType.
     *
     * @param SurplusProduceType the SurplusProduceType DTO to update
     * @return the updated SurplusProduceType DTO
     * @throws NotFoundException if the SurplusProduceType is not found
     */
    @Transactional
    @Override
    public SurplusProduceTypeDTO update(SurplusProduceTypeDTO SurplusProduceType) {
        log.debug("Entry - update(SurplusProduceType={})", SurplusProduceType);
        SurplusProduceType = preHookUpdate(SurplusProduceType);
        SurplusProduceType saved = repository.findById(SurplusProduceType.getId()).orElseThrow(() -> new NotFoundException("SurplusProduceType not found"));
        saved.update(assembler.fromDTO(SurplusProduceType));
        saved = repository.save(saved);
        SurplusProduceTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of SurplusProduceTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing SurplusProduceType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<SurplusProduceTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<SurplusProduceType> result = repository.findAll(PageRequest.of(page, size));
        Set<SurplusProduceTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<SurplusProduceTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a SurplusProduceType by ID.
     *
     * @param id     the ID of the SurplusProduceType to delete
     * @param reason the reason for deletion
     * @return the deleted SurplusProduceType DTO
     * @throws NotFoundException if the SurplusProduceType is not found
     */
    @Transactional
    @Override
    public SurplusProduceTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        SurplusProduceType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SurplusProduceType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        SurplusProduceTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a SurplusProduceType by ID.
     *
     * @param id the ID of the SurplusProduceType to retrieve
     * @return the SurplusProduceType DTO
     * @throws NotFoundException if the SurplusProduceType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public SurplusProduceTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        SurplusProduceType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SurplusProduceType not found"));
        SurplusProduceTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<SurplusProduceTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<SurplusProduceType> savedData = repository.findAllById(ids);
        Set<SurplusProduceTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SurplusProduceTypeDTO postHookSave(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected SurplusProduceTypeDTO preHookSave(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected SurplusProduceTypeDTO postHookUpdate(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected SurplusProduceTypeDTO preHookUpdate(SurplusProduceTypeDTO SurplusProduceTypeDTO) {
        return SurplusProduceTypeDTO;
    }

    protected SurplusProduceTypeDTO postHookDelete(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SurplusProduceTypeDTO postHookGetById(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected PageDTO<SurplusProduceTypeDTO> postHookGetAll(PageDTO<SurplusProduceTypeDTO> result) {
        return result;
    }




}
