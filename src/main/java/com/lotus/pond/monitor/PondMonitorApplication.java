package com.lotus.pond.monitor;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class PondMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PondMonitorApplication.class, args);
    }

    @Bean
    NewTopic programmaticallyCreateTopic() {
        return TopicBuilder.name("programmatically.topic")
                .partitions(4)
                .replicas(3)
                .build();
    }
}
