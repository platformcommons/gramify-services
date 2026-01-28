package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ExportPotentialNicheProductBuyeService;


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

public class ExportPotentialNicheProductBuyeServiceImpl implements ExportPotentialNicheProductBuyeService {

    private final ExportPotentialNicheProductBuyeDTOAssembler assembler;
    private final ExportPotentialNicheProductBuyeRepository repository;


    public ExportPotentialNicheProductBuyeServiceImpl(
        ExportPotentialNicheProductBuyeRepository repository, ExportPotentialNicheProductBuyeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ExportPotentialNicheProductBuye.
     *
     * @param ExportPotentialNicheProductBuye the ExportPotentialNicheProductBuye DTO to save
     * @return the saved ExportPotentialNicheProductBuye DTO
     */
    @Transactional
    @Override
    public ExportPotentialNicheProductBuyeDTO save(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuye) {
        log.debug("Entry - save(ExportPotentialNicheProductBuye={})", ExportPotentialNicheProductBuye);
        ExportPotentialNicheProductBuye = preHookSave(ExportPotentialNicheProductBuye);
        ExportPotentialNicheProductBuye entity = assembler.fromDTO(ExportPotentialNicheProductBuye);
        ExportPotentialNicheProductBuyeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ExportPotentialNicheProductBuye.
     *
     * @param ExportPotentialNicheProductBuye the ExportPotentialNicheProductBuye DTO to update
     * @return the updated ExportPotentialNicheProductBuye DTO
     * @throws NotFoundException if the ExportPotentialNicheProductBuye is not found
     */
    @Transactional
    @Override
    public ExportPotentialNicheProductBuyeDTO update(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuye) {
        log.debug("Entry - update(ExportPotentialNicheProductBuye={})", ExportPotentialNicheProductBuye);
        ExportPotentialNicheProductBuye = preHookUpdate(ExportPotentialNicheProductBuye);
        ExportPotentialNicheProductBuye saved = repository.findById(ExportPotentialNicheProductBuye.getId()).orElseThrow(() -> new NotFoundException("ExportPotentialNicheProductBuye not found"));
        saved.update(assembler.fromDTO(ExportPotentialNicheProductBuye));
        saved = repository.save(saved);
        ExportPotentialNicheProductBuyeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ExportPotentialNicheProductBuyes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ExportPotentialNicheProductBuye DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ExportPotentialNicheProductBuyeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ExportPotentialNicheProductBuye> result = repository.findAll(PageRequest.of(page, size));
        Set<ExportPotentialNicheProductBuyeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ExportPotentialNicheProductBuyeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ExportPotentialNicheProductBuye by ID.
     *
     * @param id     the ID of the ExportPotentialNicheProductBuye to delete
     * @param reason the reason for deletion
     * @return the deleted ExportPotentialNicheProductBuye DTO
     * @throws NotFoundException if the ExportPotentialNicheProductBuye is not found
     */
    @Transactional
    @Override
    public ExportPotentialNicheProductBuyeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ExportPotentialNicheProductBuye saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ExportPotentialNicheProductBuye not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ExportPotentialNicheProductBuyeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ExportPotentialNicheProductBuye by ID.
     *
     * @param id the ID of the ExportPotentialNicheProductBuye to retrieve
     * @return the ExportPotentialNicheProductBuye DTO
     * @throws NotFoundException if the ExportPotentialNicheProductBuye is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ExportPotentialNicheProductBuyeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ExportPotentialNicheProductBuye saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ExportPotentialNicheProductBuye not found"));
        ExportPotentialNicheProductBuyeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ExportPotentialNicheProductBuyeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ExportPotentialNicheProductBuye> savedData = repository.findAllById(ids);
        Set<ExportPotentialNicheProductBuyeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ExportPotentialNicheProductBuyeDTO postHookSave(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected ExportPotentialNicheProductBuyeDTO preHookSave(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected ExportPotentialNicheProductBuyeDTO postHookUpdate(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected ExportPotentialNicheProductBuyeDTO preHookUpdate(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO) {
        return ExportPotentialNicheProductBuyeDTO;
    }

    protected ExportPotentialNicheProductBuyeDTO postHookDelete(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ExportPotentialNicheProductBuyeDTO postHookGetById(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected PageDTO<ExportPotentialNicheProductBuyeDTO> postHookGetAll(PageDTO<ExportPotentialNicheProductBuyeDTO> result) {
        return result;
    }




}
