package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHLoanSourceService;


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

public class HHLoanSourceServiceImpl implements HHLoanSourceService {

    private final HHLoanSourceDTOAssembler assembler;
    private final HHLoanSourceRepository repository;


    public HHLoanSourceServiceImpl(
        HHLoanSourceRepository repository, HHLoanSourceDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHLoanSource.
     *
     * @param HHLoanSource the HHLoanSource DTO to save
     * @return the saved HHLoanSource DTO
     */
    @Transactional
    @Override
    public HHLoanSourceDTO save(HHLoanSourceDTO HHLoanSource) {
        log.debug("Entry - save(HHLoanSource={})", HHLoanSource);
        HHLoanSource = preHookSave(HHLoanSource);
        HHLoanSource entity = assembler.fromDTO(HHLoanSource);
        HHLoanSourceDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHLoanSource.
     *
     * @param HHLoanSource the HHLoanSource DTO to update
     * @return the updated HHLoanSource DTO
     * @throws NotFoundException if the HHLoanSource is not found
     */
    @Transactional
    @Override
    public HHLoanSourceDTO update(HHLoanSourceDTO HHLoanSource) {
        log.debug("Entry - update(HHLoanSource={})", HHLoanSource);
        HHLoanSource = preHookUpdate(HHLoanSource);
        HHLoanSource saved = repository.findById(HHLoanSource.getId()).orElseThrow(() -> new NotFoundException("HHLoanSource not found"));
        saved.update(assembler.fromDTO(HHLoanSource));
        saved = repository.save(saved);
        HHLoanSourceDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHLoanSources.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHLoanSource DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHLoanSourceDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHLoanSource> result = repository.findAll(PageRequest.of(page, size));
        Set<HHLoanSourceDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHLoanSourceDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHLoanSource by ID.
     *
     * @param id     the ID of the HHLoanSource to delete
     * @param reason the reason for deletion
     * @return the deleted HHLoanSource DTO
     * @throws NotFoundException if the HHLoanSource is not found
     */
    @Transactional
    @Override
    public HHLoanSourceDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHLoanSource saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHLoanSource not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHLoanSourceDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHLoanSource by ID.
     *
     * @param id the ID of the HHLoanSource to retrieve
     * @return the HHLoanSource DTO
     * @throws NotFoundException if the HHLoanSource is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHLoanSourceDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHLoanSource saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHLoanSource not found"));
        HHLoanSourceDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHLoanSourceDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHLoanSource> savedData = repository.findAllById(ids);
        Set<HHLoanSourceDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHLoanSourceDTO postHookSave(HHLoanSourceDTO dto) {
        return dto;
    }

    protected HHLoanSourceDTO preHookSave(HHLoanSourceDTO dto) {
        return dto;
    }

    protected HHLoanSourceDTO postHookUpdate(HHLoanSourceDTO dto) {
        return dto;
    }

    protected HHLoanSourceDTO preHookUpdate(HHLoanSourceDTO HHLoanSourceDTO) {
        return HHLoanSourceDTO;
    }

    protected HHLoanSourceDTO postHookDelete(HHLoanSourceDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHLoanSourceDTO postHookGetById(HHLoanSourceDTO dto) {
        return dto;
    }

    protected PageDTO<HHLoanSourceDTO> postHookGetAll(PageDTO<HHLoanSourceDTO> result) {
        return result;
    }




}
