package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.AppFeatureConfigurationService;
import com.platformcommons.platform.service.app.application.constant.AppFeatureConfigurationConstant;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.AppFeatureConfiguration;
import com.platformcommons.platform.service.app.domain.ComponentConfig;
import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.dto.AppFeatureConfigurationDTO;
import com.platformcommons.platform.service.app.dto.ComponentConfigDTO;
import com.platformcommons.platform.service.app.facade.AppFeatureConfigurationFacade;
import com.platformcommons.platform.service.app.facade.assembler.AppFeatureConfigurationDTOAssembler;
import com.platformcommons.platform.service.app.facade.assembler.ComponentConfigDTOAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
public class AppFeatureConfigurationFacadeImpl implements AppFeatureConfigurationFacade {

    @Autowired
    private AppFeatureConfigurationService service;

    @Autowired
    private AppFeatureConfigurationDTOAssembler assembler;

    @Autowired
    private ComponentConfigDTOAssembler componentConfigDTOAssembler;

    @Override
    public AppFeatureConfigurationDTO getByParams(String ownerEntityId, String ownerEntityType, String forEntityType) {
        return assembler.toDTO(service.getByParams(ownerEntityId,ownerEntityType,forEntityType));
    }

    @Override
    public List<AppFeatureConfigurationDTO> getAllByOwnerEntityType(String ownerEntityType, String forEntityType) {
        List<AppFeatureConfiguration> appFeatureConfigurationList = service.getAllByOwnerEntityType(ownerEntityType,forEntityType);
        return assembler.toDTOs(appFeatureConfigurationList);
    }


    @Override
    public AppFeatureConfigurationDTO getForLoggedInTenantByHierarchyAndFallback(String marketId, String forEntityType, String tenantId) {
        AppFeatureConfiguration appFeatureConfiguration = service.getForLoggedInTenantByHierarchy(marketId,forEntityType, tenantId);
        AppFeatureConfigurationDTO appFeatureConfigurationDTO = assembler.toDTO(appFeatureConfiguration);

        if(Objects.equals(appFeatureConfiguration.getIsFallback(),Boolean.TRUE)) {
            AppFeatureConfigurationDTOAssembler.resetPrimaryKey(appFeatureConfigurationDTO);
        }

        return appFeatureConfigurationDTO;
    }

    @Override
    public AppFeatureConfigurationDTO getForLoggedInTenantByHierarchy(String marketId, String forEntityType, String tenantId) {
        return assembler.toDTO(service.getForLoggedInTenantByHierarchy(marketId,forEntityType, tenantId));
    }

    @Override
    public AppFeatureConfigurationDTO save(AppFeatureConfigurationDTO dto) {
        // Business rule: validate ownerEntityType vs ownerEntityId
        if (AppFeatureConfigurationConstant.OWNER_ENTITY_TYPE_PLATFORM.equals(dto.getOwnerEntityType())) {
            PlatformUtil.validatePlatformAdmin();
            if (dto.getOwnerEntityId() != null) {
                throw new IllegalArgumentException("For PLATFORM ownerEntityType, ownerEntityId must be null");
            }
        } else if (AppFeatureConfigurationConstant.OWNER_ENTITY_TYPE_MARKET.equals(dto.getOwnerEntityType())
                || AppFeatureConfigurationConstant.OWNER_ENTITY_TYPE_TENANT.equals(dto.getOwnerEntityType())) {
            if (dto.getOwnerEntityId() == null) {
                throw new IllegalArgumentException("For MARKET or TENANT ownerEntityType, ownerEntityId must not be null");
            }
        }

        AppFeatureConfiguration appFeatureConfiguration = assembler.fromDTO(dto);
        appFeatureConfiguration = service.save(appFeatureConfiguration);
        return assembler.toDTO(appFeatureConfiguration);
    }

    @Override
    public void patch(AppFeatureConfigurationDTO dto) {
        AppFeatureConfiguration fetchedConfig = service.getById(dto.getId());
        PlatformUtil.validateLoginTenant(fetchedConfig.getTenantId());

        AppFeatureConfiguration toPatch = assembler.fromDTO(dto);
        service.patch(toPatch);
    }

    @Override
    public void deleteById(Long id, String reason) {
        service.deleteById(id, reason);
    }

    @Override
    public AppFeatureConfigurationDTO getById(Long id) {
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public void syncDefaultFeatureConfigToGivenFeatureConfigs( String forEntityType,String ownerEntityType) {
        PlatformUtil.validatePlatformAdmin();
        AppFeatureConfigurationDTO defaultAppFeatureDTO = getByParams(null, AppFeatureConfigurationConstant.OWNER_ENTITY_TYPE_PLATFORM,
                forEntityType);

        List<AppFeatureConfigurationDTO> targetAppFeatureConfigDTOList = getAllByOwnerEntityType(ownerEntityType,forEntityType);

        for(AppFeatureConfigurationDTO targetAppFeatureConfigDTO : targetAppFeatureConfigDTOList) {
            syncComponentConfigFromSource(defaultAppFeatureDTO.getComponentConfigSet(),
                    targetAppFeatureConfigDTO.getComponentConfigSet());
            patch(targetAppFeatureConfigDTO);
        }

    }

    public void syncComponentConfigFromSource(Set<ComponentConfigDTO> sourceSet,
                                              Set<ComponentConfigDTO> targetSet) {

        if(sourceSet == null || sourceSet.isEmpty()){
            return;
        }

        if(targetSet == null) {
            targetSet = new HashSet<>();
        }

        Map<String, ComponentConfigDTO> targetComponentConfigMap = targetSet.stream()
                .collect(Collectors.toMap(ComponentConfigDTO::getComponentCode, Function.identity(),(a, b)->a));

        for(ComponentConfigDTO sourceComponentConfigDTO : sourceSet) {
            String componentCode = sourceComponentConfigDTO.getComponentCode();
            ComponentConfigDTO targetComponentConfigDTO = targetComponentConfigMap.getOrDefault(componentCode, null);

            if(targetComponentConfigDTO != null){
                syncComponentConfigFromSource(sourceComponentConfigDTO.getSubComponentConfigSet(),
                        targetComponentConfigDTO.getSubComponentConfigSet());
            }
            else {
                ComponentConfigDTO componentConfigDTO = buildComponentConfigFromSource(sourceComponentConfigDTO);
                targetSet.add(componentConfigDTO);
            }
        }

    }

    public ComponentConfigDTO buildComponentConfigFromSource(ComponentConfigDTO source) {
        ComponentConfigDTO target = ComponentConfigDTO.builder().build();
        BeanUtils.copyProperties(source, target);

        // Reset ID
        ComponentConfigDTOAssembler.resetPrimaryKey(target);

        // Recursively copy children
        if (source.getSubComponentConfigSet() != null) {
            Set<ComponentConfigDTO> targetSubComponentConfigDTOSet = new HashSet<>();
            for (ComponentConfigDTO sourceSubComponentConfigDTO : source.getSubComponentConfigSet()) {
                ComponentConfigDTO targetSubComponentConfigDTO = buildComponentConfigFromSource(sourceSubComponentConfigDTO);
                targetSubComponentConfigDTOSet.add(targetSubComponentConfigDTO);
            }
            target.setSubComponentConfigSet(targetSubComponentConfigDTOSet);
        }

        return target;
    }

}
