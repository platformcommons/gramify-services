package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.SupportNeededForNicheGrowthService;


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

public class SupportNeededForNicheGrowthServiceImpl implements SupportNeededForNicheGrowthService {

    private final SupportNeededForNicheGrowthDTOAssembler assembler;
    private final SupportNeededForNicheGrowthRepository repository;


    public SupportNeededForNicheGrowthServiceImpl(
        SupportNeededForNicheGrowthRepository repository, SupportNeededForNicheGrowthDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new SupportNeededForNicheGrowth.
     *
     * @param SupportNeededForNicheGrowth the SupportNeededForNicheGrowth DTO to save
     * @return the saved SupportNeededForNicheGrowth DTO
     */
    @Transactional
    @Override
    public SupportNeededForNicheGrowthDTO save(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowth) {
        log.debug("Entry - save(SupportNeededForNicheGrowth={})", SupportNeededForNicheGrowth);
        SupportNeededForNicheGrowth = preHookSave(SupportNeededForNicheGrowth);
        SupportNeededForNicheGrowth entity = assembler.fromDTO(SupportNeededForNicheGrowth);
        SupportNeededForNicheGrowthDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing SupportNeededForNicheGrowth.
     *
     * @param SupportNeededForNicheGrowth the SupportNeededForNicheGrowth DTO to update
     * @return the updated SupportNeededForNicheGrowth DTO
     * @throws NotFoundException if the SupportNeededForNicheGrowth is not found
     */
    @Transactional
    @Override
    public SupportNeededForNicheGrowthDTO update(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowth) {
        log.debug("Entry - update(SupportNeededForNicheGrowth={})", SupportNeededForNicheGrowth);
        SupportNeededForNicheGrowth = preHookUpdate(SupportNeededForNicheGrowth);
        SupportNeededForNicheGrowth saved = repository.findById(SupportNeededForNicheGrowth.getId()).orElseThrow(() -> new NotFoundException("SupportNeededForNicheGrowth not found"));
        saved.update(assembler.fromDTO(SupportNeededForNicheGrowth));
        saved = repository.save(saved);
        SupportNeededForNicheGrowthDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of SupportNeededForNicheGrowths.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing SupportNeededForNicheGrowth DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<SupportNeededForNicheGrowthDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<SupportNeededForNicheGrowth> result = repository.findAll(PageRequest.of(page, size));
        Set<SupportNeededForNicheGrowthDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<SupportNeededForNicheGrowthDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a SupportNeededForNicheGrowth by ID.
     *
     * @param id     the ID of the SupportNeededForNicheGrowth to delete
     * @param reason the reason for deletion
     * @return the deleted SupportNeededForNicheGrowth DTO
     * @throws NotFoundException if the SupportNeededForNicheGrowth is not found
     */
    @Transactional
    @Override
    public SupportNeededForNicheGrowthDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        SupportNeededForNicheGrowth saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SupportNeededForNicheGrowth not found"));
        saved.deactivate(reason);
        repository.save(saved);
        SupportNeededForNicheGrowthDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a SupportNeededForNicheGrowth by ID.
     *
     * @param id the ID of the SupportNeededForNicheGrowth to retrieve
     * @return the SupportNeededForNicheGrowth DTO
     * @throws NotFoundException if the SupportNeededForNicheGrowth is not found
     */
    @Transactional(readOnly = true)
    @Override
    public SupportNeededForNicheGrowthDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        SupportNeededForNicheGrowth saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SupportNeededForNicheGrowth not found"));
        SupportNeededForNicheGrowthDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<SupportNeededForNicheGrowthDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<SupportNeededForNicheGrowth> savedData = repository.findAllById(ids);
        Set<SupportNeededForNicheGrowthDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SupportNeededForNicheGrowthDTO postHookSave(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected SupportNeededForNicheGrowthDTO preHookSave(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected SupportNeededForNicheGrowthDTO postHookUpdate(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected SupportNeededForNicheGrowthDTO preHookUpdate(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO) {
        return SupportNeededForNicheGrowthDTO;
    }

    protected SupportNeededForNicheGrowthDTO postHookDelete(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SupportNeededForNicheGrowthDTO postHookGetById(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected PageDTO<SupportNeededForNicheGrowthDTO> postHookGetAll(PageDTO<SupportNeededForNicheGrowthDTO> result) {
        return result;
    }




}
