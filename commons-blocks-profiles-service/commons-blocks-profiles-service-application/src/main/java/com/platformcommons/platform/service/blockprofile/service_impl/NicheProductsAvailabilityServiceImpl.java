package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.NicheProductsAvailabilityService;


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

public class NicheProductsAvailabilityServiceImpl implements NicheProductsAvailabilityService {

    private final NicheProductsAvailabilityDTOAssembler assembler;
    private final NicheProductsAvailabilityRepository repository;


    public NicheProductsAvailabilityServiceImpl(
        NicheProductsAvailabilityRepository repository, NicheProductsAvailabilityDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new NicheProductsAvailability.
     *
     * @param NicheProductsAvailability the NicheProductsAvailability DTO to save
     * @return the saved NicheProductsAvailability DTO
     */
    @Transactional
    @Override
    public NicheProductsAvailabilityDTO save(NicheProductsAvailabilityDTO NicheProductsAvailability) {
        log.debug("Entry - save(NicheProductsAvailability={})", NicheProductsAvailability);
        NicheProductsAvailability = preHookSave(NicheProductsAvailability);
        NicheProductsAvailability entity = assembler.fromDTO(NicheProductsAvailability);
        NicheProductsAvailabilityDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing NicheProductsAvailability.
     *
     * @param NicheProductsAvailability the NicheProductsAvailability DTO to update
     * @return the updated NicheProductsAvailability DTO
     * @throws NotFoundException if the NicheProductsAvailability is not found
     */
    @Transactional
    @Override
    public NicheProductsAvailabilityDTO update(NicheProductsAvailabilityDTO NicheProductsAvailability) {
        log.debug("Entry - update(NicheProductsAvailability={})", NicheProductsAvailability);
        NicheProductsAvailability = preHookUpdate(NicheProductsAvailability);
        NicheProductsAvailability saved = repository.findById(NicheProductsAvailability.getId()).orElseThrow(() -> new NotFoundException("NicheProductsAvailability not found"));
        saved.update(assembler.fromDTO(NicheProductsAvailability));
        saved = repository.save(saved);
        NicheProductsAvailabilityDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of NicheProductsAvailabilitys.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing NicheProductsAvailability DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<NicheProductsAvailabilityDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<NicheProductsAvailability> result = repository.findAll(PageRequest.of(page, size));
        Set<NicheProductsAvailabilityDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<NicheProductsAvailabilityDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a NicheProductsAvailability by ID.
     *
     * @param id     the ID of the NicheProductsAvailability to delete
     * @param reason the reason for deletion
     * @return the deleted NicheProductsAvailability DTO
     * @throws NotFoundException if the NicheProductsAvailability is not found
     */
    @Transactional
    @Override
    public NicheProductsAvailabilityDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        NicheProductsAvailability saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NicheProductsAvailability not found"));
        saved.deactivate(reason);
        repository.save(saved);
        NicheProductsAvailabilityDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a NicheProductsAvailability by ID.
     *
     * @param id the ID of the NicheProductsAvailability to retrieve
     * @return the NicheProductsAvailability DTO
     * @throws NotFoundException if the NicheProductsAvailability is not found
     */
    @Transactional(readOnly = true)
    @Override
    public NicheProductsAvailabilityDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        NicheProductsAvailability saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NicheProductsAvailability not found"));
        NicheProductsAvailabilityDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<NicheProductsAvailabilityDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<NicheProductsAvailability> savedData = repository.findAllById(ids);
        Set<NicheProductsAvailabilityDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected NicheProductsAvailabilityDTO postHookSave(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected NicheProductsAvailabilityDTO preHookSave(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected NicheProductsAvailabilityDTO postHookUpdate(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected NicheProductsAvailabilityDTO preHookUpdate(NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO) {
        return NicheProductsAvailabilityDTO;
    }

    protected NicheProductsAvailabilityDTO postHookDelete(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NicheProductsAvailabilityDTO postHookGetById(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected PageDTO<NicheProductsAvailabilityDTO> postHookGetAll(PageDTO<NicheProductsAvailabilityDTO> result) {
        return result;
    }




}
