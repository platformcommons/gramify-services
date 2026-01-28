package com.platformcommons.platform.service.person.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.Map;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import com.platformcommons.platform.service.dto.commons.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Validated
public class IeDeliveryModeDTO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    private String code;
    private String type;

    @Builder
    public IeDeliveryModeDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt,Long id, String code, String type) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.code = code;
        this.type = type;
    }
}
