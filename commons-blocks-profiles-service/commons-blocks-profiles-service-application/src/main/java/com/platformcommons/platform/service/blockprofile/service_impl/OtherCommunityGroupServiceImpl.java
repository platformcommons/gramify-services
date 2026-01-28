package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.OtherCommunityGroupService;


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

public class OtherCommunityGroupServiceImpl implements OtherCommunityGroupService {

    private final OtherCommunityGroupDTOAssembler assembler;
    private final OtherCommunityGroupRepository repository;


    public OtherCommunityGroupServiceImpl(
        OtherCommunityGroupRepository repository, OtherCommunityGroupDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new OtherCommunityGroup.
     *
     * @param OtherCommunityGroup the OtherCommunityGroup DTO to save
     * @return the saved OtherCommunityGroup DTO
     */
    @Transactional
    @Override
    public OtherCommunityGroupDTO save(OtherCommunityGroupDTO OtherCommunityGroup) {
        log.debug("Entry - save(OtherCommunityGroup={})", OtherCommunityGroup);
        OtherCommunityGroup = preHookSave(OtherCommunityGroup);
        OtherCommunityGroup entity = assembler.fromDTO(OtherCommunityGroup);
        OtherCommunityGroupDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing OtherCommunityGroup.
     *
     * @param OtherCommunityGroup the OtherCommunityGroup DTO to update
     * @return the updated OtherCommunityGroup DTO
     * @throws NotFoundException if the OtherCommunityGroup is not found
     */
    @Transactional
    @Override
    public OtherCommunityGroupDTO update(OtherCommunityGroupDTO OtherCommunityGroup) {
        log.debug("Entry - update(OtherCommunityGroup={})", OtherCommunityGroup);
        OtherCommunityGroup = preHookUpdate(OtherCommunityGroup);
        OtherCommunityGroup saved = repository.findById(OtherCommunityGroup.getId()).orElseThrow(() -> new NotFoundException("OtherCommunityGroup not found"));
        saved.update(assembler.fromDTO(OtherCommunityGroup));
        saved = repository.save(saved);
        OtherCommunityGroupDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of OtherCommunityGroups.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing OtherCommunityGroup DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<OtherCommunityGroupDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<OtherCommunityGroup> result = repository.findAll(PageRequest.of(page, size));
        Set<OtherCommunityGroupDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<OtherCommunityGroupDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a OtherCommunityGroup by ID.
     *
     * @param id     the ID of the OtherCommunityGroup to delete
     * @param reason the reason for deletion
     * @return the deleted OtherCommunityGroup DTO
     * @throws NotFoundException if the OtherCommunityGroup is not found
     */
    @Transactional
    @Override
    public OtherCommunityGroupDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        OtherCommunityGroup saved = repository.findById(id).orElseThrow(() -> new NotFoundException("OtherCommunityGroup not found"));
        saved.deactivate(reason);
        repository.save(saved);
        OtherCommunityGroupDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a OtherCommunityGroup by ID.
     *
     * @param id the ID of the OtherCommunityGroup to retrieve
     * @return the OtherCommunityGroup DTO
     * @throws NotFoundException if the OtherCommunityGroup is not found
     */
    @Transactional(readOnly = true)
    @Override
    public OtherCommunityGroupDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        OtherCommunityGroup saved = repository.findById(id).orElseThrow(() -> new NotFoundException("OtherCommunityGroup not found"));
        OtherCommunityGroupDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<OtherCommunityGroupDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<OtherCommunityGroup> savedData = repository.findAllById(ids);
        Set<OtherCommunityGroupDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected OtherCommunityGroupDTO postHookSave(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected OtherCommunityGroupDTO preHookSave(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected OtherCommunityGroupDTO postHookUpdate(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected OtherCommunityGroupDTO preHookUpdate(OtherCommunityGroupDTO OtherCommunityGroupDTO) {
        return OtherCommunityGroupDTO;
    }

    protected OtherCommunityGroupDTO postHookDelete(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected OtherCommunityGroupDTO postHookGetById(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected PageDTO<OtherCommunityGroupDTO> postHookGetAll(PageDTO<OtherCommunityGroupDTO> result) {
        return result;
    }




}
