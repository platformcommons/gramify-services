package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmaChannelProductStatusDTO extends BaseDTO {

    public Long tmaChannelProductId;

    public String tmaChannelProductStatus;

    @Builder
    public TmaChannelProductStatusDTO(Long tmaChannelProductId, String tmaChannelProductStatus) {
        this.tmaChannelProductId = tmaChannelProductId;
        this.tmaChannelProductStatus = tmaChannelProductStatus;
    }
}
