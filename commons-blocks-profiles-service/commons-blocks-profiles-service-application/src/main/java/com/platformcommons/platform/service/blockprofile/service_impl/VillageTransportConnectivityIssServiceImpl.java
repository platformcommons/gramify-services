package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageTransportConnectivityIssService;


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

public class VillageTransportConnectivityIssServiceImpl implements VillageTransportConnectivityIssService {

    private final VillageTransportConnectivityIssDTOAssembler assembler;
    private final VillageTransportConnectivityIssRepository repository;


    public VillageTransportConnectivityIssServiceImpl(
        VillageTransportConnectivityIssRepository repository, VillageTransportConnectivityIssDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageTransportConnectivityIss.
     *
     * @param VillageTransportConnectivityIss the VillageTransportConnectivityIss DTO to save
     * @return the saved VillageTransportConnectivityIss DTO
     */
    @Transactional
    @Override
    public VillageTransportConnectivityIssDTO save(VillageTransportConnectivityIssDTO VillageTransportConnectivityIss) {
        log.debug("Entry - save(VillageTransportConnectivityIss={})", VillageTransportConnectivityIss);
        VillageTransportConnectivityIss = preHookSave(VillageTransportConnectivityIss);
        VillageTransportConnectivityIss entity = assembler.fromDTO(VillageTransportConnectivityIss);
        VillageTransportConnectivityIssDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageTransportConnectivityIss.
     *
     * @param VillageTransportConnectivityIss the VillageTransportConnectivityIss DTO to update
     * @return the updated VillageTransportConnectivityIss DTO
     * @throws NotFoundException if the VillageTransportConnectivityIss is not found
     */
    @Transactional
    @Override
    public VillageTransportConnectivityIssDTO update(VillageTransportConnectivityIssDTO VillageTransportConnectivityIss) {
        log.debug("Entry - update(VillageTransportConnectivityIss={})", VillageTransportConnectivityIss);
        VillageTransportConnectivityIss = preHookUpdate(VillageTransportConnectivityIss);
        VillageTransportConnectivityIss saved = repository.findById(VillageTransportConnectivityIss.getId()).orElseThrow(() -> new NotFoundException("VillageTransportConnectivityIss not found"));
        saved.update(assembler.fromDTO(VillageTransportConnectivityIss));
        saved = repository.save(saved);
        VillageTransportConnectivityIssDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageTransportConnectivityIsss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageTransportConnectivityIss DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageTransportConnectivityIssDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageTransportConnectivityIss> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageTransportConnectivityIssDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageTransportConnectivityIssDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageTransportConnectivityIss by ID.
     *
     * @param id     the ID of the VillageTransportConnectivityIss to delete
     * @param reason the reason for deletion
     * @return the deleted VillageTransportConnectivityIss DTO
     * @throws NotFoundException if the VillageTransportConnectivityIss is not found
     */
    @Transactional
    @Override
    public VillageTransportConnectivityIssDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageTransportConnectivityIss saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageTransportConnectivityIss not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageTransportConnectivityIssDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageTransportConnectivityIss by ID.
     *
     * @param id the ID of the VillageTransportConnectivityIss to retrieve
     * @return the VillageTransportConnectivityIss DTO
     * @throws NotFoundException if the VillageTransportConnectivityIss is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageTransportConnectivityIssDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageTransportConnectivityIss saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageTransportConnectivityIss not found"));
        VillageTransportConnectivityIssDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageTransportConnectivityIssDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageTransportConnectivityIss> savedData = repository.findAllById(ids);
        Set<VillageTransportConnectivityIssDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageTransportConnectivityIssDTO postHookSave(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected VillageTransportConnectivityIssDTO preHookSave(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected VillageTransportConnectivityIssDTO postHookUpdate(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected VillageTransportConnectivityIssDTO preHookUpdate(VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO) {
        return VillageTransportConnectivityIssDTO;
    }

    protected VillageTransportConnectivityIssDTO postHookDelete(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageTransportConnectivityIssDTO postHookGetById(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected PageDTO<VillageTransportConnectivityIssDTO> postHookGetAll(PageDTO<VillageTransportConnectivityIssDTO> result) {
        return result;
    }




}
