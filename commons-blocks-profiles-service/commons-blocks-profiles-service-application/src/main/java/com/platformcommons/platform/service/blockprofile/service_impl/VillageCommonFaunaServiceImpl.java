package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageCommonFaunaService;


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

public class VillageCommonFaunaServiceImpl implements VillageCommonFaunaService {

    private final VillageCommonFaunaDTOAssembler assembler;
    private final VillageCommonFaunaRepository repository;


    public VillageCommonFaunaServiceImpl(
        VillageCommonFaunaRepository repository, VillageCommonFaunaDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageCommonFauna.
     *
     * @param VillageCommonFauna the VillageCommonFauna DTO to save
     * @return the saved VillageCommonFauna DTO
     */
    @Transactional
    @Override
    public VillageCommonFaunaDTO save(VillageCommonFaunaDTO VillageCommonFauna) {
        log.debug("Entry - save(VillageCommonFauna={})", VillageCommonFauna);
        VillageCommonFauna = preHookSave(VillageCommonFauna);
        VillageCommonFauna entity = assembler.fromDTO(VillageCommonFauna);
        VillageCommonFaunaDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageCommonFauna.
     *
     * @param VillageCommonFauna the VillageCommonFauna DTO to update
     * @return the updated VillageCommonFauna DTO
     * @throws NotFoundException if the VillageCommonFauna is not found
     */
    @Transactional
    @Override
    public VillageCommonFaunaDTO update(VillageCommonFaunaDTO VillageCommonFauna) {
        log.debug("Entry - update(VillageCommonFauna={})", VillageCommonFauna);
        VillageCommonFauna = preHookUpdate(VillageCommonFauna);
        VillageCommonFauna saved = repository.findById(VillageCommonFauna.getId()).orElseThrow(() -> new NotFoundException("VillageCommonFauna not found"));
        saved.update(assembler.fromDTO(VillageCommonFauna));
        saved = repository.save(saved);
        VillageCommonFaunaDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageCommonFaunas.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageCommonFauna DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageCommonFaunaDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageCommonFauna> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageCommonFaunaDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageCommonFaunaDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageCommonFauna by ID.
     *
     * @param id     the ID of the VillageCommonFauna to delete
     * @param reason the reason for deletion
     * @return the deleted VillageCommonFauna DTO
     * @throws NotFoundException if the VillageCommonFauna is not found
     */
    @Transactional
    @Override
    public VillageCommonFaunaDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageCommonFauna saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommonFauna not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageCommonFaunaDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageCommonFauna by ID.
     *
     * @param id the ID of the VillageCommonFauna to retrieve
     * @return the VillageCommonFauna DTO
     * @throws NotFoundException if the VillageCommonFauna is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageCommonFaunaDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommonFauna saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommonFauna not found"));
        VillageCommonFaunaDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageCommonFaunaDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageCommonFauna> savedData = repository.findAllById(ids);
        Set<VillageCommonFaunaDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommonFaunaDTO postHookSave(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected VillageCommonFaunaDTO preHookSave(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected VillageCommonFaunaDTO postHookUpdate(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected VillageCommonFaunaDTO preHookUpdate(VillageCommonFaunaDTO VillageCommonFaunaDTO) {
        return VillageCommonFaunaDTO;
    }

    protected VillageCommonFaunaDTO postHookDelete(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommonFaunaDTO postHookGetById(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommonFaunaDTO> postHookGetAll(PageDTO<VillageCommonFaunaDTO> result) {
        return result;
    }




}
