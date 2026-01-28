package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.ReportFlag;


import com.platformcommons.platform.service.post.domain.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.post.application.ReportFlagService;
import com.platformcommons.platform.service.post.domain.repo.ReportFlagRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;

import static com.platformcommons.platform.service.post.application.constant.PostConstant.*;


@Service
public class ReportFlagServiceImpl implements ReportFlagService {


    @Autowired
    private ReportFlagRepository repository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public Long save(ReportFlag reportFlag ){
        reportFlag.init();
        return repository.save(reportFlag).getId();
    }



    @Override
    public ReportFlag update(ReportFlag reportFlag) {
       ReportFlag fetchedReportFlag = repository.findById(reportFlag.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request ReportFlag with  %d  not found",reportFlag.getId())));

       fetchedReportFlag.update(reportFlag);
       return repository.save(fetchedReportFlag);
    }


    @Override
    public Page<ReportFlag> getByPage(Integer page, Integer size, String reportType, String marketCode) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByFilters(reportType, marketCode, pageable);
    }

    @Override
    public void deleteById(Long id, String reason) {
        ReportFlag fetchedReportFlag = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request ReportFlag with  %d  not found",id)));
        fetchedReportFlag.deactivate(reason);
        repository.save(fetchedReportFlag);
    }


    @Override
    public ReportFlag getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request ReportFlag with  %d  not found",id)));
    }


    @Override
    public Set<ReportFlag>  getReportFlagsByLoggedInUser(Set<String> reportedOnEntityTypes){
        Long reportedByEntityId= PlatformSecurityUtil.getCurrentUserId();
        String reportedByEntityType = PlatformSecurityUtil.getActorContext().getActorContext();
        return  repository.findByReportedOnEntityTypeAndReportedByEntityId(reportedOnEntityTypes,reportedByEntityId,reportedByEntityType );
    }

    @Override
    public ReportFlag patchReportFlag(ReportFlag body) {
        ReportFlag existingReportFlag = repository.findById(body.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("ReportFlag with id %d not found", body.getId())));

        existingReportFlag.update(body);

        Long postId = existingReportFlag.getReportedOnEntityId();
        if (postId != null) {
            Post post = postRepository.getById(postId);

            String reportStatus = existingReportFlag.getReportStatus();
            if (REPORT_STATUS_APPROVED.equals(reportStatus)) {
                post.setIsReported(Boolean.FALSE);
            } else if (REPORT_STATUS_DECLINED.equals(reportStatus)|| REPORT_STATUS_REJECTED.equals(reportStatus)|| REPORT_STATUS_REPORTED.equals(reportStatus)) {
                post.setIsReported(Boolean.TRUE);
            }
            postRepository.save(post);
        }

        return repository.save(existingReportFlag);
    }

}
