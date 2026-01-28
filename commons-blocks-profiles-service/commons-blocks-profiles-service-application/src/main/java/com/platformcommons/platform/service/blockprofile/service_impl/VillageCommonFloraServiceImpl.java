package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageCommonFloraService;


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

public class VillageCommonFloraServiceImpl implements VillageCommonFloraService {

    private final VillageCommonFloraDTOAssembler assembler;
    private final VillageCommonFloraRepository repository;


    public VillageCommonFloraServiceImpl(
        VillageCommonFloraRepository repository, VillageCommonFloraDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageCommonFlora.
     *
     * @param VillageCommonFlora the VillageCommonFlora DTO to save
     * @return the saved VillageCommonFlora DTO
     */
    @Transactional
    @Override
    public VillageCommonFloraDTO save(VillageCommonFloraDTO VillageCommonFlora) {
        log.debug("Entry - save(VillageCommonFlora={})", VillageCommonFlora);
        VillageCommonFlora = preHookSave(VillageCommonFlora);
        VillageCommonFlora entity = assembler.fromDTO(VillageCommonFlora);
        VillageCommonFloraDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageCommonFlora.
     *
     * @param VillageCommonFlora the VillageCommonFlora DTO to update
     * @return the updated VillageCommonFlora DTO
     * @throws NotFoundException if the VillageCommonFlora is not found
     */
    @Transactional
    @Override
    public VillageCommonFloraDTO update(VillageCommonFloraDTO VillageCommonFlora) {
        log.debug("Entry - update(VillageCommonFlora={})", VillageCommonFlora);
        VillageCommonFlora = preHookUpdate(VillageCommonFlora);
        VillageCommonFlora saved = repository.findById(VillageCommonFlora.getId()).orElseThrow(() -> new NotFoundException("VillageCommonFlora not found"));
        saved.update(assembler.fromDTO(VillageCommonFlora));
        saved = repository.save(saved);
        VillageCommonFloraDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageCommonFloras.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageCommonFlora DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageCommonFloraDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageCommonFlora> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageCommonFloraDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageCommonFloraDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageCommonFlora by ID.
     *
     * @param id     the ID of the VillageCommonFlora to delete
     * @param reason the reason for deletion
     * @return the deleted VillageCommonFlora DTO
     * @throws NotFoundException if the VillageCommonFlora is not found
     */
    @Transactional
    @Override
    public VillageCommonFloraDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageCommonFlora saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommonFlora not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageCommonFloraDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageCommonFlora by ID.
     *
     * @param id the ID of the VillageCommonFlora to retrieve
     * @return the VillageCommonFlora DTO
     * @throws NotFoundException if the VillageCommonFlora is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageCommonFloraDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommonFlora saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommonFlora not found"));
        VillageCommonFloraDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageCommonFloraDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageCommonFlora> savedData = repository.findAllById(ids);
        Set<VillageCommonFloraDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommonFloraDTO postHookSave(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected VillageCommonFloraDTO preHookSave(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected VillageCommonFloraDTO postHookUpdate(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected VillageCommonFloraDTO preHookUpdate(VillageCommonFloraDTO VillageCommonFloraDTO) {
        return VillageCommonFloraDTO;
    }

    protected VillageCommonFloraDTO postHookDelete(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommonFloraDTO postHookGetById(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommonFloraDTO> postHookGetAll(PageDTO<VillageCommonFloraDTO> result) {
        return result;
    }




}
