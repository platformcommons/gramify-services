package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.IssuesInHigherEducationAccessService;


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

public class IssuesInHigherEducationAccessServiceImpl implements IssuesInHigherEducationAccessService {

    private final IssuesInHigherEducationAccessDTOAssembler assembler;
    private final IssuesInHigherEducationAccessRepository repository;


    public IssuesInHigherEducationAccessServiceImpl(
        IssuesInHigherEducationAccessRepository repository, IssuesInHigherEducationAccessDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new IssuesInHigherEducationAccess.
     *
     * @param IssuesInHigherEducationAccess the IssuesInHigherEducationAccess DTO to save
     * @return the saved IssuesInHigherEducationAccess DTO
     */
    @Transactional
    @Override
    public IssuesInHigherEducationAccessDTO save(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccess) {
        log.debug("Entry - save(IssuesInHigherEducationAccess={})", IssuesInHigherEducationAccess);
        IssuesInHigherEducationAccess = preHookSave(IssuesInHigherEducationAccess);
        IssuesInHigherEducationAccess entity = assembler.fromDTO(IssuesInHigherEducationAccess);
        IssuesInHigherEducationAccessDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing IssuesInHigherEducationAccess.
     *
     * @param IssuesInHigherEducationAccess the IssuesInHigherEducationAccess DTO to update
     * @return the updated IssuesInHigherEducationAccess DTO
     * @throws NotFoundException if the IssuesInHigherEducationAccess is not found
     */
    @Transactional
    @Override
    public IssuesInHigherEducationAccessDTO update(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccess) {
        log.debug("Entry - update(IssuesInHigherEducationAccess={})", IssuesInHigherEducationAccess);
        IssuesInHigherEducationAccess = preHookUpdate(IssuesInHigherEducationAccess);
        IssuesInHigherEducationAccess saved = repository.findById(IssuesInHigherEducationAccess.getId()).orElseThrow(() -> new NotFoundException("IssuesInHigherEducationAccess not found"));
        saved.update(assembler.fromDTO(IssuesInHigherEducationAccess));
        saved = repository.save(saved);
        IssuesInHigherEducationAccessDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of IssuesInHigherEducationAccesss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing IssuesInHigherEducationAccess DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<IssuesInHigherEducationAccessDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<IssuesInHigherEducationAccess> result = repository.findAll(PageRequest.of(page, size));
        Set<IssuesInHigherEducationAccessDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<IssuesInHigherEducationAccessDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a IssuesInHigherEducationAccess by ID.
     *
     * @param id     the ID of the IssuesInHigherEducationAccess to delete
     * @param reason the reason for deletion
     * @return the deleted IssuesInHigherEducationAccess DTO
     * @throws NotFoundException if the IssuesInHigherEducationAccess is not found
     */
    @Transactional
    @Override
    public IssuesInHigherEducationAccessDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        IssuesInHigherEducationAccess saved = repository.findById(id).orElseThrow(() -> new NotFoundException("IssuesInHigherEducationAccess not found"));
        saved.deactivate(reason);
        repository.save(saved);
        IssuesInHigherEducationAccessDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a IssuesInHigherEducationAccess by ID.
     *
     * @param id the ID of the IssuesInHigherEducationAccess to retrieve
     * @return the IssuesInHigherEducationAccess DTO
     * @throws NotFoundException if the IssuesInHigherEducationAccess is not found
     */
    @Transactional(readOnly = true)
    @Override
    public IssuesInHigherEducationAccessDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        IssuesInHigherEducationAccess saved = repository.findById(id).orElseThrow(() -> new NotFoundException("IssuesInHigherEducationAccess not found"));
        IssuesInHigherEducationAccessDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<IssuesInHigherEducationAccessDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<IssuesInHigherEducationAccess> savedData = repository.findAllById(ids);
        Set<IssuesInHigherEducationAccessDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected IssuesInHigherEducationAccessDTO postHookSave(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected IssuesInHigherEducationAccessDTO preHookSave(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected IssuesInHigherEducationAccessDTO postHookUpdate(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected IssuesInHigherEducationAccessDTO preHookUpdate(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO) {
        return IssuesInHigherEducationAccessDTO;
    }

    protected IssuesInHigherEducationAccessDTO postHookDelete(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected IssuesInHigherEducationAccessDTO postHookGetById(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected PageDTO<IssuesInHigherEducationAccessDTO> postHookGetAll(PageDTO<IssuesInHigherEducationAccessDTO> result) {
        return result;
    }




}
