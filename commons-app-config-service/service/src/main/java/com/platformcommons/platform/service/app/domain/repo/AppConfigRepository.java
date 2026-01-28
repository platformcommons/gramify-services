package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.AppConfig;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AppConfigRepository extends NonMultiTenantBaseRepository<AppConfig, Long> {
    @Query("From #{#entityName} ac where  ac.scopeCode in :scopeCodes and ac.appVersion.id  = :versionId ")
    List<AppConfig> findAllByScopeCodeWithValue(Long versionId, Set<String> scopeCodes);


    @Query("From #{#entityName} ac where  ac.appVersion.id  = :versionId  AND   ac.scopeCode in :scopeCodes ")
    List<AppConfig> findAllByScopeCodes(Long versionId, Set<String> scopeCodes);

    @Query("From #{#entityName} ac where  ac.appVersion.id  = :versionId  AND   ac.scopeCode = :scopeCode  and ac.scopeValue = :scopeValue and (   ac.configKey not in  :existingKeys   OR coalesce(:existingKeys) is null )")
    List<AppConfig> findAllByScopeCodeWithValue(Long versionId, String scopeCode, String scopeValue, List<String> existingKeys);

    @Query("From #{#entityName} ac where  ac.appVersion.id  = :versionId  AND   ac.scopeCode = :scopeCode  and ac.scopeValue IS NULL  and (   ac.configKey not in  :existingKeys   OR coalesce(:existingKeys) is null )")
    List<AppConfig> findAllByScopeCode(Long versionId, String scopeCode, List<String> existingKeys);

    @Query("From #{#entityName} ac where  ac.appVersion.id  = :versionId AND ( ac.configType=:configType OR :configType is null ) AND   ac.scopeCode = :scopeCode  and ac.scopeValue IS NULL  and (   ac.configKey not in  :existingKeys   OR coalesce(:existingKeys) is null )")
    List<AppConfig> findAllByScopeCodeAndConfigType(Long versionId, String scopeCode, List<String> existingKeys,String configType);

    @Query("From #{#entityName} ac where  ac.appVersion.id  = :versionId AND ( ac.configType=:configType OR :configType is null ) AND   ac.scopeCode = :scopeCode  " +
            "and ac.scopeValue = :scopeValue and (   ac.configKey not in  :existingKeys   OR coalesce(:existingKeys) is null )")
    List<AppConfig> findAllByScopeCodeWithValueForConfigType(Long versionId, String scopeCode, String scopeValue,
                                                             List<String> existingKeys, String configType);

    @Query("From #{#entityName} ac where  ac.appVersion.id  = :versionId AND ( ac.configType=:configType OR :configType is null ) AND   ac.scopeCode = :scopeCode  " +
            "and ac.scopeValue = :scopeValue and (   ac.configKey IN  :configKeys   OR coalesce(:configKeys) is null )")
    Set<AppConfig> findAllByConfigKeysAndScopeAndAppVersion(Long versionId, String scopeCode, String scopeValue,
                                                             Set<String> configKeys, String configType);

    @Query("From #{#entityName} ac where  ac.appVersion.id  = :versionId AND ( ac.configType=:configType OR :configType is null ) AND   ac.scopeCode = :scopeCode  " +
            "and ac.scopeValue = :scopeValue and   ac.configKey = :configKey ")
    Optional<AppConfig> findByConfigKeyAndScopeAndAppVersion(Long versionId, String scopeCode, String scopeValue,
                                                                 String configKey, String configType);

    @Query("From #{#entityName} ac where ac.appVersion.appId.code = :appCode AND ac.appVersion.version = :appVersion AND ac.scopeValue = :scopeValue")
    List<AppConfig> findByAppCodeAndAppVersionWithScopeValue(String appCode, String appVersion, String scopeValue);
}