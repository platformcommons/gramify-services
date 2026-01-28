package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageWaterSupplyGapService;


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

public class VillageWaterSupplyGapServiceImpl implements VillageWaterSupplyGapService {

    private final VillageWaterSupplyGapDTOAssembler assembler;
    private final VillageWaterSupplyGapRepository repository;


    public VillageWaterSupplyGapServiceImpl(
        VillageWaterSupplyGapRepository repository, VillageWaterSupplyGapDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageWaterSupplyGap.
     *
     * @param VillageWaterSupplyGap the VillageWaterSupplyGap DTO to save
     * @return the saved VillageWaterSupplyGap DTO
     */
    @Transactional
    @Override
    public VillageWaterSupplyGapDTO save(VillageWaterSupplyGapDTO VillageWaterSupplyGap) {
        log.debug("Entry - save(VillageWaterSupplyGap={})", VillageWaterSupplyGap);
        VillageWaterSupplyGap = preHookSave(VillageWaterSupplyGap);
        VillageWaterSupplyGap entity = assembler.fromDTO(VillageWaterSupplyGap);
        VillageWaterSupplyGapDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageWaterSupplyGap.
     *
     * @param VillageWaterSupplyGap the VillageWaterSupplyGap DTO to update
     * @return the updated VillageWaterSupplyGap DTO
     * @throws NotFoundException if the VillageWaterSupplyGap is not found
     */
    @Transactional
    @Override
    public VillageWaterSupplyGapDTO update(VillageWaterSupplyGapDTO VillageWaterSupplyGap) {
        log.debug("Entry - update(VillageWaterSupplyGap={})", VillageWaterSupplyGap);
        VillageWaterSupplyGap = preHookUpdate(VillageWaterSupplyGap);
        VillageWaterSupplyGap saved = repository.findById(VillageWaterSupplyGap.getId()).orElseThrow(() -> new NotFoundException("VillageWaterSupplyGap not found"));
        saved.update(assembler.fromDTO(VillageWaterSupplyGap));
        saved = repository.save(saved);
        VillageWaterSupplyGapDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageWaterSupplyGaps.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageWaterSupplyGap DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageWaterSupplyGapDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageWaterSupplyGap> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageWaterSupplyGapDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageWaterSupplyGapDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageWaterSupplyGap by ID.
     *
     * @param id     the ID of the VillageWaterSupplyGap to delete
     * @param reason the reason for deletion
     * @return the deleted VillageWaterSupplyGap DTO
     * @throws NotFoundException if the VillageWaterSupplyGap is not found
     */
    @Transactional
    @Override
    public VillageWaterSupplyGapDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageWaterSupplyGap saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageWaterSupplyGap not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageWaterSupplyGapDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageWaterSupplyGap by ID.
     *
     * @param id the ID of the VillageWaterSupplyGap to retrieve
     * @return the VillageWaterSupplyGap DTO
     * @throws NotFoundException if the VillageWaterSupplyGap is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageWaterSupplyGapDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageWaterSupplyGap saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageWaterSupplyGap not found"));
        VillageWaterSupplyGapDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageWaterSupplyGapDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageWaterSupplyGap> savedData = repository.findAllById(ids);
        Set<VillageWaterSupplyGapDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageWaterSupplyGapDTO postHookSave(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected VillageWaterSupplyGapDTO preHookSave(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected VillageWaterSupplyGapDTO postHookUpdate(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected VillageWaterSupplyGapDTO preHookUpdate(VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO) {
        return VillageWaterSupplyGapDTO;
    }

    protected VillageWaterSupplyGapDTO postHookDelete(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageWaterSupplyGapDTO postHookGetById(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected PageDTO<VillageWaterSupplyGapDTO> postHookGetAll(PageDTO<VillageWaterSupplyGapDTO> result) {
        return result;
    }




}
