package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageSocialStructureProfileService;


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

public class VillageSocialStructureProfileServiceImpl implements VillageSocialStructureProfileService {

    private final VillageSocialStructureProfileDTOAssembler assembler;
    private final VillageSocialStructureProfileRepository repository;


    private final OtherCommunityGroupDTOAssembler assemblerOtherCommunityGroup;
    private final OtherCommunityGroupRepository repositoryOtherCommunityGroup;


    public VillageSocialStructureProfileServiceImpl(
        OtherCommunityGroupDTOAssembler assemblerOtherCommunityGroup,  OtherCommunityGroupRepository repositoryOtherCommunityGroup,
        VillageSocialStructureProfileRepository repository, VillageSocialStructureProfileDTOAssembler assembler) {
        this.assemblerOtherCommunityGroup = assemblerOtherCommunityGroup;
        this.repositoryOtherCommunityGroup = repositoryOtherCommunityGroup;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageSocialStructureProfile.
     *
     * @param VillageSocialStructureProfile the VillageSocialStructureProfile DTO to save
     * @return the saved VillageSocialStructureProfile DTO
     */
    @Transactional
    @Override
    public VillageSocialStructureProfileDTO save(VillageSocialStructureProfileDTO VillageSocialStructureProfile) {
        log.debug("Entry - save(VillageSocialStructureProfile={})", VillageSocialStructureProfile);
        VillageSocialStructureProfile = preHookSave(VillageSocialStructureProfile);
        VillageSocialStructureProfile entity = assembler.fromDTO(VillageSocialStructureProfile);
        VillageSocialStructureProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageSocialStructureProfile.
     *
     * @param VillageSocialStructureProfile the VillageSocialStructureProfile DTO to update
     * @return the updated VillageSocialStructureProfile DTO
     * @throws NotFoundException if the VillageSocialStructureProfile is not found
     */
    @Transactional
    @Override
    public VillageSocialStructureProfileDTO update(VillageSocialStructureProfileDTO VillageSocialStructureProfile) {
        log.debug("Entry - update(VillageSocialStructureProfile={})", VillageSocialStructureProfile);
        VillageSocialStructureProfile = preHookUpdate(VillageSocialStructureProfile);
        VillageSocialStructureProfile saved = repository.findById(VillageSocialStructureProfile.getId()).orElseThrow(() -> new NotFoundException("VillageSocialStructureProfile not found"));
        saved.update(assembler.fromDTO(VillageSocialStructureProfile));
        saved = repository.save(saved);
        VillageSocialStructureProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageSocialStructureProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageSocialStructureProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageSocialStructureProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageSocialStructureProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageSocialStructureProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageSocialStructureProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageSocialStructureProfile by ID.
     *
     * @param id     the ID of the VillageSocialStructureProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageSocialStructureProfile DTO
     * @throws NotFoundException if the VillageSocialStructureProfile is not found
     */
    @Transactional
    @Override
    public VillageSocialStructureProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageSocialStructureProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSocialStructureProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageSocialStructureProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageSocialStructureProfile by ID.
     *
     * @param id the ID of the VillageSocialStructureProfile to retrieve
     * @return the VillageSocialStructureProfile DTO
     * @throws NotFoundException if the VillageSocialStructureProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageSocialStructureProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageSocialStructureProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSocialStructureProfile not found"));
        VillageSocialStructureProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageSocialStructureProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageSocialStructureProfile> savedData = repository.findAllById(ids);
        Set<VillageSocialStructureProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of otherCommunityGroupList to a VillageSocialStructureProfile identified by their ID.
      *
      * @param id               The ID of the VillageSocialStructureProfile to add hobbies to
      * @param otherCommunityGroupList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addOtherCommunityGroupToVillageSocialStructureProfile(Long id, List<OtherCommunityGroupDTO> otherCommunityGroupList){
         VillageSocialStructureProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSocialStructureProfile not found"));
         if(otherCommunityGroupList != null && !otherCommunityGroupList.isEmpty()) {

             Set<OtherCommunityGroup> toBeSavedList = otherCommunityGroupList.stream().map(it-> assemblerOtherCommunityGroup.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageSocialStructureProfile(saved));
             repositoryOtherCommunityGroup.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageSocialStructureProfileDTO postHookSave(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected VillageSocialStructureProfileDTO preHookSave(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected VillageSocialStructureProfileDTO postHookUpdate(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected VillageSocialStructureProfileDTO preHookUpdate(VillageSocialStructureProfileDTO VillageSocialStructureProfileDTO) {
        return VillageSocialStructureProfileDTO;
    }

    protected VillageSocialStructureProfileDTO postHookDelete(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSocialStructureProfileDTO postHookGetById(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSocialStructureProfileDTO> postHookGetAll(PageDTO<VillageSocialStructureProfileDTO> result) {
        return result;
    }




}
