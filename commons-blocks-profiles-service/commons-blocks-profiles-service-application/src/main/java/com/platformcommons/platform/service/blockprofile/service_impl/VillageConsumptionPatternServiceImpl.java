package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageConsumptionPatternService;


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

public class VillageConsumptionPatternServiceImpl implements VillageConsumptionPatternService {

    private final VillageConsumptionPatternDTOAssembler assembler;
    private final VillageConsumptionPatternRepository repository;


    private final CommonConsumerGoodsPurchasedDTOAssembler assemblerCommonConsumerGoodsPurchased;
    private final CommonConsumerGoodsPurchasedRepository repositoryCommonConsumerGoodsPurchased;


    private final ItemsUsuallyBoughtFromOutsideDTOAssembler assemblerItemsUsuallyBoughtFromOutside;
    private final ItemsUsuallyBoughtFromOutsideRepository repositoryItemsUsuallyBoughtFromOutside;


    private final StapleFoodsConsumedDTOAssembler assemblerStapleFoodsConsumed;
    private final StapleFoodsConsumedRepository repositoryStapleFoodsConsumed;


    private final ItemsUsuallyBoughtLocallyDTOAssembler assemblerItemsUsuallyBoughtLocally;
    private final ItemsUsuallyBoughtLocallyRepository repositoryItemsUsuallyBoughtLocally;


