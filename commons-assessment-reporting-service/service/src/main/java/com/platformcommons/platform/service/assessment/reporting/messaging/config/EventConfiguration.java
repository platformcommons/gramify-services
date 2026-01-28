package com.platformcommons.platform.service.assessment.reporting.messaging.config;

import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(
        name = {"commons.platform.event.enabled"},
        havingValue = "true"
)
public class EventConfiguration {

    @Value("${commons.platform.event.servers}")
    private String bootstrapServers;


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AssesseDimHierarchyResolvedEventDTO> batchKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AssesseDimHierarchyResolvedEventDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(batchConsumerFactory());
        factory.setBatchListener(true);
        return factory;
    }

    public ConsumerFactory<String, AssesseDimHierarchyResolvedEventDTO> batchConsumerFactory() {
        Map<String, Object> props = stringConsumerConfig(bootstrapServers);
        JsonDeserializer<AssesseDimHierarchyResolvedEventDTO> valueDeserializer = new JsonDeserializer<>(AssesseDimHierarchyResolvedEventDTO.class);
        valueDeserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                valueDeserializer
        );
    }

    public Map<String, Object> stringConsumerConfig(String bootstrapServers) {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1000*5);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1024*50);

        return props;
    }

    public ProducerFactory<String, AssesseDimHierarchyResolvedEventDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put("enable.idempotence", "true");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, AssesseDimHierarchyResolvedEventDTO> assesseDimHierarchyResolvedEventKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
