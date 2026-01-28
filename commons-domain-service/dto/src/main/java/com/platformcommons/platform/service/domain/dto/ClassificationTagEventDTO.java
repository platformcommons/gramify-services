package com.platformcommons.platform.service.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassificationTagEventDTO {

    private Long id;

    private String classificationCode;

    private Set<MLTextDTO> names;

    private String context;

    private String contextType;

    @Builder
    public ClassificationTagEventDTO(Long id, String classificationCode, Set<MLTextDTO> names, String context, String contextType) {
        this.id = id;
        this.classificationCode = classificationCode;
        this.names = names;
        this.context = context;
        this.contextType = contextType;
    }
}
