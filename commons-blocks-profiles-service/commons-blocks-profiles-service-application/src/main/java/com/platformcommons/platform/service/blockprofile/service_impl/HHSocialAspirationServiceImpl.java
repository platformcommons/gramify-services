package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHSocialAspirationService;


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

public class HHSocialAspirationServiceImpl implements HHSocialAspirationService {

    private final HHSocialAspirationDTOAssembler assembler;
    private final HHSocialAspirationRepository repository;


    public HHSocialAspirationServiceImpl(
        HHSocialAspirationRepository repository, HHSocialAspirationDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHSocialAspiration.
     *
     * @param HHSocialAspiration the HHSocialAspiration DTO to save
     * @return the saved HHSocialAspiration DTO
     */
    @Transactional
    @Override
    public HHSocialAspirationDTO save(HHSocialAspirationDTO HHSocialAspiration) {
        log.debug("Entry - save(HHSocialAspiration={})", HHSocialAspiration);
        HHSocialAspiration = preHookSave(HHSocialAspiration);
        HHSocialAspiration entity = assembler.fromDTO(HHSocialAspiration);
        HHSocialAspirationDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHSocialAspiration.
     *
     * @param HHSocialAspiration the HHSocialAspiration DTO to update
     * @return the updated HHSocialAspiration DTO
     * @throws NotFoundException if the HHSocialAspiration is not found
     */
    @Transactional
    @Override
    public HHSocialAspirationDTO update(HHSocialAspirationDTO HHSocialAspiration) {
        log.debug("Entry - update(HHSocialAspiration={})", HHSocialAspiration);
        HHSocialAspiration = preHookUpdate(HHSocialAspiration);
        HHSocialAspiration saved = repository.findById(HHSocialAspiration.getId()).orElseThrow(() -> new NotFoundException("HHSocialAspiration not found"));
        saved.update(assembler.fromDTO(HHSocialAspiration));
        saved = repository.save(saved);
        HHSocialAspirationDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHSocialAspirations.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHSocialAspiration DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHSocialAspirationDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHSocialAspiration> result = repository.findAll(PageRequest.of(page, size));
        Set<HHSocialAspirationDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHSocialAspirationDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHSocialAspiration by ID.
     *
     * @param id     the ID of the HHSocialAspiration to delete
     * @param reason the reason for deletion
     * @return the deleted HHSocialAspiration DTO
     * @throws NotFoundException if the HHSocialAspiration is not found
     */
    @Transactional
    @Override
    public HHSocialAspirationDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHSocialAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSocialAspiration not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHSocialAspirationDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHSocialAspiration by ID.
     *
     * @param id the ID of the HHSocialAspiration to retrieve
     * @return the HHSocialAspiration DTO
     * @throws NotFoundException if the HHSocialAspiration is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHSocialAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHSocialAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHSocialAspiration not found"));
        HHSocialAspirationDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHSocialAspirationDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHSocialAspiration> savedData = repository.findAllById(ids);
        Set<HHSocialAspirationDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHSocialAspirationDTO postHookSave(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected HHSocialAspirationDTO preHookSave(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected HHSocialAspirationDTO postHookUpdate(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected HHSocialAspirationDTO preHookUpdate(HHSocialAspirationDTO HHSocialAspirationDTO) {
        return HHSocialAspirationDTO;
    }

    protected HHSocialAspirationDTO postHookDelete(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHSocialAspirationDTO postHookGetById(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHSocialAspirationDTO> postHookGetAll(PageDTO<HHSocialAspirationDTO> result) {
        return result;
    }




}
