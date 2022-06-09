//package com.lotus.pond.monitor.configuration;
//
//import org.apache.kafka.common.serialization.IntegerSerializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.Map;
//
//import static org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG;
//import static org.apache.kafka.clients.producer.ProducerConfig.*;
//
//@Configuration
//public class KafkaConfiguration {
//
//    Logger logger = LoggerFactory.getLogger(KafkaConfiguration.class);
//
//    @Bean
//    public KafkaTemplate<Integer,String> KafkaTemplate(){
//        return new KafkaTemplate<>(ProducerFactory());
//    }
//
//    @Bean
//    public ProducerFactory<Integer,String> ProducerFactory(){
//        logger.info("PRODUCER FACTORY CREATION");
//        return new DefaultKafkaProducerFactory<>(
//                Map.of(BOOTSTRAP_SERVERS_CONFIG,"pkc-6ojv2.us-west4.gcp.confluent.cloud:9092",
//                        RETRIES_CONFIG,0,
//                        BUFFER_MEMORY_CONFIG,33554432,
//                        KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
//                        VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class
//                ));
//    }
//}
