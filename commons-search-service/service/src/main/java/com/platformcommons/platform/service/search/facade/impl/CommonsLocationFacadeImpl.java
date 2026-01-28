package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.CommonsLocationService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.domain.CommonsLocation;
import com.platformcommons.platform.service.search.domain.CompanyMasterDataV2;
import com.platformcommons.platform.service.search.dto.CommonsLocationDTO;
import com.platformcommons.platform.service.search.facade.CommonsLocationFacade;
import com.platformcommons.platform.service.search.facade.assembler.CommonsLocationDTOAssembler;
import com.platformcommons.platform.service.search.facade.client.GoogleMapsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional
@Component
public class CommonsLocationFacadeImpl implements CommonsLocationFacade {

    @Autowired
    private CommonsLocationService service;

    @Autowired
    private CommonsLocationDTOAssembler assembler;


    @Value("${commons.client.google.map.key:AIzaSyDSBm6IdI1MJDwob6bSsYdGp0tbbfkKHAo}")
    private String mapsKey;

    @Autowired
    private GoogleMapsClient mapsClient;

    @Override
    public CommonsLocationDTO save(CommonsLocationDTO commonsLocationDTO) {
        return assembler.toDTO(service.save(assembler.fromDTO(commonsLocationDTO)));
    }

    @Override
    public PageDTO<CommonsLocationDTO> getLocationByAddressLabel(String searchTerm, Integer page, Integer size, Boolean cityData,
                                                                 Boolean stateData, Boolean countryData, String sortBy, String direction) {
        Page<CommonsLocation> result = service.getLocationByAddressLabel(searchTerm,
                PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)),cityData,stateData,countryData);
        return new PageDTO<>(result.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public Set<CommonsLocationDTO> getByAddressLabel(String addressLabel) {
        return assembler.toDTOs(service.getByAddressLabel(addressLabel));
    }

    @Override
    public Map<String, CommonsLocationDTO> getByLocationCodesInBulk(Set<String> dataCodes, String sortBy, String direction, String dataCodesField) {
        List<CommonsLocation> result = service.getByLocationCodesInBulk(dataCodes,sortBy,direction,dataCodesField);
        return result.stream()
                .collect(Collectors.toMap(it->{
                    String keyFieldValue = null;
                    if(Objects.equals(dataCodesField,"cityCode")) {
                        keyFieldValue = it.getCityCode();
                    }
                    if(Objects.equals(dataCodesField,"blockCode")) {
                        keyFieldValue = it.getBlockCode();
                    }
                    if(Objects.equals(dataCodesField,"districtCode")) {
                        keyFieldValue = it.getDistrictCode();
                    }
                    else if(Objects.equals(dataCodesField,"stateCode")) {
                        keyFieldValue = it.getStateCode();
                    }
                    else  if(Objects.equals(dataCodesField,"countryCode")) {
                        keyFieldValue = it.getCountryCode();
                    }
                    return keyFieldValue;
                }, it->assembler.toDTO(it),(a, b)->a, LinkedHashMap::new));
    }

    @Override
    public Object searchLocationByGoogle(String query) {
        return mapsClient.searchLocation(query,mapsKey).getBody();
    }

    @Override
    public PageDTO<CommonsLocationDTO> getLocationBySearchTerm(String searchText, String countryCode ,Integer page, Integer size, Boolean cityData, String sortBy, String direction) {
        Page<CommonsLocation> result = service.getLocationBySearchTerm(searchText,countryCode, cityData,
                PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
        return new PageDTO<>(result.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public void reindex() {
       service.reindex();
    }

    @Override
    public Set<String> getLocationHierarchy(String locationCode) {
        Set<String> hierarchy = new LinkedHashSet<>();

        // Add the input location code to the hierarchy
        hierarchy.add(locationCode);

        // Parse the location code to determine its type and value
        Pattern pattern = Pattern.compile("REGION_(CITY|BLOCK|DISTRICT|STATE|COUNTRY)\\.(\\w+)");
        Matcher matcher = pattern.matcher(locationCode);

        if (matcher.find()) {
            String locationType = matcher.group(1);

            // Get the corresponding location from the database
            Set<String> codes = new HashSet<>();
            codes.add(locationCode);
            String field = "";

            if ("CITY".equals(locationType)) {
                field = "cityCode";
                // Get city details
                Map<String, CommonsLocationDTO> cityMap = getByLocationCodesInBulk(codes, "id", "asc", field);
                CommonsLocationDTO cityDTO = cityMap.get(locationCode);

                if (cityDTO != null) {
                    // Add district to hierarchy if it exists
                    if (cityDTO.getDistrictCode() != null) {
                        hierarchy.add(cityDTO.getDistrictCode());
                    }

                    // Add state to hierarchy if it exists
                    if (cityDTO.getStateCode() != null) {
                        hierarchy.add(cityDTO.getStateCode());
                    }

                    // Add country to hierarchy if it exists
                    if (cityDTO.getCountryCode() != null) {
                        hierarchy.add(cityDTO.getCountryCode());
                    }
                }
            } else if ("BLOCK".equals(locationType)) {
                field = "blockCode";
                // Get block details
                Map<String, CommonsLocationDTO> blockMap = getByLocationCodesInBulk(codes, "id", "asc", field);
                CommonsLocationDTO blockDTO = blockMap.get(locationCode);

                if (blockDTO != null) {
                    // Add district to hierarchy if it exists
                    if (blockDTO.getDistrictCode() != null) {
                        hierarchy.add(blockDTO.getDistrictCode());
                    }

                    // Add state to hierarchy if it exists
                    if (blockDTO.getStateCode() != null) {
                        hierarchy.add(blockDTO.getStateCode());
                    }

                    // Add country to hierarchy if it exists
                    if (blockDTO.getCountryCode() != null) {
                        hierarchy.add(blockDTO.getCountryCode());
                    }
                }
            } else if ("DISTRICT".equals(locationType)) {
                field = "districtCode";
                // Get district details
                Map<String, CommonsLocationDTO> districtMap = getByLocationCodesInBulk(codes, "id", "asc", field);
                CommonsLocationDTO districtDTO = districtMap.get(locationCode);

                if (districtDTO != null) {
                    // Add state to hierarchy if it exists
                    if (districtDTO.getStateCode() != null) {
                        hierarchy.add(districtDTO.getStateCode());
                    }

                    // Add country to hierarchy if it exists
                    if (districtDTO.getCountryCode() != null) {
                        hierarchy.add(districtDTO.getCountryCode());
                    }
                }
            } else if ("STATE".equals(locationType)) {
                field = "stateCode";
                // Get state details
                Map<String, CommonsLocationDTO> stateMap = getByLocationCodesInBulk(codes, "id", "asc", field);
                CommonsLocationDTO stateDTO = stateMap.get(locationCode);

                if (stateDTO != null) {
                    // Add country to hierarchy if it exists
                    if (stateDTO.getCountryCode() != null) {
                        hierarchy.add(stateDTO.getCountryCode());
                    }
                }
            }
            // For COUNTRY, we already added it to the hierarchy, so no further processing needed
        }

        return hierarchy;
    }

    @Override
    public CommonsLocationDTO getLocationByCode(String locationCode) {
        // Parse the location code to determine its type and value
        Pattern pattern = Pattern.compile("REGION_(CITY|BLOCK|DISTRICT|STATE|COUNTRY)\\.(\\w+)");
        Matcher matcher = pattern.matcher(locationCode);

        if (matcher.find()) {
            String locationType = matcher.group(1);

            // Get the corresponding location from the database
            Set<String> codes = new HashSet<>();
            String field;

            // Determine the field based on location type
            switch (locationType) {
                case "CITY":
                    field = "cityCode";
                    break;
                case "BLOCK":
                    field = "blockCode";
                    break;
                case "DISTRICT":
                    field = "districtCode";
                    break;
                case "STATE":
                    field = "stateCode";
                    break;
                case "COUNTRY":
                    field = "countryCode";
                    break;
                default:
                    throw new InvalidInputException("Invalid location type: " + locationType);
            }

            // Add the code value to search for
            codes.add(locationCode);

            // Get location details
            List<CommonsLocation> locations = service.getByLocationCodesInBulk(codes, "id", "asc", field);

            // Return the first matching location or null if none found
            if (!locations.isEmpty()) {
                return assembler.toDTO(locations.get(0));
            }
        } else {
            throw new InvalidInputException("Invalid location code format. Expected format: REGION_TYPE.CODE (e.g., REGION_CITY.ABC)");
        }

        return null;
    }
}
