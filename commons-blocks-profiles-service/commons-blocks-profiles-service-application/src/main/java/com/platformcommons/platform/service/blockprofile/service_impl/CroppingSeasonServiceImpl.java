package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.CroppingSeasonService;


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

public class CroppingSeasonServiceImpl implements CroppingSeasonService {

    private final CroppingSeasonDTOAssembler assembler;
    private final CroppingSeasonRepository repository;


    public CroppingSeasonServiceImpl(
        CroppingSeasonRepository repository, CroppingSeasonDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new CroppingSeason.
     *
     * @param CroppingSeason the CroppingSeason DTO to save
     * @return the saved CroppingSeason DTO
     */
    @Transactional
    @Override
    public CroppingSeasonDTO save(CroppingSeasonDTO CroppingSeason) {
        log.debug("Entry - save(CroppingSeason={})", CroppingSeason);
        CroppingSeason = preHookSave(CroppingSeason);
        CroppingSeason entity = assembler.fromDTO(CroppingSeason);
        CroppingSeasonDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing CroppingSeason.
     *
     * @param CroppingSeason the CroppingSeason DTO to update
     * @return the updated CroppingSeason DTO
     * @throws NotFoundException if the CroppingSeason is not found
     */
    @Transactional
    @Override
    public CroppingSeasonDTO update(CroppingSeasonDTO CroppingSeason) {
        log.debug("Entry - update(CroppingSeason={})", CroppingSeason);
        CroppingSeason = preHookUpdate(CroppingSeason);
        CroppingSeason saved = repository.findById(CroppingSeason.getId()).orElseThrow(() -> new NotFoundException("CroppingSeason not found"));
        saved.update(assembler.fromDTO(CroppingSeason));
        saved = repository.save(saved);
        CroppingSeasonDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of CroppingSeasons.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing CroppingSeason DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CroppingSeasonDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<CroppingSeason> result = repository.findAll(PageRequest.of(page, size));
        Set<CroppingSeasonDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<CroppingSeasonDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a CroppingSeason by ID.
     *
     * @param id     the ID of the CroppingSeason to delete
     * @param reason the reason for deletion
     * @return the deleted CroppingSeason DTO
     * @throws NotFoundException if the CroppingSeason is not found
     */
    @Transactional
    @Override
    public CroppingSeasonDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        CroppingSeason saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CroppingSeason not found"));
        saved.deactivate(reason);
        repository.save(saved);
        CroppingSeasonDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a CroppingSeason by ID.
     *
     * @param id the ID of the CroppingSeason to retrieve
     * @return the CroppingSeason DTO
     * @throws NotFoundException if the CroppingSeason is not found
     */
    @Transactional(readOnly = true)
    @Override
    public CroppingSeasonDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        CroppingSeason saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CroppingSeason not found"));
        CroppingSeasonDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<CroppingSeasonDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<CroppingSeason> savedData = repository.findAllById(ids);
        Set<CroppingSeasonDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CroppingSeasonDTO postHookSave(CroppingSeasonDTO dto) {
        return dto;
    }

    protected CroppingSeasonDTO preHookSave(CroppingSeasonDTO dto) {
        return dto;
    }

    protected CroppingSeasonDTO postHookUpdate(CroppingSeasonDTO dto) {
        return dto;
    }

    protected CroppingSeasonDTO preHookUpdate(CroppingSeasonDTO CroppingSeasonDTO) {
        return CroppingSeasonDTO;
    }

    protected CroppingSeasonDTO postHookDelete(CroppingSeasonDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CroppingSeasonDTO postHookGetById(CroppingSeasonDTO dto) {
        return dto;
    }

    protected PageDTO<CroppingSeasonDTO> postHookGetAll(PageDTO<CroppingSeasonDTO> result) {
        return result;
    }




}
