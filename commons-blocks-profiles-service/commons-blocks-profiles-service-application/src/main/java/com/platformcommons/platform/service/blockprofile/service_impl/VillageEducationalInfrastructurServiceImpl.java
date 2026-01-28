package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageEducationalInfrastructurService;


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

public class VillageEducationalInfrastructurServiceImpl implements VillageEducationalInfrastructurService {

    private final VillageEducationalInfrastructurDTOAssembler assembler;
    private final VillageEducationalInfrastructurRepository repository;


    public VillageEducationalInfrastructurServiceImpl(
        VillageEducationalInfrastructurRepository repository, VillageEducationalInfrastructurDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageEducationalInfrastructur.
     *
     * @param VillageEducationalInfrastructur the VillageEducationalInfrastructur DTO to save
     * @return the saved VillageEducationalInfrastructur DTO
     */
    @Transactional
    @Override
    public VillageEducationalInfrastructurDTO save(VillageEducationalInfrastructurDTO VillageEducationalInfrastructur) {
        log.debug("Entry - save(VillageEducationalInfrastructur={})", VillageEducationalInfrastructur);
        VillageEducationalInfrastructur = preHookSave(VillageEducationalInfrastructur);
        VillageEducationalInfrastructur entity = assembler.fromDTO(VillageEducationalInfrastructur);
        VillageEducationalInfrastructurDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageEducationalInfrastructur.
     *
     * @param VillageEducationalInfrastructur the VillageEducationalInfrastructur DTO to update
     * @return the updated VillageEducationalInfrastructur DTO
     * @throws NotFoundException if the VillageEducationalInfrastructur is not found
     */
    @Transactional
    @Override
    public VillageEducationalInfrastructurDTO update(VillageEducationalInfrastructurDTO VillageEducationalInfrastructur) {
        log.debug("Entry - update(VillageEducationalInfrastructur={})", VillageEducationalInfrastructur);
        VillageEducationalInfrastructur = preHookUpdate(VillageEducationalInfrastructur);
        VillageEducationalInfrastructur saved = repository.findById(VillageEducationalInfrastructur.getId()).orElseThrow(() -> new NotFoundException("VillageEducationalInfrastructur not found"));
        saved.update(assembler.fromDTO(VillageEducationalInfrastructur));
        saved = repository.save(saved);
        VillageEducationalInfrastructurDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageEducationalInfrastructurs.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageEducationalInfrastructur DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageEducationalInfrastructurDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageEducationalInfrastructur> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageEducationalInfrastructurDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageEducationalInfrastructurDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageEducationalInfrastructur by ID.
     *
     * @param id     the ID of the VillageEducationalInfrastructur to delete
     * @param reason the reason for deletion
     * @return the deleted VillageEducationalInfrastructur DTO
     * @throws NotFoundException if the VillageEducationalInfrastructur is not found
     */
    @Transactional
    @Override
    public VillageEducationalInfrastructurDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageEducationalInfrastructur saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageEducationalInfrastructur not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageEducationalInfrastructurDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageEducationalInfrastructur by ID.
     *
     * @param id the ID of the VillageEducationalInfrastructur to retrieve
     * @return the VillageEducationalInfrastructur DTO
     * @throws NotFoundException if the VillageEducationalInfrastructur is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageEducationalInfrastructurDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageEducationalInfrastructur saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageEducationalInfrastructur not found"));
        VillageEducationalInfrastructurDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageEducationalInfrastructurDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageEducationalInfrastructur> savedData = repository.findAllById(ids);
        Set<VillageEducationalInfrastructurDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageEducationalInfrastructurDTO postHookSave(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected VillageEducationalInfrastructurDTO preHookSave(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected VillageEducationalInfrastructurDTO postHookUpdate(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected VillageEducationalInfrastructurDTO preHookUpdate(VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO) {
        return VillageEducationalInfrastructurDTO;
    }

    protected VillageEducationalInfrastructurDTO postHookDelete(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageEducationalInfrastructurDTO postHookGetById(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected PageDTO<VillageEducationalInfrastructurDTO> postHookGetAll(PageDTO<VillageEducationalInfrastructurDTO> result) {
        return result;
    }




}
