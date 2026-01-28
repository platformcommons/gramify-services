package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.PurposeOfCreditService;


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

public class PurposeOfCreditServiceImpl implements PurposeOfCreditService {

    private final PurposeOfCreditDTOAssembler assembler;
    private final PurposeOfCreditRepository repository;


    public PurposeOfCreditServiceImpl(
        PurposeOfCreditRepository repository, PurposeOfCreditDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new PurposeOfCredit.
     *
     * @param PurposeOfCredit the PurposeOfCredit DTO to save
     * @return the saved PurposeOfCredit DTO
     */
    @Transactional
    @Override
    public PurposeOfCreditDTO save(PurposeOfCreditDTO PurposeOfCredit) {
        log.debug("Entry - save(PurposeOfCredit={})", PurposeOfCredit);
        PurposeOfCredit = preHookSave(PurposeOfCredit);
        PurposeOfCredit entity = assembler.fromDTO(PurposeOfCredit);
        PurposeOfCreditDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing PurposeOfCredit.
     *
     * @param PurposeOfCredit the PurposeOfCredit DTO to update
     * @return the updated PurposeOfCredit DTO
     * @throws NotFoundException if the PurposeOfCredit is not found
     */
    @Transactional
    @Override
    public PurposeOfCreditDTO update(PurposeOfCreditDTO PurposeOfCredit) {
        log.debug("Entry - update(PurposeOfCredit={})", PurposeOfCredit);
        PurposeOfCredit = preHookUpdate(PurposeOfCredit);
        PurposeOfCredit saved = repository.findById(PurposeOfCredit.getId()).orElseThrow(() -> new NotFoundException("PurposeOfCredit not found"));
        saved.update(assembler.fromDTO(PurposeOfCredit));
        saved = repository.save(saved);
        PurposeOfCreditDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of PurposeOfCredits.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing PurposeOfCredit DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<PurposeOfCreditDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<PurposeOfCredit> result = repository.findAll(PageRequest.of(page, size));
        Set<PurposeOfCreditDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<PurposeOfCreditDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a PurposeOfCredit by ID.
     *
     * @param id     the ID of the PurposeOfCredit to delete
     * @param reason the reason for deletion
     * @return the deleted PurposeOfCredit DTO
     * @throws NotFoundException if the PurposeOfCredit is not found
     */
    @Transactional
    @Override
    public PurposeOfCreditDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        PurposeOfCredit saved = repository.findById(id).orElseThrow(() -> new NotFoundException("PurposeOfCredit not found"));
        saved.deactivate(reason);
        repository.save(saved);
        PurposeOfCreditDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a PurposeOfCredit by ID.
     *
     * @param id the ID of the PurposeOfCredit to retrieve
     * @return the PurposeOfCredit DTO
     * @throws NotFoundException if the PurposeOfCredit is not found
     */
    @Transactional(readOnly = true)
    @Override
    public PurposeOfCreditDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        PurposeOfCredit saved = repository.findById(id).orElseThrow(() -> new NotFoundException("PurposeOfCredit not found"));
        PurposeOfCreditDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<PurposeOfCreditDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<PurposeOfCredit> savedData = repository.findAllById(ids);
        Set<PurposeOfCreditDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected PurposeOfCreditDTO postHookSave(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PurposeOfCreditDTO preHookSave(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PurposeOfCreditDTO postHookUpdate(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PurposeOfCreditDTO preHookUpdate(PurposeOfCreditDTO PurposeOfCreditDTO) {
        return PurposeOfCreditDTO;
    }

    protected PurposeOfCreditDTO postHookDelete(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected PurposeOfCreditDTO postHookGetById(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PageDTO<PurposeOfCreditDTO> postHookGetAll(PageDTO<PurposeOfCreditDTO> result) {
        return result;
    }




}
