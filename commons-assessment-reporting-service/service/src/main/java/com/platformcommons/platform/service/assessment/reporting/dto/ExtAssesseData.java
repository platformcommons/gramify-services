package com.platformcommons.platform.service.assessment.reporting.dto;


import com.google.common.base.Strings;
import lombok.*;

import java.util.*;

public class ExtAssesseData {

    private final Boolean addPortfolioId;
    private final Boolean isCropPlan;
    private final Map<Long,ExtData> cropPlanName;

    public ExtAssesseData(boolean isCropPlan, boolean addPortfolioId) {
        this.isCropPlan = isCropPlan;
        this.cropPlanName = new HashMap<>();
        this.addPortfolioId = addPortfolioId;
    }



    @Getter @Setter
    static class ExtData {

        private String cropPlanName;
        private String fpoName;
        private String fpoId;
        private String portfolioLineItemId;

        @Builder
        public ExtData(String cropPlanName, String fpoName, String fpoId, String portfolioLineItemId) {
            this.cropPlanName = cropPlanName;
            this.fpoName = fpoName;
            this.fpoId = fpoId;
            this.portfolioLineItemId = portfolioLineItemId;
        }
    }


    public void add(List<Map<String,Object>> extAssesseData){
        for (Map<String, Object> map : extAssesseData) {

            Long assesseId = map.get("assesseeId")!=null?Long.parseLong(map.get("assesseeId").toString()):null;
            String cropPlanName = Strings.isNullOrEmpty((String) map.get("cropPlanName"))?null:Objects.toString(map.get("cropPlanName"));
            String fpoName = Strings.isNullOrEmpty((String) map.get("fpoName"))?null:Objects.toString(map.get("fpoName"));
            String fpoId = Strings.isNullOrEmpty((String) map.get("fpoId"))?null:Objects.toString(map.get("fpoId"));
            String portfolioLineItemId = Strings.isNullOrEmpty((String) map.get("portfolioLineItemId"))?null:Objects.toString(map.get("portfolioLineItemId"));

            if(assesseId!=null){
                this.cropPlanName.put(assesseId,ExtData.builder().cropPlanName(cropPlanName).fpoName(fpoName).fpoId(fpoId).portfolioLineItemId(portfolioLineItemId).build());
            }
        }
    }

    public String getCropPlanName(Long assesseId){
        return cropPlanName.get(assesseId).getCropPlanName();
    }
    public String getFpoName(Long assesseId) {
        return cropPlanName.get(assesseId).getFpoName();
    }
    public String getFpoId(Long assesseId) {
        return cropPlanName.get(assesseId).getFpoId();
    }
    public Boolean isCropPlanAssessment(){
        return isCropPlan;
    }
    public Boolean toAddPortfolioId(){
        return this.addPortfolioId;
    }
    public String getPortfolioLineItemId(Long assesseId) {
        return cropPlanName.get(assesseId).getPortfolioLineItemId();
    }
    public Set<Long> getAssesseIds() {
        return cropPlanName.keySet();
    }
}
