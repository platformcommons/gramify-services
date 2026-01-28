package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdIncomeAndPovertyProfilService;


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

public class HouseholdIncomeAndPovertyProfilServiceImpl implements HouseholdIncomeAndPovertyProfilService {

    private final HouseholdIncomeAndPovertyProfilDTOAssembler assembler;
    private final HouseholdIncomeAndPovertyProfilRepository repository;


    public HouseholdIncomeAndPovertyProfilServiceImpl(
        HouseholdIncomeAndPovertyProfilRepository repository, HouseholdIncomeAndPovertyProfilDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HouseholdIncomeAndPovertyProfil.
     *
     * @param HouseholdIncomeAndPovertyProfil the HouseholdIncomeAndPovertyProfil DTO to save
     * @return the saved HouseholdIncomeAndPovertyProfil DTO
     */
    @Transactional
    @Override
    public HouseholdIncomeAndPovertyProfilDTO save(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfil) {
        log.debug("Entry - save(HouseholdIncomeAndPovertyProfil={})", HouseholdIncomeAndPovertyProfil);
        HouseholdIncomeAndPovertyProfil = preHookSave(HouseholdIncomeAndPovertyProfil);
        HouseholdIncomeAndPovertyProfil entity = assembler.fromDTO(HouseholdIncomeAndPovertyProfil);
        HouseholdIncomeAndPovertyProfilDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HouseholdIncomeAndPovertyProfil.
     *
     * @param HouseholdIncomeAndPovertyProfil the HouseholdIncomeAndPovertyProfil DTO to update
     * @return the updated HouseholdIncomeAndPovertyProfil DTO
     * @throws NotFoundException if the HouseholdIncomeAndPovertyProfil is not found
     */
    @Transactional
    @Override
    public HouseholdIncomeAndPovertyProfilDTO update(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfil) {
        log.debug("Entry - update(HouseholdIncomeAndPovertyProfil={})", HouseholdIncomeAndPovertyProfil);
        HouseholdIncomeAndPovertyProfil = preHookUpdate(HouseholdIncomeAndPovertyProfil);
        HouseholdIncomeAndPovertyProfil saved = repository.findById(HouseholdIncomeAndPovertyProfil.getId()).orElseThrow(() -> new NotFoundException("HouseholdIncomeAndPovertyProfil not found"));
        saved.update(assembler.fromDTO(HouseholdIncomeAndPovertyProfil));
        saved = repository.save(saved);
        HouseholdIncomeAndPovertyProfilDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HouseholdIncomeAndPovertyProfils.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HouseholdIncomeAndPovertyProfil DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdIncomeAndPovertyProfilDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HouseholdIncomeAndPovertyProfil> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdIncomeAndPovertyProfilDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdIncomeAndPovertyProfilDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HouseholdIncomeAndPovertyProfil by ID.
     *
     * @param id     the ID of the HouseholdIncomeAndPovertyProfil to delete
     * @param reason the reason for deletion
     * @return the deleted HouseholdIncomeAndPovertyProfil DTO
     * @throws NotFoundException if the HouseholdIncomeAndPovertyProfil is not found
     */
    @Transactional
    @Override
    public HouseholdIncomeAndPovertyProfilDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HouseholdIncomeAndPovertyProfil saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdIncomeAndPovertyProfil not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdIncomeAndPovertyProfilDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HouseholdIncomeAndPovertyProfil by ID.
     *
     * @param id the ID of the HouseholdIncomeAndPovertyProfil to retrieve
     * @return the HouseholdIncomeAndPovertyProfil DTO
     * @throws NotFoundException if the HouseholdIncomeAndPovertyProfil is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdIncomeAndPovertyProfilDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdIncomeAndPovertyProfil saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdIncomeAndPovertyProfil not found"));
        HouseholdIncomeAndPovertyProfilDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdIncomeAndPovertyProfilDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HouseholdIncomeAndPovertyProfil> savedData = repository.findAllById(ids);
        Set<HouseholdIncomeAndPovertyProfilDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HouseholdIncomeAndPovertyProfilDTO postHookSave(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected HouseholdIncomeAndPovertyProfilDTO preHookSave(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected HouseholdIncomeAndPovertyProfilDTO postHookUpdate(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected HouseholdIncomeAndPovertyProfilDTO preHookUpdate(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO) {
        return HouseholdIncomeAndPovertyProfilDTO;
    }

    protected HouseholdIncomeAndPovertyProfilDTO postHookDelete(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdIncomeAndPovertyProfilDTO postHookGetById(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdIncomeAndPovertyProfilDTO> postHookGetAll(PageDTO<HouseholdIncomeAndPovertyProfilDTO> result) {
        return result;
    }




}
