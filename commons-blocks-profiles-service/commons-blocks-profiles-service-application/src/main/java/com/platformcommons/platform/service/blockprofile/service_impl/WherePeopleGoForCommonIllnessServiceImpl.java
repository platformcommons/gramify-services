package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.WherePeopleGoForCommonIllnessService;


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

public class WherePeopleGoForCommonIllnessServiceImpl implements WherePeopleGoForCommonIllnessService {

    private final WherePeopleGoForCommonIllnessDTOAssembler assembler;
    private final WherePeopleGoForCommonIllnessRepository repository;


    public WherePeopleGoForCommonIllnessServiceImpl(
        WherePeopleGoForCommonIllnessRepository repository, WherePeopleGoForCommonIllnessDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new WherePeopleGoForCommonIllness.
     *
     * @param WherePeopleGoForCommonIllness the WherePeopleGoForCommonIllness DTO to save
     * @return the saved WherePeopleGoForCommonIllness DTO
     */
    @Transactional
    @Override
    public WherePeopleGoForCommonIllnessDTO save(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllness) {
        log.debug("Entry - save(WherePeopleGoForCommonIllness={})", WherePeopleGoForCommonIllness);
        WherePeopleGoForCommonIllness = preHookSave(WherePeopleGoForCommonIllness);
        WherePeopleGoForCommonIllness entity = assembler.fromDTO(WherePeopleGoForCommonIllness);
        WherePeopleGoForCommonIllnessDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing WherePeopleGoForCommonIllness.
     *
     * @param WherePeopleGoForCommonIllness the WherePeopleGoForCommonIllness DTO to update
     * @return the updated WherePeopleGoForCommonIllness DTO
     * @throws NotFoundException if the WherePeopleGoForCommonIllness is not found
     */
    @Transactional
    @Override
    public WherePeopleGoForCommonIllnessDTO update(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllness) {
        log.debug("Entry - update(WherePeopleGoForCommonIllness={})", WherePeopleGoForCommonIllness);
        WherePeopleGoForCommonIllness = preHookUpdate(WherePeopleGoForCommonIllness);
        WherePeopleGoForCommonIllness saved = repository.findById(WherePeopleGoForCommonIllness.getId()).orElseThrow(() -> new NotFoundException("WherePeopleGoForCommonIllness not found"));
        saved.update(assembler.fromDTO(WherePeopleGoForCommonIllness));
        saved = repository.save(saved);
        WherePeopleGoForCommonIllnessDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of WherePeopleGoForCommonIllnesss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing WherePeopleGoForCommonIllness DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<WherePeopleGoForCommonIllnessDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<WherePeopleGoForCommonIllness> result = repository.findAll(PageRequest.of(page, size));
        Set<WherePeopleGoForCommonIllnessDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<WherePeopleGoForCommonIllnessDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a WherePeopleGoForCommonIllness by ID.
     *
     * @param id     the ID of the WherePeopleGoForCommonIllness to delete
     * @param reason the reason for deletion
     * @return the deleted WherePeopleGoForCommonIllness DTO
     * @throws NotFoundException if the WherePeopleGoForCommonIllness is not found
     */
    @Transactional
    @Override
    public WherePeopleGoForCommonIllnessDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        WherePeopleGoForCommonIllness saved = repository.findById(id).orElseThrow(() -> new NotFoundException("WherePeopleGoForCommonIllness not found"));
        saved.deactivate(reason);
        repository.save(saved);
        WherePeopleGoForCommonIllnessDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a WherePeopleGoForCommonIllness by ID.
     *
     * @param id the ID of the WherePeopleGoForCommonIllness to retrieve
     * @return the WherePeopleGoForCommonIllness DTO
     * @throws NotFoundException if the WherePeopleGoForCommonIllness is not found
     */
    @Transactional(readOnly = true)
    @Override
    public WherePeopleGoForCommonIllnessDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        WherePeopleGoForCommonIllness saved = repository.findById(id).orElseThrow(() -> new NotFoundException("WherePeopleGoForCommonIllness not found"));
        WherePeopleGoForCommonIllnessDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<WherePeopleGoForCommonIllnessDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<WherePeopleGoForCommonIllness> savedData = repository.findAllById(ids);
        Set<WherePeopleGoForCommonIllnessDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected WherePeopleGoForCommonIllnessDTO postHookSave(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected WherePeopleGoForCommonIllnessDTO preHookSave(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected WherePeopleGoForCommonIllnessDTO postHookUpdate(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected WherePeopleGoForCommonIllnessDTO preHookUpdate(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO) {
        return WherePeopleGoForCommonIllnessDTO;
    }

    protected WherePeopleGoForCommonIllnessDTO postHookDelete(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected WherePeopleGoForCommonIllnessDTO postHookGetById(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected PageDTO<WherePeopleGoForCommonIllnessDTO> postHookGetAll(PageDTO<WherePeopleGoForCommonIllnessDTO> result) {
        return result;
    }




}
