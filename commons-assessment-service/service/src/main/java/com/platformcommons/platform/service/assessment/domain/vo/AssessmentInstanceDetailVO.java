package com.platformcommons.platform.service.assessment.domain.vo;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class AssessmentInstanceDetailVO extends BaseDTO {

    private String assessmentName;
    private String domainCode;
    private String subDomainCode;
    private Long assessmentInstanceId;
    private String assessmentImage;
    private Long noOfQuestion;
    private String author;
    private Boolean editable;

    @Builder
    public AssessmentInstanceDetailVO(String assessmentName, String domainCode,String subDomainCode, Long assessmentInstanceId, String assessmentImage, Long noOfQuestion, String author,Boolean editable) {
        this.assessmentName = assessmentName;
        this.domainCode = domainCode;
        this.assessmentInstanceId = assessmentInstanceId;
        this.assessmentImage = assessmentImage;
        this.noOfQuestion = noOfQuestion;
        this.author = author;
        this.subDomainCode = subDomainCode;
        this.editable=editable;
    }
    @Builder
    public AssessmentInstanceDetailVO(String assessmentName, String domainCode,String subDomainCode, Long assessmentInstanceId, String assessmentImage, Long noOfQuestion, String author) {
        this.assessmentName = assessmentName;
        this.domainCode = domainCode;
        this.assessmentInstanceId = assessmentInstanceId;
        this.assessmentImage = assessmentImage;
        this.noOfQuestion = noOfQuestion;
        this.author = author;
        this.subDomainCode = subDomainCode;
    }

    public static AssessmentInstanceDetailVO mapToAssessmentInstanceDetailVO(Map<String, Object> map) {
        return AssessmentInstanceDetailVO.builder()
                .assessmentName(map.get("assessmentName")!=null?map.get("assessmentName").toString():null)
                .domainCode(map.get("domainCode")!=null?map.get("domainCode").toString():null)
                .assessmentInstanceId(map.get("assessmentInstanceId")!=null?((Number)map.get("assessmentInstanceId")).longValue():null)
                .assessmentImage(map.get("assessmentImage")!=null?map.get("assessmentImage").toString():null)
                .noOfQuestion(map.get("noOfQuestion")!=null?((Number)map.get("noOfQuestion")).longValue():null)
                .author(map.get("author")!=null?map.get("author").toString():null)
                .subDomainCode(map.get("subDomainCode")!=null?map.get("subDomainCode").toString():null)

                .build();
    }
}
