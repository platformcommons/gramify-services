package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.post.domain.ReportFlag;
import com.platformcommons.platform.service.post.dto.ReportFlagDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface ReportFlagFacade {

    Long save(ReportFlagDTO reportFlagDTO );

    ReportFlagDTO update(ReportFlagDTO reportFlagDTO );

    PageDTO<ReportFlagDTO> getAllPage(Integer page, Integer size, String reportType, String marketCode);

    void delete(Long id,String reason);

    ReportFlagDTO getById(Long id);

    ReportFlagDTO patchReportFlag(ReportFlagDTO body);

    Map<String,Set<ReportFlagDTO>> getReportFlagsByLoggedInUser(Set<String> entityTypes);
}
