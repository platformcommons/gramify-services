package com.platformcommons.platform.service.blockprofile.facade_ext.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.impl.*;
import com.platformcommons.platform.service.blockprofile.facade_ext.*;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MainSurplusMarketsOutsideBlockProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MainSurplusMarketsOutsideBlockServiceExt;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;


@Slf4j
@Component

public class MainSurplusMarketsOutsideBlockFacadeExtImpl extends MainSurplusMarketsOutsideBlockFacadeImpl implements MainSurplusMarketsOutsideBlockFacadeExt {


    /**
     * Constructs a new MainSurplusMarketsOutsideBlockFacadeExtImpl.
     *
     * @param serviceExt   the MainSurplusMarketsOutsideBlock service extension
     * @param producer  the MainSurplusMarketsOutsideBlock producer
     * @param evaluator the policy evaluator
     */
    public MainSurplusMarketsOutsideBlockFacadeExtImpl(MainSurplusMarketsOutsideBlockServiceExt serviceExt, MainSurplusMarketsOutsideBlockProducer producer, PolicyEvaluator evaluator) {
        super(serviceExt, producer, evaluator);
    }


    /**
     * Pre-processing hook for save operation.
     *
     * @param dto the MainSurplusMarketsOutsideBlock DTO before save
     * @return the processed MainSurplusMarketsOutsideBlock DTO
     */
    @Override
    protected MainSurplusMarketsOutsideBlockDTO preHookSave(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry - preHookSave(dto={})", dto);
        MainSurplusMarketsOutsideBlockDTO result = super.preHookSave(dto);
        log.debug("Exit - preHookSave() with result: {}", result);
        return super.preHookSave(result);
    }

    /**
     * Post-processing hook for save operation.
     *
     * @param dto the MainSurplusMarketsOutsideBlock DTO after save
     * @return the processed MainSurplusMarketsOutsideBlock DTO
     */
    @Override
    protected MainSurplusMarketsOutsideBlockDTO postHookSave(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry - postHookSave(dto={})", dto);

        log.debug("Exit - postHookSave() with result: {}", dto);
        return super.postHookSave(dto);
    }

    /**
     * Pre-processing hook for update operation.
     *
     * @param dto the MainSurplusMarketsOutsideBlock DTO before update
     * @return the processed MainSurplusMarketsOutsideBlock DTO
     */
    @Override
    protected MainSurplusMarketsOutsideBlockDTO preHookUpdate(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry - preHookUpdate(dto={})", dto);

        log.debug("Exit - preHookUpdate() with result: {}", dto);
        return super.preHookUpdate(dto);
    }


    /**
     * Post-processing hook for update operation.
     *
     * @param dto the MainSurplusMarketsOutsideBlock DTO after update
     * @return the processed MainSurplusMarketsOutsideBlock DTO
     */
    @Override
    protected MainSurplusMarketsOutsideBlockDTO postHookUpdate(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry - postHookUpdate(dto={})", dto);

        log.debug("Exit - postHookUpdate() with result: {}", dto);
        return super.postHookUpdate(dto);
    }


    /**
     * Pre-processing hook for delete operation.
     *
     * @param id     the MainSurplusMarketsOutsideBlock ID
     * @param reason the reason for deletion
     */
    @Override
    protected void preHookDelete(Long id, String reason) {
        log.debug("Entry - preHookDelete(id={}, reason={})", id, reason);

        log.debug("Exit - preHookDelete()");
        super.preHookDelete(id, reason);
    }

    /**
     * Post-processing hook for delete operation.
     *
     * @param dto the MainSurplusMarketsOutsideBlock DTO after delete
     * @return the processed MainSurplusMarketsOutsideBlock DTO
     */
    @Override
    protected MainSurplusMarketsOutsideBlockDTO postHookDelete(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry - postHookDelete(dto={})", dto);

        log.debug("Exit - postHookDelete() with result: {}", dto);
        return super.postHookDelete(dto);
    }



    /**
     * Post-processing hook for getById operation.
     *
     * @param dto the MainSurplusMarketsOutsideBlock DTO after retrieval
     * @return the processed MainSurplusMarketsOutsideBlock DTO
     */
    @Override
    protected MainSurplusMarketsOutsideBlockDTO postHookGetById(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry - postHookGetById(dto={})", dto);

        log.debug("Exit - postHookGetById() with result: {}", dto);
        return super.postHookGetById(dto);
    }

    /**
     * Post-processing hook for getAll operation.
     *
     * @param result the page of MainSurplusMarketsOutsideBlock DTOs after retrieval
     * @return the processed page of MainSurplusMarketsOutsideBlock DTOs
     */
    @Override
    protected PageDTO<MainSurplusMarketsOutsideBlockDTO> postHookGetAll(PageDTO<MainSurplusMarketsOutsideBlockDTO> result) {
        log.debug("Entry - postHookGetAll(result={})", result);

        log.debug("Exit - postHookGetAll() with result: {}", result);
        return super.postHookGetAll(result);
    }
}
