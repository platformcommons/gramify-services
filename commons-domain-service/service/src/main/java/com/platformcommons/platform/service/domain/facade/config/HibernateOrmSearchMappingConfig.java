package com.platformcommons.platform.service.domain.facade.config;

import com.platformcommons.platform.service.entity.common.MLText;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.mapper.orm.mapping.HibernateOrmMappingConfigurationContext;
import org.hibernate.search.mapper.orm.mapping.HibernateOrmSearchMappingConfigurer;
import org.hibernate.search.mapper.pojo.mapping.definition.programmatic.ProgrammaticMappingConfigurationContext;
import org.hibernate.search.mapper.pojo.mapping.definition.programmatic.TypeMappingStep;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HibernateOrmSearchMappingConfig implements HibernateOrmSearchMappingConfigurer {


    @Override
    public void configure(HibernateOrmMappingConfigurationContext context) {
        ProgrammaticMappingConfigurationContext mapping = context.programmaticMapping();
        TypeMappingStep nameMapping;

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