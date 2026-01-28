package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.UserMetaDataService;
import com.platformcommons.platform.service.iam.domain.UserMetaData;
import com.platformcommons.platform.service.iam.domain.repo.UserMetaDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMetaDataServiceImpl implements UserMetaDataService {

    private final UserMetaDataRepository repository;

    @Override
    public Set<UserMetaData> getByMetaKeyInBulkForLoggedInUser(Set<String> metaKeys,Long userId) {
        return repository.findByMetaKeyAndUserId(userId,metaKeys);
    }

    @Override
    public void postOrUpdateForUser(Set<UserMetaData> userMetaDataSet,Long userId) {
        Map<String, UserMetaData> existingMetaData = fetchExistingMetaData(userMetaDataSet, userId);
        Set<UserMetaData> updatedMetaData = processMetaDataUpdates(userMetaDataSet, existingMetaData, userId);
        repository.saveAll(updatedMetaData);
    }

    private Map<String, UserMetaData> fetchExistingMetaData(Set<UserMetaData> userMetaDataSet, Long userId) {
        Set<String> metaKeys = userMetaDataSet.stream()
                .map(UserMetaData::getMetaKey)
                .collect(Collectors.toSet());
        return getByMetaKeyInBulkForLoggedInUser(metaKeys, userId).stream()
                .collect(Collectors.toMap(UserMetaData::getMetaKey, Function.identity(), (a,b) -> a));
    }

    private Set<UserMetaData> processMetaDataUpdates(Set<UserMetaData> updates,
                                                     Map<String, UserMetaData> existing, Long userId) {
        updates.forEach(inputValue -> {
            if(existing.containsKey(inputValue.getMetaKey())){
                UserMetaData existingValue= existing.get(inputValue.getMetaKey());
                existingValue.patch(inputValue);
            }else{
                inputValue.setUserId(userId);
                inputValue.init();
            }
        });
        return updates;
    }


}
