package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.OtherIndustryTypeService;


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

public class OtherIndustryTypeServiceImpl implements OtherIndustryTypeService {

    private final OtherIndustryTypeDTOAssembler assembler;
    private final OtherIndustryTypeRepository repository;


    public OtherIndustryTypeServiceImpl(
        OtherIndustryTypeRepository repository, OtherIndustryTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new OtherIndustryType.
     *
     * @param OtherIndustryType the OtherIndustryType DTO to save
     * @return the saved OtherIndustryType DTO
     */
    @Transactional
    @Override
    public OtherIndustryTypeDTO save(OtherIndustryTypeDTO OtherIndustryType) {
        log.debug("Entry - save(OtherIndustryType={})", OtherIndustryType);
        OtherIndustryType = preHookSave(OtherIndustryType);
        OtherIndustryType entity = assembler.fromDTO(OtherIndustryType);
        OtherIndustryTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing OtherIndustryType.
     *
     * @param OtherIndustryType the OtherIndustryType DTO to update
     * @return the updated OtherIndustryType DTO
     * @throws NotFoundException if the OtherIndustryType is not found
     */
    @Transactional
    @Override
    public OtherIndustryTypeDTO update(OtherIndustryTypeDTO OtherIndustryType) {
        log.debug("Entry - update(OtherIndustryType={})", OtherIndustryType);
        OtherIndustryType = preHookUpdate(OtherIndustryType);
        OtherIndustryType saved = repository.findById(OtherIndustryType.getId()).orElseThrow(() -> new NotFoundException("OtherIndustryType not found"));
        saved.update(assembler.fromDTO(OtherIndustryType));
        saved = repository.save(saved);
        OtherIndustryTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of OtherIndustryTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing OtherIndustryType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<OtherIndustryTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<OtherIndustryType> result = repository.findAll(PageRequest.of(page, size));
        Set<OtherIndustryTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<OtherIndustryTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a OtherIndustryType by ID.
     *
     * @param id     the ID of the OtherIndustryType to delete
     * @param reason the reason for deletion
     * @return the deleted OtherIndustryType DTO
     * @throws NotFoundException if the OtherIndustryType is not found
     */
    @Transactional
    @Override
    public OtherIndustryTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        OtherIndustryType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("OtherIndustryType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        OtherIndustryTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a OtherIndustryType by ID.
     *
     * @param id the ID of the OtherIndustryType to retrieve
     * @return the OtherIndustryType DTO
     * @throws NotFoundException if the OtherIndustryType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public OtherIndustryTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        OtherIndustryType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("OtherIndustryType not found"));
        OtherIndustryTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<OtherIndustryTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<OtherIndustryType> savedData = repository.findAllById(ids);
        Set<OtherIndustryTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected OtherIndustryTypeDTO postHookSave(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected OtherIndustryTypeDTO preHookSave(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected OtherIndustryTypeDTO postHookUpdate(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected OtherIndustryTypeDTO preHookUpdate(OtherIndustryTypeDTO OtherIndustryTypeDTO) {
        return OtherIndustryTypeDTO;
    }

    protected OtherIndustryTypeDTO postHookDelete(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected OtherIndustryTypeDTO postHookGetById(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected PageDTO<OtherIndustryTypeDTO> postHookGetAll(PageDTO<OtherIndustryTypeDTO> result) {
        return result;
    }




}
