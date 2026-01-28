package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.ReportFlag;

import org.springframework.data.domain.Page;

import java.util.*;

public interface ReportFlagService {

    Long save(ReportFlag reportFlag );

    ReportFlag update(ReportFlag reportFlag );

    Page<ReportFlag> getByPage(Integer page, Integer size, String reportType, String marketCode);

    void deleteById(Long id,String reason);

    ReportFlag getById(Long id);


    Set<ReportFlag>  getReportFlagsByLoggedInUser(Set<String> reportedOnEntityTypes);

    ReportFlag patchReportFlag(ReportFlag reportFlag);
}
