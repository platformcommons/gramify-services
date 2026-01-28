package com.platformcommons.platform.service.iam.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
@Slf4j
@Tag(name = "health-controller")
public class HealthController {

    @GetMapping
    public String health() {
        log.info("Health check");
        return "OK";
    }
}
