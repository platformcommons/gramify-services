package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.application.TenantMetaConfigService;
import com.platformcommons.platform.service.iam.application.TenantService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.utility.OBOSecurityUtil;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import com.platformcommons.platform.service.iam.application.utility.TenantOnBoardUtil;
import com.platformcommons.platform.service.iam.application.utility.VMSSignupUtil;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.TenantMetaAdditionalProperty;
import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import com.platformcommons.platform.service.iam.facade.assembler.TenantMetaAdditionalPropertyDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.TenantMetaConfigDTOAssembler;
import com.platformcommons.platform.service.iam.facade.cache.TenantMetaConfigCacheManager;
import com.platformcommons.platform.service.utility.JPAUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class TenantMetaConfigFacadeImpl implements TenantMetaConfigFacade{

    @Autowired
    private TenantMetaConfigService service;

    @Autowired
    private TenantMetaAdditionalPropertyDTOAssembler tenantMetaAdditionalPropertyDTOAssembler;

    @Autowired
    private TenantMetaConfigDTOAssembler assembler;

    @Autowired(required = false)
    private TenantMetaConfigCacheManager tenantMetaConfigCacheManager;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantOnBoardUtil tenantOnBoardUtil;

    @Autowired
    private OBOSecurityUtil oboSecurityUtil;

    private final VMSSignupUtil vmsSignupUtil;

    @Override
    public TenantMetaConfigDTO getTenantMetaConfig(Long tenantId, String tenantLogin) {
        return assembler.toDTO(service.getTenantMetaConfig(tenantId,tenantLogin));
    }

    @Override
    public Optional<TenantMetaConfigDTO> getTenantMetaConfigOptional(Long tenantId, String tenantLogin) {
        Optional<TenantMetaConfigDTO> tenantMetaConfigDTOOptional = Optional.empty();
        Optional<TenantMetaConfig> tenantMetaConfigOptional = service.getTenantMetaConfigOptional(tenantId,tenantLogin);
        if(tenantMetaConfigOptional.isPresent()) {
            TenantMetaConfig tenantMetaConfig = tenantMetaConfigOptional.get();
            tenantMetaConfigDTOOptional = Optional.of(assembler.toDTO(tenantMetaConfig));
        }
        return tenantMetaConfigDTOOptional;
    }


    @Override
    public void addTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalPropertyDTO> additionalPropertyDTOS) {
        Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet = additionalPropertyDTOS.stream()
                .map(tenantMetaAdditionalPropertyDTOAssembler::fromDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        service.addTenantMetaAdditionalProperty(tenantLogin,tenantMetaAdditionalPropertySet);
        if(tenantMetaConfigCacheManager != null) {
            tenantMetaConfigCacheManager.clearCache(tenantLogin);
        }
    }

    @Override
    public void patchTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalPropertyDTO> additionalPropertyDTOS) {
        Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet = additionalPropertyDTOS.stream()
                .map(tenantMetaAdditionalPropertyDTOAssembler::fromDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        service.patchTenantMetaAdditionalProperty(tenantLogin,tenantMetaAdditionalPropertySet);
        if(tenantMetaConfigCacheManager != null) {
            tenantMetaConfigCacheManager.clearCache(tenantLogin);
        }
    }

    @Override
    public TenantMetaConfigDTO patchTenantMetaConfigForLoggedInTenant(TenantMetaConfigDTO tenantMetaConfigDTO) {
        TenantMetaConfigDTO metaConfigDTO = assembler.toDTO(service.patchTenantMetaConfigForLoggedInTenant(assembler.fromDTO(tenantMetaConfigDTO)));
        if(tenantMetaConfigCacheManager != null) {
            tenantMetaConfigCacheManager.clearCache(metaConfigDTO.getTenantLogin());
        }
        return metaConfigDTO;
    }

    @Override
    public Long postTenantMetaConfigForLoggedInTenant(TenantMetaConfigDTO tenantMetaConfigDTO) {
        PlatformUtil.validateTenantAdmin();
        TenantMetaConfig tenantMetaConfig = service.postTenantMetaConfigForLoggedInTenant(assembler.fromDTO(tenantMetaConfigDTO));
        if(tenantMetaConfigCacheManager != null) {
            tenantMetaConfigCacheManager.clearCache(tenantMetaConfig.getTenantLogin());
        }
        return tenantMetaConfig.getId();
    }

    @Override
    public void deleteTenantMetaConfig(Long id, String reason) {
        TenantMetaConfig fetchedTenantMetaConfig = service.deleteTenantMetaConfig(id,reason);
        if(tenantMetaConfigCacheManager != null) {
            tenantMetaConfigCacheManager.clearCache(fetchedTenantMetaConfig.getTenantLogin());
        }
    }

    @Override
    public TenantMetaConfigDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public TenantMetaConfigDTO getForLoggedInTenant() {
        return assembler.toDTO(service.getTenantMetaConfig(PlatformSecurityUtil.getCurrentTenantId(),null));
    }

    @Override
    public void deleteAllTenantMetaConfigCache() {
        if(tenantMetaConfigCacheManager != null) {
            tenantMetaConfigCacheManager.clearAllCache();
        }
    }

    @Override
    public TenantMetaConfigDTO getTenantMetaConfigByTenantLoginAndLoadCache(String tenantLogin) {
        TenantMetaConfigDTO tenantMetaConfigDTO = null;
        Optional<TenantMetaConfig> tenantMetaConfigOptional = service.getTenantMetaConfigOptional(null,tenantLogin);
        if(tenantMetaConfigOptional.isPresent()) {
            tenantMetaConfigDTO = assembler.toDTO(tenantMetaConfigOptional.get());
            if (tenantMetaConfigCacheManager != null){
                tenantMetaConfigCacheManager.setValueToCache(tenantMetaConfigDTO);
            }
        }
        return tenantMetaConfigDTO;
    }

    @Override
    public void deleteTenantMetaConfigCacheByTenantLogin(String tenantLogin) {
        if (tenantMetaConfigCacheManager != null) {
            tenantMetaConfigCacheManager.clearCache(tenantLogin);
        }
    }

    @Override
    public TenantMetaConfigDTO getByTenantLoginFromCacheOrDB(String tenantLogin) {
        TenantMetaConfigDTO tenantMetaConfigDTO;
        if(tenantMetaConfigCacheManager != null) {
            tenantMetaConfigDTO = tenantMetaConfigCacheManager.getValueForKey(tenantLogin);
        }
        else {
            tenantMetaConfigDTO =  assembler.toDTO(service.getTenantMetaConfig(null,tenantLogin));
        }
        return tenantMetaConfigDTO;
    }

    @Override
    public PageDTO<TenantMetaConfigDTO> getAllTenantMetaConfig(String metaKey, String metaValue, Integer page, Integer size, String sortBy, String direction) {
        if (sortBy == null){
            sortBy = "id";
            direction = "desc";
        }
        PageRequest pageRequest = PageRequest.of(page, size, JPAUtility.convertToSort(sortBy, direction));
        Page<TenantMetaConfig> tenantMetaConfigPage = service.getAllTenantMetaConfig(metaKey,metaValue, pageRequest);
        return new PageDTO<>(
                tenantMetaConfigPage.getContent().stream()
                        .map(assembler::toDTO)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                tenantMetaConfigPage.hasNext(),
                tenantMetaConfigPage.getTotalElements()
        );
    }

    @Override
    public Set<TenantMetaConfigDTO> getAllTenantMetaConfigSet(String metaKey, String metaValue) {
        Set<TenantMetaConfig> tenantMetaConfigs = service.getAllTenantMetaConfigSet(metaKey,metaValue);
        return tenantMetaConfigs.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TenantMetaAdditionalPropertyDTO> getTenantMetaAdditionalPropertiesByMetaKeysAndTenantLogin(Set<String> metaKeys,
                                                                                                          String tenantLogin,
                                                                                                          String appContext) {
        String defaultAppContext  = IAMConstant.DEFAULT_APP_CONTEXT;
        Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet = service.getTenantMetaAdditionalPropertiesByMetaKeysAndTenantLogin(
                metaKeys,tenantLogin,appContext,defaultAppContext);
        return tenantMetaAdditionalPropertyDTOAssembler.toDTOs(tenantMetaAdditionalPropertySet);
    }

    public TenantMetaAdditionalPropertyDTO getAdditionalPropertyByMetaKeyAndAppContext(Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertyDTOSet,
                                                                          String metaKey,String appContext) {
        TenantMetaAdditionalPropertyDTO result  = null;
        if(tenantMetaAdditionalPropertyDTOSet != null && metaKey != null && appContext != null) {
            result = tenantMetaAdditionalPropertyDTOSet.stream()
                    .filter(it -> Objects.equals(it.getMetaKey(),metaKey)
                            && Objects.equals(it.getAppContext(),appContext))
                    .findFirst()
                    .orElse(null);
        }
        return result;
    }

    @Override
    public String getMetaPropertyValueByMetaKeyAndHierarchy(String metaKey, String tenantLogin,String appContext) {
        String value = null;
        TenantMetaAdditionalPropertyDTO propertyDTO = getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(metaKey,tenantLogin,appContext);
        if(propertyDTO != null) {
            value = propertyDTO.getMetaValue();
        }
        return value;
    }

    @Override
    public TenantMetaAdditionalPropertyDTO getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(String metaKey, String tenantLogin,String appContext) {
        TenantMetaAdditionalPropertyDTO result = null;
        if(tenantLogin != null && metaKey != null) {
            TenantMetaConfigDTO tenantMetaConfigDTO = getByTenantLoginFromCacheOrDB(tenantLogin);
            if(tenantMetaConfigDTO != null) {
                Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertyDTOSet = tenantMetaConfigDTO.getTenantMetaAdditionalPropertySet();
                result = getMetaPropertyDTOByHierarchy(tenantMetaAdditionalPropertyDTOSet,metaKey,appContext);
            }
        }
        return result;
    }

    public TenantMetaAdditionalPropertyDTO getMetaPropertyDTOByHierarchy(Set<TenantMetaAdditionalPropertyDTO> set,String metaKey,
                                                              String appContext) {
        TenantMetaAdditionalPropertyDTO result = null;
        appContext = appContext == null ? IAMConstant.DEFAULT_APP_CONTEXT : appContext;
        result =  getAdditionalPropertyByMetaKeyAndAppContext(set,metaKey,appContext);
        if(result == null && !appContext.equals(IAMConstant.DEFAULT_APP_CONTEXT)) {
            appContext = IAMConstant.DEFAULT_APP_CONTEXT;
            result = getAdditionalPropertyByMetaKeyAndAppContext(set,metaKey,appContext);
        }
        return result;
    }

    @Override
    public Set<String> getMetaValueForMultiValuedMetaByConfigDTO(TenantMetaConfigDTO tenantMetaConfigDTO, String metaKey, String appContext) {
        Set<String> values = new HashSet<>();
        Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertyDTOSet = tenantMetaConfigDTO.getTenantMetaAdditionalPropertySet();
        TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO = getMetaPropertyDTOByHierarchy(tenantMetaAdditionalPropertyDTOSet,
                metaKey,appContext);
        if(tenantMetaAdditionalPropertyDTO != null) {
            values = getMetaValueForMultiValuedMeta(tenantMetaAdditionalPropertyDTO);
        }
        return values;
    }

    public Set<String> getMetaValueForMultiValuedMeta(TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO) {
        Set<String> metaValue = new HashSet<>();
        if (tenantMetaAdditionalPropertyDTO != null) {
            if (tenantMetaAdditionalPropertyDTO.getIsMultivalued() == null || tenantMetaAdditionalPropertyDTO.getIsMultivalued().equals(Boolean.FALSE)) {
                throw new InvalidInputException(String.format("Tenant Meta Property with meta key %s is not multiValued", tenantMetaAdditionalPropertyDTO.getMetaKey()));
            } else {
                String[] metaValueArray = tenantMetaAdditionalPropertyDTO.getMetaValue().split(tenantMetaAdditionalPropertyDTO.getMultiValueSeparator());
                Collections.addAll(metaValue, metaValueArray);
            }
        }
        return metaValue;
    }

    public TenantMetaConfig createTenantMetaConfigObjectFromTenantId(TenantDTO tenantDTO) {
        return TenantMetaConfig.builder()
                .id(0L)
                .tenantLogin(tenantDTO.getTenantLogin())
                .userLogin(vmsSignupUtil.opsSupportLogin)
                .password(vmsSignupUtil.opsSupportPassword)
                .actorContext("OPS_SUPPORT")
                .build();
    }

    @Override
    public void createTenantMetaConfigOnTenantSignupVMS(TenantDTO tenantDTO, String appContext) {
        TenantMetaConfig tenantMetaConfig = createTenantMetaConfigObjectFromTenantId(tenantDTO);
        Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet = VMSSignupUtil
                .createDefaultTenantAdditionalPropertyOnSignUpVMS(tenantDTO,appContext);
        tenantMetaConfig.setTenantMetaAdditionalPropertySet(tenantMetaAdditionalPropertySet);
        service.save(tenantMetaConfig);
    }

}