    public VillageConsumptionPatternServiceImpl(
        CommonConsumerGoodsPurchasedDTOAssembler assemblerCommonConsumerGoodsPurchased,  CommonConsumerGoodsPurchasedRepository repositoryCommonConsumerGoodsPurchased,
        ItemsUsuallyBoughtFromOutsideDTOAssembler assemblerItemsUsuallyBoughtFromOutside,  ItemsUsuallyBoughtFromOutsideRepository repositoryItemsUsuallyBoughtFromOutside,
        StapleFoodsConsumedDTOAssembler assemblerStapleFoodsConsumed,  StapleFoodsConsumedRepository repositoryStapleFoodsConsumed,
        ItemsUsuallyBoughtLocallyDTOAssembler assemblerItemsUsuallyBoughtLocally,  ItemsUsuallyBoughtLocallyRepository repositoryItemsUsuallyBoughtLocally,
        VillageConsumptionPatternRepository repository, VillageConsumptionPatternDTOAssembler assembler) {
        this.assemblerCommonConsumerGoodsPurchased = assemblerCommonConsumerGoodsPurchased;
        this.repositoryCommonConsumerGoodsPurchased = repositoryCommonConsumerGoodsPurchased;
        this.assemblerItemsUsuallyBoughtFromOutside = assemblerItemsUsuallyBoughtFromOutside;
        this.repositoryItemsUsuallyBoughtFromOutside = repositoryItemsUsuallyBoughtFromOutside;
        this.assemblerStapleFoodsConsumed = assemblerStapleFoodsConsumed;
        this.repositoryStapleFoodsConsumed = repositoryStapleFoodsConsumed;
        this.assemblerItemsUsuallyBoughtLocally = assemblerItemsUsuallyBoughtLocally;
        this.repositoryItemsUsuallyBoughtLocally = repositoryItemsUsuallyBoughtLocally;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageConsumptionPattern.
     *
     * @param VillageConsumptionPattern the VillageConsumptionPattern DTO to save
     * @return the saved VillageConsumptionPattern DTO
     */
    @Transactional
    @Override
    public VillageConsumptionPatternDTO save(VillageConsumptionPatternDTO VillageConsumptionPattern) {
        log.debug("Entry - save(VillageConsumptionPattern={})", VillageConsumptionPattern);
        VillageConsumptionPattern = preHookSave(VillageConsumptionPattern);
        VillageConsumptionPattern entity = assembler.fromDTO(VillageConsumptionPattern);
        VillageConsumptionPatternDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageConsumptionPattern.
     *
     * @param VillageConsumptionPattern the VillageConsumptionPattern DTO to update
     * @return the updated VillageConsumptionPattern DTO
     * @throws NotFoundException if the VillageConsumptionPattern is not found
     */
    @Transactional
    @Override
    public VillageConsumptionPatternDTO update(VillageConsumptionPatternDTO VillageConsumptionPattern) {
        log.debug("Entry - update(VillageConsumptionPattern={})", VillageConsumptionPattern);
        VillageConsumptionPattern = preHookUpdate(VillageConsumptionPattern);
        VillageConsumptionPattern saved = repository.findById(VillageConsumptionPattern.getId()).orElseThrow(() -> new NotFoundException("VillageConsumptionPattern not found"));
        saved.update(assembler.fromDTO(VillageConsumptionPattern));
        saved = repository.save(saved);
        VillageConsumptionPatternDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageConsumptionPatterns.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageConsumptionPattern DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageConsumptionPatternDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageConsumptionPattern> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageConsumptionPatternDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageConsumptionPatternDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageConsumptionPattern by ID.
     *
     * @param id     the ID of the VillageConsumptionPattern to delete
     * @param reason the reason for deletion
     * @return the deleted VillageConsumptionPattern DTO
     * @throws NotFoundException if the VillageConsumptionPattern is not found
     */
    @Transactional
    @Override
    public VillageConsumptionPatternDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageConsumptionPattern saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageConsumptionPattern not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageConsumptionPatternDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageConsumptionPattern by ID.
     *
     * @param id the ID of the VillageConsumptionPattern to retrieve
     * @return the VillageConsumptionPattern DTO
     * @throws NotFoundException if the VillageConsumptionPattern is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageConsumptionPatternDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageConsumptionPattern saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageConsumptionPattern not found"));
        VillageConsumptionPatternDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageConsumptionPatternDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageConsumptionPattern> savedData = repository.findAllById(ids);
        Set<VillageConsumptionPatternDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of commonConsumerGoodsPurchasedList to a VillageConsumptionPattern identified by their ID.
      *
      * @param id               The ID of the VillageConsumptionPattern to add hobbies to
      * @param commonConsumerGoodsPurchasedList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addCommonConsumerGoodsPurchasedToVillageConsumptionPattern(Long id, List<CommonConsumerGoodsPurchasedDTO> commonConsumerGoodsPurchasedList){
         VillageConsumptionPattern saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageConsumptionPattern not found"));
         if(commonConsumerGoodsPurchasedList != null && !commonConsumerGoodsPurchasedList.isEmpty()) {

             Set<CommonConsumerGoodsPurchased> toBeSavedList = commonConsumerGoodsPurchasedList.stream().map(it-> assemblerCommonConsumerGoodsPurchased.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageConsumptionPattern(saved));
             repositoryCommonConsumerGoodsPurchased.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of itemsUsuallyBoughtFromOutsideList to a VillageConsumptionPattern identified by their ID.
      *
      * @param id               The ID of the VillageConsumptionPattern to add hobbies to
      * @param itemsUsuallyBoughtFromOutsideList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addItemsUsuallyBoughtFromOutsideToVillageConsumptionPattern(Long id, List<ItemsUsuallyBoughtFromOutsideDTO> itemsUsuallyBoughtFromOutsideList){
         VillageConsumptionPattern saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageConsumptionPattern not found"));
         if(itemsUsuallyBoughtFromOutsideList != null && !itemsUsuallyBoughtFromOutsideList.isEmpty()) {

             Set<ItemsUsuallyBoughtFromOutside> toBeSavedList = itemsUsuallyBoughtFromOutsideList.stream().map(it-> assemblerItemsUsuallyBoughtFromOutside.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageConsumptionPattern(saved));
             repositoryItemsUsuallyBoughtFromOutside.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of stapleFoodsConsumedList to a VillageConsumptionPattern identified by their ID.
      *
      * @param id               The ID of the VillageConsumptionPattern to add hobbies to
      * @param stapleFoodsConsumedList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addStapleFoodsConsumedToVillageConsumptionPattern(Long id, List<StapleFoodsConsumedDTO> stapleFoodsConsumedList){
         VillageConsumptionPattern saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageConsumptionPattern not found"));
         if(stapleFoodsConsumedList != null && !stapleFoodsConsumedList.isEmpty()) {

             Set<StapleFoodsConsumed> toBeSavedList = stapleFoodsConsumedList.stream().map(it-> assemblerStapleFoodsConsumed.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageConsumptionPattern(saved));
             repositoryStapleFoodsConsumed.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of itemsUsuallyBoughtLocallyList to a VillageConsumptionPattern identified by their ID.
      *
      * @param id               The ID of the VillageConsumptionPattern to add hobbies to
      * @param itemsUsuallyBoughtLocallyList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addItemsUsuallyBoughtLocallyToVillageConsumptionPattern(Long id, List<ItemsUsuallyBoughtLocallyDTO> itemsUsuallyBoughtLocallyList){
         VillageConsumptionPattern saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageConsumptionPattern not found"));
         if(itemsUsuallyBoughtLocallyList != null && !itemsUsuallyBoughtLocallyList.isEmpty()) {

             Set<ItemsUsuallyBoughtLocally> toBeSavedList = itemsUsuallyBoughtLocallyList.stream().map(it-> assemblerItemsUsuallyBoughtLocally.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageConsumptionPattern(saved));
             repositoryItemsUsuallyBoughtLocally.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageConsumptionPatternDTO postHookSave(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected VillageConsumptionPatternDTO preHookSave(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected VillageConsumptionPatternDTO postHookUpdate(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected VillageConsumptionPatternDTO preHookUpdate(VillageConsumptionPatternDTO VillageConsumptionPatternDTO) {
        return VillageConsumptionPatternDTO;
    }

    protected VillageConsumptionPatternDTO postHookDelete(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageConsumptionPatternDTO postHookGetById(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected PageDTO<VillageConsumptionPatternDTO> postHookGetAll(PageDTO<VillageConsumptionPatternDTO> result) {
        return result;
    }




}
