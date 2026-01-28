package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.WherePeopleGoForRepairsService;


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

public class WherePeopleGoForRepairsServiceImpl implements WherePeopleGoForRepairsService {

    private final WherePeopleGoForRepairsDTOAssembler assembler;
    private final WherePeopleGoForRepairsRepository repository;


    public WherePeopleGoForRepairsServiceImpl(
        WherePeopleGoForRepairsRepository repository, WherePeopleGoForRepairsDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new WherePeopleGoForRepairs.
     *
     * @param WherePeopleGoForRepairs the WherePeopleGoForRepairs DTO to save
     * @return the saved WherePeopleGoForRepairs DTO
     */
    @Transactional
    @Override
    public WherePeopleGoForRepairsDTO save(WherePeopleGoForRepairsDTO WherePeopleGoForRepairs) {
        log.debug("Entry - save(WherePeopleGoForRepairs={})", WherePeopleGoForRepairs);
        WherePeopleGoForRepairs = preHookSave(WherePeopleGoForRepairs);
        WherePeopleGoForRepairs entity = assembler.fromDTO(WherePeopleGoForRepairs);
        WherePeopleGoForRepairsDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing WherePeopleGoForRepairs.
     *
     * @param WherePeopleGoForRepairs the WherePeopleGoForRepairs DTO to update
     * @return the updated WherePeopleGoForRepairs DTO
     * @throws NotFoundException if the WherePeopleGoForRepairs is not found
     */
    @Transactional
    @Override
    public WherePeopleGoForRepairsDTO update(WherePeopleGoForRepairsDTO WherePeopleGoForRepairs) {
        log.debug("Entry - update(WherePeopleGoForRepairs={})", WherePeopleGoForRepairs);
        WherePeopleGoForRepairs = preHookUpdate(WherePeopleGoForRepairs);
        WherePeopleGoForRepairs saved = repository.findById(WherePeopleGoForRepairs.getId()).orElseThrow(() -> new NotFoundException("WherePeopleGoForRepairs not found"));
        saved.update(assembler.fromDTO(WherePeopleGoForRepairs));
        saved = repository.save(saved);
        WherePeopleGoForRepairsDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of WherePeopleGoForRepairss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing WherePeopleGoForRepairs DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<WherePeopleGoForRepairsDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<WherePeopleGoForRepairs> result = repository.findAll(PageRequest.of(page, size));
        Set<WherePeopleGoForRepairsDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<WherePeopleGoForRepairsDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a WherePeopleGoForRepairs by ID.
     *
     * @param id     the ID of the WherePeopleGoForRepairs to delete
     * @param reason the reason for deletion
     * @return the deleted WherePeopleGoForRepairs DTO
     * @throws NotFoundException if the WherePeopleGoForRepairs is not found
     */
    @Transactional
    @Override
    public WherePeopleGoForRepairsDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        WherePeopleGoForRepairs saved = repository.findById(id).orElseThrow(() -> new NotFoundException("WherePeopleGoForRepairs not found"));
        saved.deactivate(reason);
        repository.save(saved);
        WherePeopleGoForRepairsDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a WherePeopleGoForRepairs by ID.
     *
     * @param id the ID of the WherePeopleGoForRepairs to retrieve
     * @return the WherePeopleGoForRepairs DTO
     * @throws NotFoundException if the WherePeopleGoForRepairs is not found
     */
    @Transactional(readOnly = true)
    @Override
    public WherePeopleGoForRepairsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        WherePeopleGoForRepairs saved = repository.findById(id).orElseThrow(() -> new NotFoundException("WherePeopleGoForRepairs not found"));
        WherePeopleGoForRepairsDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<WherePeopleGoForRepairsDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<WherePeopleGoForRepairs> savedData = repository.findAllById(ids);
        Set<WherePeopleGoForRepairsDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected WherePeopleGoForRepairsDTO postHookSave(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected WherePeopleGoForRepairsDTO preHookSave(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected WherePeopleGoForRepairsDTO postHookUpdate(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected WherePeopleGoForRepairsDTO preHookUpdate(WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO) {
        return WherePeopleGoForRepairsDTO;
    }

    protected WherePeopleGoForRepairsDTO postHookDelete(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected WherePeopleGoForRepairsDTO postHookGetById(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected PageDTO<WherePeopleGoForRepairsDTO> postHookGetAll(PageDTO<WherePeopleGoForRepairsDTO> result) {
        return result;
    }




}
