package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.CommonRepairNeedService;


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

public class CommonRepairNeedServiceImpl implements CommonRepairNeedService {

    private final CommonRepairNeedDTOAssembler assembler;
    private final CommonRepairNeedRepository repository;


    public CommonRepairNeedServiceImpl(
        CommonRepairNeedRepository repository, CommonRepairNeedDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new CommonRepairNeed.
     *
     * @param CommonRepairNeed the CommonRepairNeed DTO to save
     * @return the saved CommonRepairNeed DTO
     */
    @Transactional
    @Override
    public CommonRepairNeedDTO save(CommonRepairNeedDTO CommonRepairNeed) {
        log.debug("Entry - save(CommonRepairNeed={})", CommonRepairNeed);
        CommonRepairNeed = preHookSave(CommonRepairNeed);
        CommonRepairNeed entity = assembler.fromDTO(CommonRepairNeed);
        CommonRepairNeedDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing CommonRepairNeed.
     *
     * @param CommonRepairNeed the CommonRepairNeed DTO to update
     * @return the updated CommonRepairNeed DTO
     * @throws NotFoundException if the CommonRepairNeed is not found
     */
    @Transactional
    @Override
    public CommonRepairNeedDTO update(CommonRepairNeedDTO CommonRepairNeed) {
        log.debug("Entry - update(CommonRepairNeed={})", CommonRepairNeed);
        CommonRepairNeed = preHookUpdate(CommonRepairNeed);
        CommonRepairNeed saved = repository.findById(CommonRepairNeed.getId()).orElseThrow(() -> new NotFoundException("CommonRepairNeed not found"));
        saved.update(assembler.fromDTO(CommonRepairNeed));
        saved = repository.save(saved);
        CommonRepairNeedDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of CommonRepairNeeds.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing CommonRepairNeed DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CommonRepairNeedDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<CommonRepairNeed> result = repository.findAll(PageRequest.of(page, size));
        Set<CommonRepairNeedDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<CommonRepairNeedDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a CommonRepairNeed by ID.
     *
     * @param id     the ID of the CommonRepairNeed to delete
     * @param reason the reason for deletion
     * @return the deleted CommonRepairNeed DTO
     * @throws NotFoundException if the CommonRepairNeed is not found
     */
    @Transactional
    @Override
    public CommonRepairNeedDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        CommonRepairNeed saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CommonRepairNeed not found"));
        saved.deactivate(reason);
        repository.save(saved);
        CommonRepairNeedDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a CommonRepairNeed by ID.
     *
     * @param id the ID of the CommonRepairNeed to retrieve
     * @return the CommonRepairNeed DTO
     * @throws NotFoundException if the CommonRepairNeed is not found
     */
    @Transactional(readOnly = true)
    @Override
    public CommonRepairNeedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        CommonRepairNeed saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CommonRepairNeed not found"));
        CommonRepairNeedDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<CommonRepairNeedDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<CommonRepairNeed> savedData = repository.findAllById(ids);
        Set<CommonRepairNeedDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CommonRepairNeedDTO postHookSave(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected CommonRepairNeedDTO preHookSave(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected CommonRepairNeedDTO postHookUpdate(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected CommonRepairNeedDTO preHookUpdate(CommonRepairNeedDTO CommonRepairNeedDTO) {
        return CommonRepairNeedDTO;
    }

    protected CommonRepairNeedDTO postHookDelete(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CommonRepairNeedDTO postHookGetById(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected PageDTO<CommonRepairNeedDTO> postHookGetAll(PageDTO<CommonRepairNeedDTO> result) {
        return result;
    }




}
