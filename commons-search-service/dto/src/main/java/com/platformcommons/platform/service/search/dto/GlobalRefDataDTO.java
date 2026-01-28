package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
public class GlobalRefDataDTO {

    private String dataCode;

    private String classCode;

    private String label;

    private String languageCode;

    private String notes;

    private String aliasId;

}
