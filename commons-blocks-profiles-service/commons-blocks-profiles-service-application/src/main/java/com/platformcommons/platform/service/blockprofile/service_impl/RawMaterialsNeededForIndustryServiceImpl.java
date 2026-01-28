package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.RawMaterialsNeededForIndustryService;


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

public class RawMaterialsNeededForIndustryServiceImpl implements RawMaterialsNeededForIndustryService {

    private final RawMaterialsNeededForIndustryDTOAssembler assembler;
    private final RawMaterialsNeededForIndustryRepository repository;


    public RawMaterialsNeededForIndustryServiceImpl(
        RawMaterialsNeededForIndustryRepository repository, RawMaterialsNeededForIndustryDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new RawMaterialsNeededForIndustry.
     *
     * @param RawMaterialsNeededForIndustry the RawMaterialsNeededForIndustry DTO to save
     * @return the saved RawMaterialsNeededForIndustry DTO
     */
    @Transactional
    @Override
    public RawMaterialsNeededForIndustryDTO save(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustry) {
        log.debug("Entry - save(RawMaterialsNeededForIndustry={})", RawMaterialsNeededForIndustry);
        RawMaterialsNeededForIndustry = preHookSave(RawMaterialsNeededForIndustry);
        RawMaterialsNeededForIndustry entity = assembler.fromDTO(RawMaterialsNeededForIndustry);
        RawMaterialsNeededForIndustryDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing RawMaterialsNeededForIndustry.
     *
     * @param RawMaterialsNeededForIndustry the RawMaterialsNeededForIndustry DTO to update
     * @return the updated RawMaterialsNeededForIndustry DTO
     * @throws NotFoundException if the RawMaterialsNeededForIndustry is not found
     */
    @Transactional
    @Override
    public RawMaterialsNeededForIndustryDTO update(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustry) {
        log.debug("Entry - update(RawMaterialsNeededForIndustry={})", RawMaterialsNeededForIndustry);
        RawMaterialsNeededForIndustry = preHookUpdate(RawMaterialsNeededForIndustry);
        RawMaterialsNeededForIndustry saved = repository.findById(RawMaterialsNeededForIndustry.getId()).orElseThrow(() -> new NotFoundException("RawMaterialsNeededForIndustry not found"));
        saved.update(assembler.fromDTO(RawMaterialsNeededForIndustry));
        saved = repository.save(saved);
        RawMaterialsNeededForIndustryDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of RawMaterialsNeededForIndustrys.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing RawMaterialsNeededForIndustry DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<RawMaterialsNeededForIndustryDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<RawMaterialsNeededForIndustry> result = repository.findAll(PageRequest.of(page, size));
        Set<RawMaterialsNeededForIndustryDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<RawMaterialsNeededForIndustryDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a RawMaterialsNeededForIndustry by ID.
     *
     * @param id     the ID of the RawMaterialsNeededForIndustry to delete
     * @param reason the reason for deletion
     * @return the deleted RawMaterialsNeededForIndustry DTO
     * @throws NotFoundException if the RawMaterialsNeededForIndustry is not found
     */
    @Transactional
    @Override
    public RawMaterialsNeededForIndustryDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        RawMaterialsNeededForIndustry saved = repository.findById(id).orElseThrow(() -> new NotFoundException("RawMaterialsNeededForIndustry not found"));
        saved.deactivate(reason);
        repository.save(saved);
        RawMaterialsNeededForIndustryDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a RawMaterialsNeededForIndustry by ID.
     *
     * @param id the ID of the RawMaterialsNeededForIndustry to retrieve
     * @return the RawMaterialsNeededForIndustry DTO
     * @throws NotFoundException if the RawMaterialsNeededForIndustry is not found
     */
    @Transactional(readOnly = true)
    @Override
    public RawMaterialsNeededForIndustryDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        RawMaterialsNeededForIndustry saved = repository.findById(id).orElseThrow(() -> new NotFoundException("RawMaterialsNeededForIndustry not found"));
        RawMaterialsNeededForIndustryDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<RawMaterialsNeededForIndustryDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<RawMaterialsNeededForIndustry> savedData = repository.findAllById(ids);
        Set<RawMaterialsNeededForIndustryDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected RawMaterialsNeededForIndustryDTO postHookSave(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected RawMaterialsNeededForIndustryDTO preHookSave(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected RawMaterialsNeededForIndustryDTO postHookUpdate(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected RawMaterialsNeededForIndustryDTO preHookUpdate(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO) {
        return RawMaterialsNeededForIndustryDTO;
    }

    protected RawMaterialsNeededForIndustryDTO postHookDelete(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected RawMaterialsNeededForIndustryDTO postHookGetById(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected PageDTO<RawMaterialsNeededForIndustryDTO> postHookGetAll(PageDTO<RawMaterialsNeededForIndustryDTO> result) {
        return result;
    }




}
