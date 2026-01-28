package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.CentralSchemeListService;


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

public class CentralSchemeListServiceImpl implements CentralSchemeListService {

    private final CentralSchemeListDTOAssembler assembler;
    private final CentralSchemeListRepository repository;


    public CentralSchemeListServiceImpl(
        CentralSchemeListRepository repository, CentralSchemeListDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new CentralSchemeList.
     *
     * @param CentralSchemeList the CentralSchemeList DTO to save
     * @return the saved CentralSchemeList DTO
     */
    @Transactional
    @Override
    public CentralSchemeListDTO save(CentralSchemeListDTO CentralSchemeList) {
        log.debug("Entry - save(CentralSchemeList={})", CentralSchemeList);
        CentralSchemeList = preHookSave(CentralSchemeList);
        CentralSchemeList entity = assembler.fromDTO(CentralSchemeList);
        CentralSchemeListDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing CentralSchemeList.
     *
     * @param CentralSchemeList the CentralSchemeList DTO to update
     * @return the updated CentralSchemeList DTO
     * @throws NotFoundException if the CentralSchemeList is not found
     */
    @Transactional
    @Override
    public CentralSchemeListDTO update(CentralSchemeListDTO CentralSchemeList) {
        log.debug("Entry - update(CentralSchemeList={})", CentralSchemeList);
        CentralSchemeList = preHookUpdate(CentralSchemeList);
        CentralSchemeList saved = repository.findById(CentralSchemeList.getId()).orElseThrow(() -> new NotFoundException("CentralSchemeList not found"));
        saved.update(assembler.fromDTO(CentralSchemeList));
        saved = repository.save(saved);
        CentralSchemeListDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of CentralSchemeLists.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing CentralSchemeList DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CentralSchemeListDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<CentralSchemeList> result = repository.findAll(PageRequest.of(page, size));
        Set<CentralSchemeListDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<CentralSchemeListDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a CentralSchemeList by ID.
     *
     * @param id     the ID of the CentralSchemeList to delete
     * @param reason the reason for deletion
     * @return the deleted CentralSchemeList DTO
     * @throws NotFoundException if the CentralSchemeList is not found
     */
    @Transactional
    @Override
    public CentralSchemeListDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        CentralSchemeList saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CentralSchemeList not found"));
        saved.deactivate(reason);
        repository.save(saved);
        CentralSchemeListDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a CentralSchemeList by ID.
     *
     * @param id the ID of the CentralSchemeList to retrieve
     * @return the CentralSchemeList DTO
     * @throws NotFoundException if the CentralSchemeList is not found
     */
    @Transactional(readOnly = true)
    @Override
    public CentralSchemeListDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        CentralSchemeList saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CentralSchemeList not found"));
        CentralSchemeListDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<CentralSchemeListDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<CentralSchemeList> savedData = repository.findAllById(ids);
        Set<CentralSchemeListDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CentralSchemeListDTO postHookSave(CentralSchemeListDTO dto) {
        return dto;
    }

    protected CentralSchemeListDTO preHookSave(CentralSchemeListDTO dto) {
        return dto;
    }

    protected CentralSchemeListDTO postHookUpdate(CentralSchemeListDTO dto) {
        return dto;
    }

    protected CentralSchemeListDTO preHookUpdate(CentralSchemeListDTO CentralSchemeListDTO) {
        return CentralSchemeListDTO;
    }

    protected CentralSchemeListDTO postHookDelete(CentralSchemeListDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CentralSchemeListDTO postHookGetById(CentralSchemeListDTO dto) {
        return dto;
    }

    protected PageDTO<CentralSchemeListDTO> postHookGetAll(PageDTO<CentralSchemeListDTO> result) {
        return result;
    }




}
