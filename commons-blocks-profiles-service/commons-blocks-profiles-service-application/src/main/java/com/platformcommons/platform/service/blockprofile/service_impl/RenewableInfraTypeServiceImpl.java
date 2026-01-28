package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.RenewableInfraTypeService;


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

public class RenewableInfraTypeServiceImpl implements RenewableInfraTypeService {

    private final RenewableInfraTypeDTOAssembler assembler;
    private final RenewableInfraTypeRepository repository;


    public RenewableInfraTypeServiceImpl(
        RenewableInfraTypeRepository repository, RenewableInfraTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new RenewableInfraType.
     *
     * @param RenewableInfraType the RenewableInfraType DTO to save
     * @return the saved RenewableInfraType DTO
     */
    @Transactional
    @Override
    public RenewableInfraTypeDTO save(RenewableInfraTypeDTO RenewableInfraType) {
        log.debug("Entry - save(RenewableInfraType={})", RenewableInfraType);
        RenewableInfraType = preHookSave(RenewableInfraType);
        RenewableInfraType entity = assembler.fromDTO(RenewableInfraType);
        RenewableInfraTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing RenewableInfraType.
     *
     * @param RenewableInfraType the RenewableInfraType DTO to update
     * @return the updated RenewableInfraType DTO
     * @throws NotFoundException if the RenewableInfraType is not found
     */
    @Transactional
    @Override
    public RenewableInfraTypeDTO update(RenewableInfraTypeDTO RenewableInfraType) {
        log.debug("Entry - update(RenewableInfraType={})", RenewableInfraType);
        RenewableInfraType = preHookUpdate(RenewableInfraType);
        RenewableInfraType saved = repository.findById(RenewableInfraType.getId()).orElseThrow(() -> new NotFoundException("RenewableInfraType not found"));
        saved.update(assembler.fromDTO(RenewableInfraType));
        saved = repository.save(saved);
        RenewableInfraTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of RenewableInfraTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing RenewableInfraType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<RenewableInfraTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<RenewableInfraType> result = repository.findAll(PageRequest.of(page, size));
        Set<RenewableInfraTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<RenewableInfraTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a RenewableInfraType by ID.
     *
     * @param id     the ID of the RenewableInfraType to delete
     * @param reason the reason for deletion
     * @return the deleted RenewableInfraType DTO
     * @throws NotFoundException if the RenewableInfraType is not found
     */
    @Transactional
    @Override
    public RenewableInfraTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        RenewableInfraType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("RenewableInfraType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        RenewableInfraTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a RenewableInfraType by ID.
     *
     * @param id the ID of the RenewableInfraType to retrieve
     * @return the RenewableInfraType DTO
     * @throws NotFoundException if the RenewableInfraType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public RenewableInfraTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        RenewableInfraType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("RenewableInfraType not found"));
        RenewableInfraTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<RenewableInfraTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<RenewableInfraType> savedData = repository.findAllById(ids);
        Set<RenewableInfraTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected RenewableInfraTypeDTO postHookSave(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected RenewableInfraTypeDTO preHookSave(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected RenewableInfraTypeDTO postHookUpdate(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected RenewableInfraTypeDTO preHookUpdate(RenewableInfraTypeDTO RenewableInfraTypeDTO) {
        return RenewableInfraTypeDTO;
    }

    protected RenewableInfraTypeDTO postHookDelete(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected RenewableInfraTypeDTO postHookGetById(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected PageDTO<RenewableInfraTypeDTO> postHookGetAll(PageDTO<RenewableInfraTypeDTO> result) {
        return result;
    }




}
