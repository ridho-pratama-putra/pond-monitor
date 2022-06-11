package com.lotus.pond.monitor.configuration;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*this class made to create/modify/delete topic*/
@Configuration
public class KafkaTopicConfig {

    Logger logger = LoggerFactory.getLogger(KafkaTopicConfig.class);

    @Value(value = "${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.properties.sasl.jaas.config}")
    private String saslJaasConfig;

    @Value(value = "${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Value(value = "${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;

    @Value(value = "${spring.kafka.producer.client-id}")
    private String clientId;

//    /*
//    * ENABLE this will allow Topic Builder to create new topic, but this wont contribute for deletion
//    */
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        logger.info("setup kafka admin");
//        Map<String, Object> configs = configs();
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    NewTopic programmaticallyCreateTopic() {
//        logger.info("programmaticallyCreateTopic");
//        return TopicBuilder.name("programmatically.topic")
//                .partitions(4)
//                .replicas(3)
//                .build();
//    }

    /* delete not existing topics wont stopping application*/
    @Bean
    public void deleteTopic() {
        logger.info("deleteTopic");
        Admin admin = Admin.create(configs());
        admin.deleteTopics(Collections.singletonList("programmatically.topic"));
    }

    Map<String, Object> configs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        configs.put("sasl.mechanism", saslMechanism);
        configs.put("sasl.jaas.config", saslJaasConfig);
        configs.put("security.protocol", securityProtocol);
        return configs;
    }
}
