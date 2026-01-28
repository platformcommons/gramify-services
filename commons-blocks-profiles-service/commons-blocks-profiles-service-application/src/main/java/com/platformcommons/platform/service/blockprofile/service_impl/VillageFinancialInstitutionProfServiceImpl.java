package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageFinancialInstitutionProfService;


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

public class VillageFinancialInstitutionProfServiceImpl implements VillageFinancialInstitutionProfService {

    private final VillageFinancialInstitutionProfDTOAssembler assembler;
    private final VillageFinancialInstitutionProfRepository repository;


    public VillageFinancialInstitutionProfServiceImpl(
        VillageFinancialInstitutionProfRepository repository, VillageFinancialInstitutionProfDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageFinancialInstitutionProf.
     *
     * @param VillageFinancialInstitutionProf the VillageFinancialInstitutionProf DTO to save
     * @return the saved VillageFinancialInstitutionProf DTO
     */
    @Transactional
    @Override
    public VillageFinancialInstitutionProfDTO save(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProf) {
        log.debug("Entry - save(VillageFinancialInstitutionProf={})", VillageFinancialInstitutionProf);
        VillageFinancialInstitutionProf = preHookSave(VillageFinancialInstitutionProf);
        VillageFinancialInstitutionProf entity = assembler.fromDTO(VillageFinancialInstitutionProf);
        VillageFinancialInstitutionProfDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageFinancialInstitutionProf.
     *
     * @param VillageFinancialInstitutionProf the VillageFinancialInstitutionProf DTO to update
     * @return the updated VillageFinancialInstitutionProf DTO
     * @throws NotFoundException if the VillageFinancialInstitutionProf is not found
     */
    @Transactional
    @Override
    public VillageFinancialInstitutionProfDTO update(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProf) {
        log.debug("Entry - update(VillageFinancialInstitutionProf={})", VillageFinancialInstitutionProf);
        VillageFinancialInstitutionProf = preHookUpdate(VillageFinancialInstitutionProf);
        VillageFinancialInstitutionProf saved = repository.findById(VillageFinancialInstitutionProf.getId()).orElseThrow(() -> new NotFoundException("VillageFinancialInstitutionProf not found"));
        saved.update(assembler.fromDTO(VillageFinancialInstitutionProf));
        saved = repository.save(saved);
        VillageFinancialInstitutionProfDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageFinancialInstitutionProfs.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageFinancialInstitutionProf DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageFinancialInstitutionProfDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageFinancialInstitutionProf> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageFinancialInstitutionProfDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageFinancialInstitutionProfDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageFinancialInstitutionProf by ID.
     *
     * @param id     the ID of the VillageFinancialInstitutionProf to delete
     * @param reason the reason for deletion
     * @return the deleted VillageFinancialInstitutionProf DTO
     * @throws NotFoundException if the VillageFinancialInstitutionProf is not found
     */
    @Transactional
    @Override
    public VillageFinancialInstitutionProfDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageFinancialInstitutionProf saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageFinancialInstitutionProf not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageFinancialInstitutionProfDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageFinancialInstitutionProf by ID.
     *
     * @param id the ID of the VillageFinancialInstitutionProf to retrieve
     * @return the VillageFinancialInstitutionProf DTO
     * @throws NotFoundException if the VillageFinancialInstitutionProf is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageFinancialInstitutionProfDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageFinancialInstitutionProf saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageFinancialInstitutionProf not found"));
        VillageFinancialInstitutionProfDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageFinancialInstitutionProfDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageFinancialInstitutionProf> savedData = repository.findAllById(ids);
        Set<VillageFinancialInstitutionProfDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageFinancialInstitutionProfDTO postHookSave(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected VillageFinancialInstitutionProfDTO preHookSave(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected VillageFinancialInstitutionProfDTO postHookUpdate(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected VillageFinancialInstitutionProfDTO preHookUpdate(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO) {
        return VillageFinancialInstitutionProfDTO;
    }

    protected VillageFinancialInstitutionProfDTO postHookDelete(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageFinancialInstitutionProfDTO postHookGetById(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected PageDTO<VillageFinancialInstitutionProfDTO> postHookGetAll(PageDTO<VillageFinancialInstitutionProfDTO> result) {
        return result;
    }




}
