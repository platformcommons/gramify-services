package com.platformcommons.platform.service.assessment.reporting.application.config;

import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;

//@Configuration
//@EnableBatchProcessing
//@ComponentScan(basePackages = "org.springframework.batch")
public class BatchConfiguration {

//    @Autowired
    private PlatformTransactionManager transactionManager;
//    @Autowired
//    private JobRepository jobRepository;

//    @Autowired
    private EntityManager entityManager;


}
