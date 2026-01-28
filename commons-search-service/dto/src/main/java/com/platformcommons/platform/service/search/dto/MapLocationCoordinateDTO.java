package com.platformcommons.platform.service.search.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapLocationCoordinateDTO {

    private String code;

    private String label;

    private Long count;

    private Double latitude;

    private Double longitude;

    private String latitudeLongitude;
}
