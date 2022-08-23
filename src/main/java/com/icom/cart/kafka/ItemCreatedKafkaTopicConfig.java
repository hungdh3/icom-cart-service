package com.icom.cart.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ItemCreatedKafkaTopicConfig {

    @Value("${icom.cart.item-created-topic.name}")
    private String ITEM_CREATED_KAFKA_TOPIC_NAME;
    @Value("${icom.cart.item-created-topic.partitions}")
    private Integer ITEM_CREATED_KAFKA_TOPIC_PARTITIONS;
    @Value("${icom.cart.item-created-topic.replicas}")
    private Integer ITEM_CREATED_KAFKA_TOPIC_REPLICAS;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(ITEM_CREATED_KAFKA_TOPIC_NAME)
                .partitions(ITEM_CREATED_KAFKA_TOPIC_PARTITIONS)
                .replicas(ITEM_CREATED_KAFKA_TOPIC_REPLICAS)
                .build();
    }
}
