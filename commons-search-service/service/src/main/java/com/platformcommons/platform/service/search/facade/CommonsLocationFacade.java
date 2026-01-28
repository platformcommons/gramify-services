package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.CommonsLocationDTO;

import java.util.Map;
import java.util.Set;

public interface CommonsLocationFacade {

    CommonsLocationDTO save(CommonsLocationDTO commonsLocationDTO);

    PageDTO<CommonsLocationDTO> getLocationByAddressLabel(String searchTerm, Integer page, Integer size, Boolean cityData,
                                                          Boolean stateData, Boolean countryData, String sortBy, String direction) ;

    Set<CommonsLocationDTO> getByAddressLabel(String addressLabel);

    Map<String, CommonsLocationDTO> getByLocationCodesInBulk(Set<String> dataCodes, String sortBy, String direction, String dataCodesField);


    Object searchLocationByGoogle(String query);

    PageDTO<CommonsLocationDTO> getLocationBySearchTerm(String searchText,String countryCode, Integer page, Integer size, Boolean cityData, String sortBy, String direction);

    void reindex();

    /**
     * Get a location and its parent locations in the hierarchy.
     * 
     * @param locationCode The location code (city, district, state, or country)
     * @return A Set of location codes representing the location hierarchy
     */
    Set<String> getLocationHierarchy(String locationCode);

    /**
     * Get a location by its code in the format "REGION_TYPE.CODE".
     * 
     * @param locationCode The location code in the format "REGION_TYPE.CODE" (e.g., "REGION_CITY.ABC")
     * @return The location DTO corresponding to the code
     */
    CommonsLocationDTO getLocationByCode(String locationCode);
}
