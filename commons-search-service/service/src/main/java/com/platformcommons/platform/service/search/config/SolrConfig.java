package com.platformcommons.platform.service.search.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(
        basePackages = "com.platformcommons.platform.service.search.domain.repo")
public class SolrConfig {

    @Value("${spring.data.solr.host}")
    protected String solrUrl;

    @Value("${spring.data.solr.connection-timeout:5000}")
    protected int connectionTimeout;

    @Value("${spring.data.solr.socket-timeout:10000}")
    protected int socketTimeout;

    @Value("${spring.data.solr.max-pool-size:125}")
    protected int maximumPoolSize;

    @Bean
    public SolrClient solrClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maximumPoolSize);
        connectionManager.setDefaultMaxPerRoute(maximumPoolSize);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        return new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(connectionTimeout)
                .withSocketTimeout(socketTimeout)
                .withHttpClient(httpClient)
                .build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) {
        return new SolrTemplate(client);
    }

}
