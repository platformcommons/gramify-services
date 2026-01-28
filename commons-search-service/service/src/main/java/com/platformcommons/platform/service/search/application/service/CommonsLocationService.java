package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.CommonsLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CommonsLocationService {

    CommonsLocation save(CommonsLocation commonsLocation);

    Page<CommonsLocation> getLocationByAddressLabel(String searchTerm, Pageable pageable, Boolean cityData, Boolean stateData,
                                                    Boolean countryData);

    Set<CommonsLocation> getByAddressLabel(String addressLabel);

    List<CommonsLocation> getByLocationCodesInBulk(Set<String> dataCodes, String sortBy, String direction, String dataCodesField);

    Page<CommonsLocation> getLocationBySearchTerm(String searchText, String countryCode, Boolean cityData, Pageable pageable);

    void reindex();
}
