package com.lotus.pond.monitor.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    @Value(value = "${bootstrap.servers}")
    private String bootstrapAddress;

    @Value(value = "${sasl.jaas.config}")
    private String saslJaasConfig;

    @Value(value = "${security.protocol}")
    private String securityProtocol;

    @Value(value = "${sasl.mechanism}")
    private String saslMechanism;

    @Value(value = "${spring.kafka.producer.client-id}")
    private String clientId;

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        logger.info("producerFactory");
        Map<String, Object> configProps = configs();
        return new DefaultKafkaProducerFactory<>(configProps);
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

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        logger.info("kafkaTemplate");
        return new KafkaTemplate<>(producerFactory());
    }
}
