package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.User;
import com.platformcommons.platform.service.search.dto.UserFilterDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {


    User save(User user);

    User getByUserId(Long userId);

    User putUpdate(User user);

    void delete(Long id);

    FacetPage<User> getUsersWithFilter(String searchTerm, UserFilterDTO filterDTO, Pageable pageable);

    List<User> getAllUsersByFilter(UserFilterDTO filterDTO) ;

    List<User> getUsersByUserIds(Set<Long> userIds, Set<String> fields);

    Long getUsersCount(String tenantLogin);

    List<User> getUsersByFetchStrategy(UserFilterDTO filterDTO, Pageable pageable);

    Boolean existsByUserId(Long userId);
}
