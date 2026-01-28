package com.platformcommons.platform.service.assessment.reporting.application.constant;

public class QueryComponentConstant {

    public static final String QUESTION_FIELD = "MAX(IF(question_id=%s , %s , null )) as `%s`";
    public static final String SECTION_QUESTIONS_FIELD = "MAX(IF( section_questions_id = %s , %s , null )) as `%s`";

    public static final String REPLACE_DELIMITER_FIELD = "MAX(IF(question_id=%s , REPLACE(multi_select_response,'<<::>>' , '%s' ) , null )) as `%s`";
    public static final String SECTION_QUESTIONS_REPLACE_DELIMITER_FIELD = "MAX(IF(section_questions_id=%s , REPLACE(multi_select_response,'<<::>>' , '%s' ) , null )) as `%s`";

    public static final String QUERY =  "SELECT  " +
            " %s " +
            " %s " +
            " %s " +
            "       concat(max(assessee_actor_id), '')        as User, " +
            "       %s " +
            " FROM assesse_response_fact " +
            " WHERE assessment_instance_id in ( :assessmentInstanceId ) and language = :language " +
            " GROUP BY assessee_actor_id ";

    public static final String ALL_RESPONSE__QUERY =  "SELECT  " +
            "       %s " +
            " FROM assesse_response_fact " +
            " WHERE assessment_instance_id = :assessmentInstanceId and language = :language and " +
            "       (assessment_instance_assesse_id in (:aiaIds)   OR -1 in (:aiaIds) )  and " +
            "       (from_unixtime(response_created_at / 1000) BETWEEN :startDate and :endDate ) " +
            " GROUP BY assessment_instance_assesse_id ";

    public static final String ALL_RESPONSE__QUERY_V3 =  "SELECT  " +
            "       %s " +
            " FROM assesse_response_fact " +
            " WHERE assessment_instance_id = :assessmentInstanceId and language = :language " +
            " GROUP BY assessment_instance_assesse_id ";

    public static final String ALL_RESPONSE__QUERY_V2 =  "SELECT  " +
            "       %s " +
            " FROM assesse_response_fact " +
            " WHERE assessment_instance_id = :assessmentInstanceId and language = :language and " +
            "       (assessment_instance_assesse_id in (:aiaIds)   OR -1 in (:aiaIds) ) " +
            " GROUP BY assessment_instance_assesse_id ";
    
    public static final String ASSESSMENT_TITLE =  "'%s'                                      as AssessmentTile,";
    public static final String RESPONSE_DATE    =  "       max(from_unixtime(response_created_at / 1000)) as ResponseDate,";
    public static final String ENTITY    =  "       max(assessed_for_entity_id)               as EntityId, ";


    public static final String OBJECTIVE_RESPONSE_COLUMN = "objective_response";

    public static final String SUBJECTIVE_RESPONSE_COLUMN = "subjective_responses";

    public static final String MULTISELECT_RESPONSE_COLUMN = "multi_select_response";
    public static final String QUESTION_RESPONSE_COLUMN = "question_response";
    public static final String ASSESSE_NAME = " assessment_response_fact.user_display_name as UserName, ";


    public static final String ASSESSE_ID = "       max(assessee_actor_id)               as User, ";
}
