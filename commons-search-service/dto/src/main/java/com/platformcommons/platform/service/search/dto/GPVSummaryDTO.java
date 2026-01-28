package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GPVSummaryDTO extends BaseDTO {

    public Long variety;

    public String productIds;

    public String productName;

    public Long channel;

    public String traderIds;

    public Long traderCount;

    @Builder
    public GPVSummaryDTO(Long variety, String productIds, String productName, Long channel, String traderIds, Long traderCount) {
        this.variety = variety;
        this.productIds = productIds;
        this.productName = productName;
        this.channel = channel;
        this.traderIds = traderIds;
        this.traderCount = traderCount;
    }
}
