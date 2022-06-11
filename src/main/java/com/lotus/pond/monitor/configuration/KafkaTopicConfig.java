package com.lotus.pond.monitor.configuration;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*this class made to create/modify/delete topic*/
@Configuration
public class KafkaTopicConfig {

    Logger logger = LoggerFactory.getLogger(KafkaTopicConfig.class);

    @Value(value = "${bootstrap.servers}")
    private String bootstrapAddress;

    @Value(value = "${sasl.jaas.config}")
    private String saslJaasConfig;

    @Value(value = "${security.protocol}")
    private String securityProtocol;

    @Value(value = "${sasl.mechanism}")
    private String saslMechanism;

    @Value(value = "${producer.client-id}")
    private String clientId;

    @Bean
    void programmaticallyCreateTopic() {
        logger.info("programmaticallyCreateTopic");
        AdminClient adminClient = KafkaAdminClient.create(configs());
        adminClient.createTopics(Collections.singleton(TopicBuilder.name("programmatically.topic")
                .partitions(4)
                .replicas(3)
                .build()));

    }

    /* delete not existing topics wont stopping application*/
//    @Bean
//    public void deleteTopic() {
//        logger.info("deleteTopic");
//        AdminClient adminClient = KafkaAdminClient.create(configs());
//        adminClient.deleteTopics(Collections.singletonList("programmatically.topic"))
//    }

    Map<String, Object> configs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        configs.put("sasl.mechanism", saslMechanism);
        configs.put("sasl.jaas.config", saslJaasConfig);
        configs.put("security.protocol", securityProtocol);
        return configs;
    }
}
