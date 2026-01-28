package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HyperLocalMarketProfileService;


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

public class HyperLocalMarketProfileServiceImpl implements HyperLocalMarketProfileService {

    private final HyperLocalMarketProfileDTOAssembler assembler;
    private final HyperLocalMarketProfileRepository repository;


    public HyperLocalMarketProfileServiceImpl(
        HyperLocalMarketProfileRepository repository, HyperLocalMarketProfileDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HyperLocalMarketProfile.
     *
     * @param HyperLocalMarketProfile the HyperLocalMarketProfile DTO to save
     * @return the saved HyperLocalMarketProfile DTO
     */
    @Transactional
    @Override
    public HyperLocalMarketProfileDTO save(HyperLocalMarketProfileDTO HyperLocalMarketProfile) {
        log.debug("Entry - save(HyperLocalMarketProfile={})", HyperLocalMarketProfile);
        HyperLocalMarketProfile = preHookSave(HyperLocalMarketProfile);
        HyperLocalMarketProfile entity = assembler.fromDTO(HyperLocalMarketProfile);
        HyperLocalMarketProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HyperLocalMarketProfile.
     *
     * @param HyperLocalMarketProfile the HyperLocalMarketProfile DTO to update
     * @return the updated HyperLocalMarketProfile DTO
     * @throws NotFoundException if the HyperLocalMarketProfile is not found
     */
    @Transactional
    @Override
    public HyperLocalMarketProfileDTO update(HyperLocalMarketProfileDTO HyperLocalMarketProfile) {
        log.debug("Entry - update(HyperLocalMarketProfile={})", HyperLocalMarketProfile);
        HyperLocalMarketProfile = preHookUpdate(HyperLocalMarketProfile);
        HyperLocalMarketProfile saved = repository.findById(HyperLocalMarketProfile.getId()).orElseThrow(() -> new NotFoundException("HyperLocalMarketProfile not found"));
        saved.update(assembler.fromDTO(HyperLocalMarketProfile));
        saved = repository.save(saved);
        HyperLocalMarketProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HyperLocalMarketProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HyperLocalMarketProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HyperLocalMarketProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HyperLocalMarketProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<HyperLocalMarketProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HyperLocalMarketProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HyperLocalMarketProfile by ID.
     *
     * @param id     the ID of the HyperLocalMarketProfile to delete
     * @param reason the reason for deletion
     * @return the deleted HyperLocalMarketProfile DTO
     * @throws NotFoundException if the HyperLocalMarketProfile is not found
     */
    @Transactional
    @Override
    public HyperLocalMarketProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HyperLocalMarketProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HyperLocalMarketProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HyperLocalMarketProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HyperLocalMarketProfile by ID.
     *
     * @param id the ID of the HyperLocalMarketProfile to retrieve
     * @return the HyperLocalMarketProfile DTO
     * @throws NotFoundException if the HyperLocalMarketProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HyperLocalMarketProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HyperLocalMarketProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HyperLocalMarketProfile not found"));
        HyperLocalMarketProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HyperLocalMarketProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HyperLocalMarketProfile> savedData = repository.findAllById(ids);
        Set<HyperLocalMarketProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HyperLocalMarketProfileDTO postHookSave(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected HyperLocalMarketProfileDTO preHookSave(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected HyperLocalMarketProfileDTO postHookUpdate(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected HyperLocalMarketProfileDTO preHookUpdate(HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO) {
        return HyperLocalMarketProfileDTO;
    }

    protected HyperLocalMarketProfileDTO postHookDelete(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HyperLocalMarketProfileDTO postHookGetById(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HyperLocalMarketProfileDTO> postHookGetAll(PageDTO<HyperLocalMarketProfileDTO> result) {
        return result;
    }




}
