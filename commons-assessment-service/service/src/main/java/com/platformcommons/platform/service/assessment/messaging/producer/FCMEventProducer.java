package com.platformcommons.platform.service.assessment.messaging.producer;

import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.notification.resolver.dto.FCMRRNotificationEventRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FCMEventProducer {
    private final Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate;

    public FCMEventProducer(@Autowired(required = false) @Qualifier("jsonMessageTemplate") KafkaTemplate<String, EventDTO> kafkaTemplate) {
        this.kafkaTemplate = Optional.ofNullable(kafkaTemplate);
    }

    public void triggerTopic(FCMRRNotificationEventRequestDTO fcmrrNotificationEventRequestDTO, Boolean withContext) {
        withContext =withContext!= null ? withContext : true;
        Boolean finalWithContext = withContext;
        this.kafkaTemplate.ifPresent((kafkaTemplate) -> {
            EventDTO<FCMRRNotificationEventRequestDTO> eventDTO =  EventDTO.<FCMRRNotificationEventRequestDTO>builder().requestPayload(fcmrrNotificationEventRequestDTO).withContext(finalWithContext).build();
            kafkaTemplate.send("FCM_SERVICE.RR.SEND_NOTIFICATION_TOPIC", eventDTO);
        });
    }

}

