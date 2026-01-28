package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHEconomicAspirationService;


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

public class HHEconomicAspirationServiceImpl implements HHEconomicAspirationService {

    private final HHEconomicAspirationDTOAssembler assembler;
    private final HHEconomicAspirationRepository repository;


    public HHEconomicAspirationServiceImpl(
        HHEconomicAspirationRepository repository, HHEconomicAspirationDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHEconomicAspiration.
     *
     * @param HHEconomicAspiration the HHEconomicAspiration DTO to save
     * @return the saved HHEconomicAspiration DTO
     */
    @Transactional
    @Override
    public HHEconomicAspirationDTO save(HHEconomicAspirationDTO HHEconomicAspiration) {
        log.debug("Entry - save(HHEconomicAspiration={})", HHEconomicAspiration);
        HHEconomicAspiration = preHookSave(HHEconomicAspiration);
        HHEconomicAspiration entity = assembler.fromDTO(HHEconomicAspiration);
        HHEconomicAspirationDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHEconomicAspiration.
     *
     * @param HHEconomicAspiration the HHEconomicAspiration DTO to update
     * @return the updated HHEconomicAspiration DTO
     * @throws NotFoundException if the HHEconomicAspiration is not found
     */
    @Transactional
    @Override
    public HHEconomicAspirationDTO update(HHEconomicAspirationDTO HHEconomicAspiration) {
        log.debug("Entry - update(HHEconomicAspiration={})", HHEconomicAspiration);
        HHEconomicAspiration = preHookUpdate(HHEconomicAspiration);
        HHEconomicAspiration saved = repository.findById(HHEconomicAspiration.getId()).orElseThrow(() -> new NotFoundException("HHEconomicAspiration not found"));
        saved.update(assembler.fromDTO(HHEconomicAspiration));
        saved = repository.save(saved);
        HHEconomicAspirationDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHEconomicAspirations.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHEconomicAspiration DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHEconomicAspirationDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHEconomicAspiration> result = repository.findAll(PageRequest.of(page, size));
        Set<HHEconomicAspirationDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHEconomicAspirationDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHEconomicAspiration by ID.
     *
     * @param id     the ID of the HHEconomicAspiration to delete
     * @param reason the reason for deletion
     * @return the deleted HHEconomicAspiration DTO
     * @throws NotFoundException if the HHEconomicAspiration is not found
     */
    @Transactional
    @Override
    public HHEconomicAspirationDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHEconomicAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEconomicAspiration not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHEconomicAspirationDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHEconomicAspiration by ID.
     *
     * @param id the ID of the HHEconomicAspiration to retrieve
     * @return the HHEconomicAspiration DTO
     * @throws NotFoundException if the HHEconomicAspiration is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHEconomicAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEconomicAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEconomicAspiration not found"));
        HHEconomicAspirationDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHEconomicAspirationDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHEconomicAspiration> savedData = repository.findAllById(ids);
        Set<HHEconomicAspirationDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEconomicAspirationDTO postHookSave(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected HHEconomicAspirationDTO preHookSave(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected HHEconomicAspirationDTO postHookUpdate(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected HHEconomicAspirationDTO preHookUpdate(HHEconomicAspirationDTO HHEconomicAspirationDTO) {
        return HHEconomicAspirationDTO;
    }

    protected HHEconomicAspirationDTO postHookDelete(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEconomicAspirationDTO postHookGetById(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHEconomicAspirationDTO> postHookGetAll(PageDTO<HHEconomicAspirationDTO> result) {
        return result;
    }




}
