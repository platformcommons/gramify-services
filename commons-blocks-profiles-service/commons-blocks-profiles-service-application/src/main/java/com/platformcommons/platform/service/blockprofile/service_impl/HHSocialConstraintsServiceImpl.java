package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHSocialConstraintsService;


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

public class HHSocialConstraintsServiceImpl implements HHSocialConstraintsService {

    private final HHSocialConstraintsDTOAssembler assembler;
    private final HHSocialConstraintsRepository repository;


    public HHSocialConstraintsServiceImpl(
        HHSocialConstraintsRepository repository, HHSocialConstraintsDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHSocialConstraints.
     *
     * @param HHSocialConstraints the HHSocialConstraints DTO to save
     * @return the saved HHSocialConstraints DTO
     */
    @Transactional
    @Override
    public HHSocialConstraintsDTO save(HHSocialConstraintsDTO HHSocialConstraints) {
        log.debug("Entry - save(HHSocialConstraints={})", HHSocialConstraints);
        HHSocialConstraints = preHookSave(HHSocialConstraints);
        HHSocialConstraints entity = assembler.fromDTO(HHSocialConstraints);
        HHSocialConstraintsDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHSocialConstraints.
     *
     * @param HHSocialConstraints the HHSocialConstraints DTO to update
     * @return the updated HHSocialConstraints DTO
     * @throws NotFoundException if the HHSocialConstraints is not found
     */
    @Transactional
    @Override
    public HHSocialConstraintsDTO update(HHSocialConstraintsDTO HHSocialConstraints) {
        log.debug("Entry - update(HHSocialConstraints={})", HHSocialConstraints);
        HHSocialConstraints = preHookUpdate(HHSocialConstraints);
        HHSocialConstraints saved = repository.findById(HHSocialConstraints.getId()).orElseThrow(() -> new NotFoundException("HHSocialConstraints not found"));
        saved.update(assembler.fromDTO(HHSocialConstraints));
        saved = repository.save(saved);
        HHSocialConstraintsDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHSocialConstraintss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHSocialConstraints DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHSocialConstraintsDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHSocialConstraints> result = repository.findAll(PageRequest.of(page, size));
        Set<HHSocialConstraintsDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHSocialConstraintsDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHSocialConstraints by ID.
     *
     * @param id     the ID of the HHSocialConstraints to delete
     * @param reason the reason for deletion
     * @return the deleted HHSocialConstraints DTO
     * @throws NotFoundException if the HHSocialConstraints is not found
     */
    @Transactional
    @Override
    public HHSocialConstraintsDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHSocialConstraints saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSocialConstraints not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHSocialConstraintsDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHSocialConstraints by ID.
     *
     * @param id the ID of the HHSocialConstraints to retrieve
     * @return the HHSocialConstraints DTO
     * @throws NotFoundException if the HHSocialConstraints is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHSocialConstraintsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHSocialConstraints saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSocialConstraints not found"));
        HHSocialConstraintsDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHSocialConstraintsDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHSocialConstraints> savedData = repository.findAllById(ids);
        Set<HHSocialConstraintsDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHSocialConstraintsDTO postHookSave(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected HHSocialConstraintsDTO preHookSave(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected HHSocialConstraintsDTO postHookUpdate(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected HHSocialConstraintsDTO preHookUpdate(HHSocialConstraintsDTO HHSocialConstraintsDTO) {
        return HHSocialConstraintsDTO;
    }

    protected HHSocialConstraintsDTO postHookDelete(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHSocialConstraintsDTO postHookGetById(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected PageDTO<HHSocialConstraintsDTO> postHookGetAll(PageDTO<HHSocialConstraintsDTO> result) {
        return result;
    }




}
