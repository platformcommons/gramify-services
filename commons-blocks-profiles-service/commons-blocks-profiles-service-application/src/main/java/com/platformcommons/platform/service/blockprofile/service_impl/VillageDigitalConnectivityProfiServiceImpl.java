package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageDigitalConnectivityProfiService;


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

public class VillageDigitalConnectivityProfiServiceImpl implements VillageDigitalConnectivityProfiService {

    private final VillageDigitalConnectivityProfiDTOAssembler assembler;
    private final VillageDigitalConnectivityProfiRepository repository;


    public VillageDigitalConnectivityProfiServiceImpl(
        VillageDigitalConnectivityProfiRepository repository, VillageDigitalConnectivityProfiDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageDigitalConnectivityProfi.
     *
     * @param VillageDigitalConnectivityProfi the VillageDigitalConnectivityProfi DTO to save
     * @return the saved VillageDigitalConnectivityProfi DTO
     */
    @Transactional
    @Override
    public VillageDigitalConnectivityProfiDTO save(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfi) {
        log.debug("Entry - save(VillageDigitalConnectivityProfi={})", VillageDigitalConnectivityProfi);
        VillageDigitalConnectivityProfi = preHookSave(VillageDigitalConnectivityProfi);
        VillageDigitalConnectivityProfi entity = assembler.fromDTO(VillageDigitalConnectivityProfi);
        VillageDigitalConnectivityProfiDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageDigitalConnectivityProfi.
     *
     * @param VillageDigitalConnectivityProfi the VillageDigitalConnectivityProfi DTO to update
     * @return the updated VillageDigitalConnectivityProfi DTO
     * @throws NotFoundException if the VillageDigitalConnectivityProfi is not found
     */
    @Transactional
    @Override
    public VillageDigitalConnectivityProfiDTO update(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfi) {
        log.debug("Entry - update(VillageDigitalConnectivityProfi={})", VillageDigitalConnectivityProfi);
        VillageDigitalConnectivityProfi = preHookUpdate(VillageDigitalConnectivityProfi);
        VillageDigitalConnectivityProfi saved = repository.findById(VillageDigitalConnectivityProfi.getId()).orElseThrow(() -> new NotFoundException("VillageDigitalConnectivityProfi not found"));
        saved.update(assembler.fromDTO(VillageDigitalConnectivityProfi));
        saved = repository.save(saved);
        VillageDigitalConnectivityProfiDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageDigitalConnectivityProfis.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageDigitalConnectivityProfi DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageDigitalConnectivityProfiDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageDigitalConnectivityProfi> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageDigitalConnectivityProfiDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageDigitalConnectivityProfiDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageDigitalConnectivityProfi by ID.
     *
     * @param id     the ID of the VillageDigitalConnectivityProfi to delete
     * @param reason the reason for deletion
     * @return the deleted VillageDigitalConnectivityProfi DTO
     * @throws NotFoundException if the VillageDigitalConnectivityProfi is not found
     */
    @Transactional
    @Override
    public VillageDigitalConnectivityProfiDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageDigitalConnectivityProfi saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageDigitalConnectivityProfi not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageDigitalConnectivityProfiDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageDigitalConnectivityProfi by ID.
     *
     * @param id the ID of the VillageDigitalConnectivityProfi to retrieve
     * @return the VillageDigitalConnectivityProfi DTO
     * @throws NotFoundException if the VillageDigitalConnectivityProfi is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageDigitalConnectivityProfiDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageDigitalConnectivityProfi saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageDigitalConnectivityProfi not found"));
        VillageDigitalConnectivityProfiDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageDigitalConnectivityProfiDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageDigitalConnectivityProfi> savedData = repository.findAllById(ids);
        Set<VillageDigitalConnectivityProfiDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageDigitalConnectivityProfiDTO postHookSave(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected VillageDigitalConnectivityProfiDTO preHookSave(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected VillageDigitalConnectivityProfiDTO postHookUpdate(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected VillageDigitalConnectivityProfiDTO preHookUpdate(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO) {
        return VillageDigitalConnectivityProfiDTO;
    }

    protected VillageDigitalConnectivityProfiDTO postHookDelete(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageDigitalConnectivityProfiDTO postHookGetById(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected PageDTO<VillageDigitalConnectivityProfiDTO> postHookGetAll(PageDTO<VillageDigitalConnectivityProfiDTO> result) {
        return result;
    }




}
