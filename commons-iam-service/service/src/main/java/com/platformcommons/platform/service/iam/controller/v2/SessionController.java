package com.platformcommons.platform.service.iam.controller.v2;

import com.platformcommons.platform.security.filter.session.CurrentExecutiveDTO;
import com.platformcommons.platform.service.iam.facade.v2.SessionFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "session-controller")
@Slf4j
public class SessionController {

    private SessionFacade sessionFacade;

    @GetMapping("/session/me")
    public ResponseEntity<CurrentExecutiveDTO> getTLDSessionDetails(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(sessionFacade.getLoggedInUserSessionDetails());
    }
}
