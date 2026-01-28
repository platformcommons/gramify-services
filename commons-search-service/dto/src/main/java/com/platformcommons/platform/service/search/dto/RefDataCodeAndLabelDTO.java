package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
public class RefDataCodeAndLabelDTO {

    private String dataCode;
    private String label;
}
