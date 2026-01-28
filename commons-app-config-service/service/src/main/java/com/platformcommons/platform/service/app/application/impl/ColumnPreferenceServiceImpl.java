package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.service.app.application.ColumnPreferenceService;
import com.platformcommons.platform.service.app.application.constant.ServiceConstant;
import com.platformcommons.platform.service.app.domain.ColumnPreference;
import com.platformcommons.platform.service.app.domain.ColumnSetting;
import com.platformcommons.platform.service.app.domain.repo.ColumnPreferenceNonMTRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ColumnPreferenceServiceImpl implements ColumnPreferenceService  {

    @Autowired
    private ColumnPreferenceNonMTRepository nonMTRepository;

    public ColumnPreference getById(Long id) {
        return nonMTRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format("Column Preference with id %d not found",id)));
    }


    @Override
    public Long post(ColumnPreference columnPreference) {
        Optional<ColumnPreference> columnPreferenceOptional = getByParamsForExactMatchOptional(columnPreference.getEntityId(),
                columnPreference.getEntityType(),columnPreference.getSchemaCode(),columnPreference.getOwnerId(),
                columnPreference.getOwnerType());

        if(columnPreferenceOptional.isPresent()) {
            throw new DuplicateResourceException("Column Preference already exists");
        }

        columnPreference.init();
        return nonMTRepository.save(columnPreference).getId();
    }

    @Override
    public void patchUpdateForLoggedInUser(ColumnPreference columnPreference, String userId) {
        ColumnPreference fetchedColumnPreference = getById(columnPreference.getId());
        if(Objects.equals(fetchedColumnPreference.getOwnerId(),userId)
                && Objects.equals(fetchedColumnPreference.getOwnerType(),ServiceConstant.OWNER_TYPE_USER)) {
            fetchedColumnPreference.patch(columnPreference);
            nonMTRepository.save(fetchedColumnPreference);
        }
        else {
            throw new UnAuthorizedAccessException("Not allowed to update the entity");
        }
    }

    @Override
    public void patchUpdateForDefaultOwnerLevels(ColumnPreference columnPreference) {
        ColumnPreference fetchedColumnPreference = getById(columnPreference.getId());
        if (Objects.equals(fetchedColumnPreference.getOwnerType(), ServiceConstant.OWNER_TYPE_USER)) {
            throw new InvalidInputException("Cannot update user-specific ColumnPreference via default level patch API. " +
                    " Use patchUpdateForLoggedInUser instead.");
        }
        fetchedColumnPreference.patch(columnPreference);
        nonMTRepository.save(fetchedColumnPreference);
    }

    @Override
    public ColumnPreference getByParamsForExactMatch(String entityId, String entityType, String schemaCode, String ownerId, String ownerType) {
        return getByParamsForExactMatchOptional(entityId,entityType,schemaCode,ownerId,ownerType)
                .orElseThrow(()-> new NotFoundException("Column Preference not found"));
    }


    public Optional<ColumnPreference> getByParamsForExactMatchOptional(String entityId, String entityType,
                                                           String schemaCode, String ownerId, String ownerType) {
        return nonMTRepository.findByParamsForExactMatch(schemaCode,entityId,entityType,ownerId,ownerType);
    }


    @Override
    public ColumnPreference getByUserIdAndHierarchy(String marketId, String entityId, String entityType,
                                                    String schemaCode, String userId, String tenantId) {
        List<PreferenceLookupEntry> lookupEntries = new ArrayList<>();
        lookupEntries.add(new PreferenceLookupEntry(userId, ServiceConstant.OWNER_TYPE_USER));
        lookupEntries.add(new PreferenceLookupEntry(tenantId, ServiceConstant.OWNER_TYPE_TENANT));
        lookupEntries.add(new PreferenceLookupEntry(marketId, ServiceConstant.OWNER_TYPE_MARKET));
        lookupEntries.add(new PreferenceLookupEntry(null, ServiceConstant.OWNER_TYPE_PLATFORM));


        for (PreferenceLookupEntry entry : lookupEntries) {
            if(!Objects.equals(entry.getOwnerType(),ServiceConstant.OWNER_TYPE_USER)) {
                entityId = null;
                entityType = null;
            }
            Optional<ColumnPreference> result = nonMTRepository.findByParamsForExactMatch(
                    schemaCode, entityId, entityType, entry.getOwnerId(), entry.getOwnerType());
            if (result.isPresent()) {
                return result.get();
            }
        }

        throw new NotFoundException("Column Preference not found");
    }

    @Override
    public void syncColumnPreference(String sourceOwnerType, String schemaCode) {
        List<ColumnPreference> sourcePreferences =
                nonMTRepository.findBySchemaCodeAndOwnerType(schemaCode, sourceOwnerType);

        for (ColumnPreference parentPref : sourcePreferences) {
            syncToChildrenRecursively(parentPref);
        }
    }

    public void syncToChildrenRecursively(ColumnPreference parentPref) {
        List<ColumnPreference> childPrefs = nonMTRepository.findByParentColumnPreferenceId(parentPref.getId());
        for (ColumnPreference childPref : childPrefs) {
            Boolean updated = syncMissingColumnSettings(parentPref, childPref);
            if (updated) {
                childPref = nonMTRepository.save(childPref);

                // Recursively sync this updated child down to its own children
                syncToChildrenRecursively(childPref);
            }
        }
    }

    public static Map<String,ColumnSetting> fetchMapOfColumnSettings(ColumnPreference parentPref) {
        Map<String,ColumnSetting> result = new HashMap<>();
        if(parentPref != null && parentPref.getColumnSettings() != null) {
            result = parentPref.getColumnSettings().stream()
                    .collect(Collectors.toMap(ColumnSetting::getCode, Function.identity()));
        }
        return result;
    }

    public Boolean syncMissingColumnSettings(ColumnPreference parentPref, ColumnPreference childPref) {
        AtomicReference<Boolean> updated = new AtomicReference<>(Boolean.FALSE);
        Map<String,ColumnSetting> childColumnSettingMap = fetchMapOfColumnSettings(childPref);
        Set<ColumnSetting> parentColumnPreferenceSet = parentPref.getColumnSettings();

        parentColumnPreferenceSet.forEach(parentColumnSetting -> {
            String columnSettingCode = parentColumnSetting.getCode();
            if(!childColumnSettingMap.containsKey(columnSettingCode)) {
                updated.set(Boolean.TRUE);
                ColumnSetting columnSetting = cloneColumnSetting(parentColumnSetting);
                columnSetting.init(childPref);
                childPref.getColumnSettings().add(columnSetting);
            }
        });

        return updated.get();
    }


    public static ColumnSetting cloneColumnSetting(ColumnSetting source) {
        return ColumnSetting.builder()
                .code(source.getCode())
                .label(source.getLabel())
                .sequence(source.getSequence())
                .visibility(source.getVisibility())
                .build();
    }

    @AllArgsConstructor
    @Data
    private class PreferenceLookupEntry {

        private String ownerId;

        private String ownerType;
    }

}
