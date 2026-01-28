package com.platformcommons.platform.service.assessment.reporting.application.utility;

import com.platformcommons.platform.service.assessment.dto.AiaDefaultResponseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Slf4j
public class SectionQuestionUtil {

    public static void setSectionQuestion(AssessmentInstanceAssesseDTO assesseDTO){
        for (AiaDefaultResponseDTO aiaDefaultResponseDTO : assesseDTO.getAiadefaultResponseList()) {
            if(aiaDefaultResponseDTO.getChildQuestionId()!=null){
                try {
                    Field field = aiaDefaultResponseDTO.getClass().getDeclaredField("sectionQuestion");
                    ReflectionUtils.makeAccessible(field);
                    field.set(aiaDefaultResponseDTO, null);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("Error while setting section question",e);
                }
            }
        }
    }

}
