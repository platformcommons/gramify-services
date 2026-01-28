package com.platformcommons.platform.service.search.facade.cache.validator;

import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.search.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.search.facade.client.CommonsIamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
@Slf4j
public class TenantMetaConfigValidator {

    private static final Long CACHE_TENANT_META_CONFIG_TTL = 360000L;

    @Autowired(required = false)
    private CommonsCacheManager<String, TenantMetaConfigDTO> cacheManagerForTenantMetaConfig;

    @Autowired
    private CommonsIamClient commonsIamClient;

    @Value("${commons.platform.cache.appkey:Appkey YXBwS2V5OjdiMDNlMWJlLTkwMjctNDMwNC05YjRjLTlkMTdiZmI4NTM2NyxhcHBDb2RlOkNPTU1PTlNfQVBQLkNIQU5HRU1BS0VS}")
    private String appKey;

    public TenantMetaConfigDTO getByTenantLogin(String tenantLogin) {
        TenantMetaConfigDTO tenantMetaConfigDTO = null;
        if(tenantLogin != null) {
            if (cacheManagerForTenantMetaConfig != null) {
                log.debug("-->>> TenantMetaConfig : Getting TenantMetaConfig from cache..");
                tenantMetaConfigDTO = cacheManagerForTenantMetaConfig.getCache("tenant_meta_config_iam", CACHE_TENANT_META_CONFIG_TTL)
                        .get(tenantLogin);
                if (tenantMetaConfigDTO == null) {
                    tenantMetaConfigDTO = getTenantMetaConfigDTOFromFeignCall(tenantLogin);
                }
            }
            else{
                tenantMetaConfigDTO = getTenantMetaConfigDTOFromFeignCall(tenantLogin);
            }
        }
        return tenantMetaConfigDTO;
    }

    private  TenantMetaConfigDTO getTenantMetaConfigDTOFromFeignCall(String tenantLogin) {
        log.debug("-->>> TenantMetaConfig : Not in Cache !!  Getting TenantMetaConfig from feign call..");
        return commonsIamClient.getTenantMetaConfigByTenantLoginAndLoadCache(tenantLogin,appKey).getBody();
    }

    public String getMetaPropertyValueFromTenantMetaConfigDTO(TenantMetaConfigDTO tenantMetaConfigDTO, String metaKey,String appContext) {
        String value = null;
        Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertyDTOSet = tenantMetaConfigDTO.getTenantMetaAdditionalPropertySet();
        TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO = getMetaPropertyDTOByHierarchy(tenantMetaAdditionalPropertyDTOSet,
                metaKey,appContext);
        if(tenantMetaAdditionalPropertyDTO != null) {
            value = tenantMetaAdditionalPropertyDTO.getMetaValue();
        }
        if(value == null){
            value = TenantConfigConstant.DEFAULT_SINGLE_VALUE_CONFIG_MAP.get(metaKey);
        }
        return value;
    }

    public Set<String> getMetaValueForMultiValuedMetaByConfigDTO(TenantMetaConfigDTO tenantMetaConfigDTO, String metaKey,String appContext) {
        Set<String> values = null;
        Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertyDTOSet = tenantMetaConfigDTO.getTenantMetaAdditionalPropertySet();
        TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO = getMetaPropertyDTOByHierarchy(tenantMetaAdditionalPropertyDTOSet,
                metaKey,appContext);
        if(tenantMetaAdditionalPropertyDTO != null) {
            values = getMetaValueForMultiValuedMeta(tenantMetaAdditionalPropertyDTO);
        }

        if (values == null){
            values = TenantConfigConstant.DEFAULT_MULTI_VALUE_CONFIG_MAP.get(metaKey);
        }
        return values;
    }

    public Set<String> getMetaValueForMultiValuedMeta(TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO) {
        Set<String> metaValue = null;
        if (tenantMetaAdditionalPropertyDTO != null) {
            if (tenantMetaAdditionalPropertyDTO.getIsMultivalued() == null || tenantMetaAdditionalPropertyDTO.getIsMultivalued().equals(Boolean.FALSE)) {
                throw new InvalidInputException(String.format("Tenant Meta Property with meta key %s is not multiValued", tenantMetaAdditionalPropertyDTO.getMetaKey()));
            } else {
                String[] metaValueArray = tenantMetaAdditionalPropertyDTO.getMetaValue().split(tenantMetaAdditionalPropertyDTO.getMultiValueSeparator());
                metaValue = new HashSet<>(Arrays.asList(metaValueArray));
            }
        }
        return metaValue;
    }

    public Set<String> getMultiValueByMetaKeyAndTenantLogin(String tenantLogin, String metaKey,String appContext) {
        Set<String> metaValue = null;
        TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO = getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(
                metaKey,tenantLogin,appContext);
        if(tenantMetaAdditionalPropertyDTO != null) {
            metaValue  = getMetaValueForMultiValuedMeta(tenantMetaAdditionalPropertyDTO);
        }

        if (metaValue == null) {
            metaValue = TenantConfigConstant.DEFAULT_MULTI_VALUE_CONFIG_MAP.get(metaKey);
        }
        return metaValue;
    }

    public String getMetaPropertyValueByMetaKeyAndTenantLogin(String metaKey, String tenantLogin,String appContext) {
        String value = null;
        TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO = getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(
                metaKey,tenantLogin,appContext);
        if(tenantMetaAdditionalPropertyDTO != null) {
            value = tenantMetaAdditionalPropertyDTO.getMetaValue();
        }

        if (value == null) {
            value = TenantConfigConstant.DEFAULT_SINGLE_VALUE_CONFIG_MAP.get(metaKey);
        }

        return value;
    }

    public TenantMetaAdditionalPropertyDTO getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(String metaKey, String tenantLogin,String appContext) {
        TenantMetaAdditionalPropertyDTO result = null;
        if(tenantLogin != null && metaKey != null) {
            TenantMetaConfigDTO tenantMetaConfigDTO = getByTenantLogin(tenantLogin);
            if(tenantMetaConfigDTO != null) {
                Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertyDTOSet = tenantMetaConfigDTO.getTenantMetaAdditionalPropertySet();
                result = getMetaPropertyDTOByHierarchy(tenantMetaAdditionalPropertyDTOSet,metaKey,appContext);
            }
        }
        return result;
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

    public TenantMetaAdditionalPropertyDTO getMetaPropertyDTOByHierarchy(Set<TenantMetaAdditionalPropertyDTO> set,String metaKey,
                                                                         String appContext) {
        TenantMetaAdditionalPropertyDTO result = null;
        appContext = appContext == null ? TenantConfigConstant.DEFAULT_APP_CONTEXT : appContext;
        result =  getAdditionalPropertyByMetaKeyAndAppContext(set,metaKey,appContext);
        if(result == null && !appContext.equals(TenantConfigConstant.DEFAULT_APP_CONTEXT)) {
            appContext = TenantConfigConstant.DEFAULT_APP_CONTEXT;
            result = getAdditionalPropertyByMetaKeyAndAppContext(set,metaKey,appContext);
        }
        return result;
    }

}