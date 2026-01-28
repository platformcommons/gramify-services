package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHSavingsModeService;


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

public class HHSavingsModeServiceImpl implements HHSavingsModeService {

    private final HHSavingsModeDTOAssembler assembler;
    private final HHSavingsModeRepository repository;


    public HHSavingsModeServiceImpl(
        HHSavingsModeRepository repository, HHSavingsModeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHSavingsMode.
     *
     * @param HHSavingsMode the HHSavingsMode DTO to save
     * @return the saved HHSavingsMode DTO
     */
    @Transactional
    @Override
    public HHSavingsModeDTO save(HHSavingsModeDTO HHSavingsMode) {
        log.debug("Entry - save(HHSavingsMode={})", HHSavingsMode);
        HHSavingsMode = preHookSave(HHSavingsMode);
        HHSavingsMode entity = assembler.fromDTO(HHSavingsMode);
        HHSavingsModeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHSavingsMode.
     *
     * @param HHSavingsMode the HHSavingsMode DTO to update
     * @return the updated HHSavingsMode DTO
     * @throws NotFoundException if the HHSavingsMode is not found
     */
    @Transactional
    @Override
    public HHSavingsModeDTO update(HHSavingsModeDTO HHSavingsMode) {
        log.debug("Entry - update(HHSavingsMode={})", HHSavingsMode);
        HHSavingsMode = preHookUpdate(HHSavingsMode);
        HHSavingsMode saved = repository.findById(HHSavingsMode.getId()).orElseThrow(() -> new NotFoundException("HHSavingsMode not found"));
        saved.update(assembler.fromDTO(HHSavingsMode));
        saved = repository.save(saved);
        HHSavingsModeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHSavingsModes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHSavingsMode DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHSavingsModeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHSavingsMode> result = repository.findAll(PageRequest.of(page, size));
        Set<HHSavingsModeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHSavingsModeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHSavingsMode by ID.
     *
     * @param id     the ID of the HHSavingsMode to delete
     * @param reason the reason for deletion
     * @return the deleted HHSavingsMode DTO
     * @throws NotFoundException if the HHSavingsMode is not found
     */
    @Transactional
    @Override
    public HHSavingsModeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHSavingsMode saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSavingsMode not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHSavingsModeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHSavingsMode by ID.
     *
     * @param id the ID of the HHSavingsMode to retrieve
     * @return the HHSavingsMode DTO
     * @throws NotFoundException if the HHSavingsMode is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHSavingsModeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHSavingsMode saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSavingsMode not found"));
        HHSavingsModeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHSavingsModeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHSavingsMode> savedData = repository.findAllById(ids);
        Set<HHSavingsModeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHSavingsModeDTO postHookSave(HHSavingsModeDTO dto) {
        return dto;
    }

    protected HHSavingsModeDTO preHookSave(HHSavingsModeDTO dto) {
        return dto;
    }

    protected HHSavingsModeDTO postHookUpdate(HHSavingsModeDTO dto) {
        return dto;
    }

    protected HHSavingsModeDTO preHookUpdate(HHSavingsModeDTO HHSavingsModeDTO) {
        return HHSavingsModeDTO;
    }

    protected HHSavingsModeDTO postHookDelete(HHSavingsModeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHSavingsModeDTO postHookGetById(HHSavingsModeDTO dto) {
        return dto;
    }

    protected PageDTO<HHSavingsModeDTO> postHookGetAll(PageDTO<HHSavingsModeDTO> result) {
        return result;
    }




}
