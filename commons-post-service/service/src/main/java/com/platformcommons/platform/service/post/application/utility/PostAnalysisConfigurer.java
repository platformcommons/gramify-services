package com.platformcommons.platform.service.post.application.utility;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class PostAnalysisConfigurer implements LuceneAnalysisConfigurer {

    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {

        context.analyzer( "post_indexing" ).custom()
                .tokenizer( "standard" )
                .charFilter( "htmlStrip" )
                .tokenFilter( "lowercase" )
                .tokenFilter( "snowballPorter" )
                .param( "language", "English" )
                .tokenFilter( "asciiFolding" )
                .tokenFilter("stop").param("words","stopwords.txt");

        context.analyzer( "post_searching" ).custom()
                .tokenizer( "standard" )
                .charFilter( "htmlStrip" )
                .tokenFilter( "lowercase" )
                .tokenFilter( "snowballPorter" )
                .param( "language", "English" )
                .tokenFilter( "asciiFolding" );
    }
}