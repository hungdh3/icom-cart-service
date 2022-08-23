package com.icom.cart.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class ItemCreatedKafkaProducer {
    @Value("${icom.cart.item-created-topic.name}")
    private String ITEM_CREATED_KAFKA_TOPIC_NAME;
    @Value("${icom.cart.item-created-topic.short-timeout-ms}")
    private Integer ITEM_CREATED_KAFKA_TOPIC_TIMEOUT_MS;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg, String msgKey) throws ExecutionException, InterruptedException, TimeoutException {
        Message<String> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, ITEM_CREATED_KAFKA_TOPIC_NAME)
                .setHeader(KafkaHeaders.MESSAGE_KEY, msgKey)
                //.setHeader("X-Custom-Header", "Sending Custom Header with Spring Kafka")
                .build();

        //@TODO: using Non Blocking (Async) https://docs.spring.io/spring-kafka/reference/html/#sending-messages
        kafkaTemplate.send(message).get(ITEM_CREATED_KAFKA_TOPIC_TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }

}
