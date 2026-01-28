package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHGovernanceAspirationService;


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

public class HHGovernanceAspirationServiceImpl implements HHGovernanceAspirationService {

    private final HHGovernanceAspirationDTOAssembler assembler;
    private final HHGovernanceAspirationRepository repository;


    public HHGovernanceAspirationServiceImpl(
        HHGovernanceAspirationRepository repository, HHGovernanceAspirationDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHGovernanceAspiration.
     *
     * @param HHGovernanceAspiration the HHGovernanceAspiration DTO to save
     * @return the saved HHGovernanceAspiration DTO
     */
    @Transactional
    @Override
    public HHGovernanceAspirationDTO save(HHGovernanceAspirationDTO HHGovernanceAspiration) {
        log.debug("Entry - save(HHGovernanceAspiration={})", HHGovernanceAspiration);
        HHGovernanceAspiration = preHookSave(HHGovernanceAspiration);
        HHGovernanceAspiration entity = assembler.fromDTO(HHGovernanceAspiration);
        HHGovernanceAspirationDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHGovernanceAspiration.
     *
     * @param HHGovernanceAspiration the HHGovernanceAspiration DTO to update
     * @return the updated HHGovernanceAspiration DTO
     * @throws NotFoundException if the HHGovernanceAspiration is not found
     */
    @Transactional
    @Override
    public HHGovernanceAspirationDTO update(HHGovernanceAspirationDTO HHGovernanceAspiration) {
        log.debug("Entry - update(HHGovernanceAspiration={})", HHGovernanceAspiration);
        HHGovernanceAspiration = preHookUpdate(HHGovernanceAspiration);
        HHGovernanceAspiration saved = repository.findById(HHGovernanceAspiration.getId()).orElseThrow(() -> new NotFoundException("HHGovernanceAspiration not found"));
        saved.update(assembler.fromDTO(HHGovernanceAspiration));
        saved = repository.save(saved);
        HHGovernanceAspirationDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHGovernanceAspirations.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHGovernanceAspiration DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHGovernanceAspirationDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHGovernanceAspiration> result = repository.findAll(PageRequest.of(page, size));
        Set<HHGovernanceAspirationDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHGovernanceAspirationDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHGovernanceAspiration by ID.
     *
     * @param id     the ID of the HHGovernanceAspiration to delete
     * @param reason the reason for deletion
     * @return the deleted HHGovernanceAspiration DTO
     * @throws NotFoundException if the HHGovernanceAspiration is not found
     */
    @Transactional
    @Override
    public HHGovernanceAspirationDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHGovernanceAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHGovernanceAspiration not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHGovernanceAspirationDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHGovernanceAspiration by ID.
     *
     * @param id the ID of the HHGovernanceAspiration to retrieve
     * @return the HHGovernanceAspiration DTO
     * @throws NotFoundException if the HHGovernanceAspiration is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHGovernanceAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHGovernanceAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHGovernanceAspiration not found"));
        HHGovernanceAspirationDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHGovernanceAspirationDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHGovernanceAspiration> savedData = repository.findAllById(ids);
        Set<HHGovernanceAspirationDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHGovernanceAspirationDTO postHookSave(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected HHGovernanceAspirationDTO preHookSave(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected HHGovernanceAspirationDTO postHookUpdate(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected HHGovernanceAspirationDTO preHookUpdate(HHGovernanceAspirationDTO HHGovernanceAspirationDTO) {
        return HHGovernanceAspirationDTO;
    }

    protected HHGovernanceAspirationDTO postHookDelete(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHGovernanceAspirationDTO postHookGetById(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHGovernanceAspirationDTO> postHookGetAll(PageDTO<HHGovernanceAspirationDTO> result) {
        return result;
    }




}
