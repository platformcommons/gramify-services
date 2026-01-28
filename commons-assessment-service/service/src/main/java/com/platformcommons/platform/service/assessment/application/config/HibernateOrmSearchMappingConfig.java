package com.platformcommons.platform.service.assessment.application.config;

import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.entity.common.MLText;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.orm.mapping.HibernateOrmMappingConfigurationContext;
import org.hibernate.search.mapper.orm.mapping.HibernateOrmSearchMappingConfigurer;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.programmatic.ProgrammaticMappingConfigurationContext;
import org.hibernate.search.mapper.pojo.mapping.definition.programmatic.TypeMappingStep;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HibernateOrmSearchMappingConfig implements HibernateOrmSearchMappingConfigurer {


    @Override
    public void configure(HibernateOrmMappingConfigurationContext context) {
        ProgrammaticMappingConfigurationContext mapping = context.programmaticMapping();
        TypeMappingStep instanceMapping,assessmentMapping,nameMapping,refDataMapping,questionMapping;

        instanceMapping = mapping.type( AssessmentInstance.class );
        AssessmentInstanceConfig: {
            instanceMapping.indexed();

            instanceMapping.property("id")
                           .genericField();
            instanceMapping.property("assessment")
                           .indexedEmbedded();
            instanceMapping.property("createdByUser")
                            .genericField();
            instanceMapping.property("createdTimestamp")
                           .genericField().sortable(Sortable.YES);
            instanceMapping.property("startDate")
                            .genericField();
            instanceMapping.property("endDate")
                            .genericField();
            instanceMapping.property("isPublic")
                            .genericField();
            instanceMapping.property("scheduleStatus")
                    .keywordField();

        }

        AssessmentConfig:{
            assessmentMapping = mapping.type( Assessment.class );
            assessmentMapping.property("domain")
                             .keywordField();
            assessmentMapping.property("subDomain")
                             .keywordField();

            assessmentMapping.property("assessmentType")
                    .keywordField();
            assessmentMapping.property("assessmentName")
                             .indexedEmbedded()
                             .indexingDependency()
                             .reindexOnUpdate(ReindexOnUpdate.SHALLOW);
        }

        QuestionConfig: {
            questionMapping = mapping.type( Question.class );
            questionMapping.indexed();

            questionMapping.property("id")
                           .genericField();
            questionMapping.property("createdTimestamp")
                            .genericField()
                            .sortable(Sortable.YES);
            questionMapping.property("questionText")
                            .indexedEmbedded()
                            .indexingDependency()
                            .reindexOnUpdate(ReindexOnUpdate.SHALLOW);
            questionMapping.property("questionType")
                           .keywordField();
            questionMapping.property("questionSubtype")
                           .keywordField();
            questionMapping.property("domain")
                            .keywordField();
            questionMapping.property("subDomain")
                            .keywordField();


        }

        MLTextConfig:{
            nameMapping= mapping.type( MLText.class );

            nameMapping.property("text")
                       .fullTextField()
                    .searchable(Searchable.YES);

            nameMapping.property("languageCode")
                       .keywordField();
        }
    }

}