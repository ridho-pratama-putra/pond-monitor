package com.lotus.pond.monitor;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class Producer {
    Logger logger = LoggerFactory.getLogger(Producer.class);

    private final KafkaTemplate<String, String> template;

    Faker faker;

    @Value(value = "${producer.topic.name.pond}")
    String topicName;


    @EventListener(ApplicationStartedEvent.class)
    public void sendMessage() {
        logger.info("SENDING MESSAGE");
        faker = Faker.instance();
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));
        Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.weather().temperatureCelsius()));
        Flux.zip(interval, quotes).map(it -> {
                    ListenableFuture<SendResult<String, String>> future = template.send(topicName, "temperature", it.getT2());
                    future.addCallback(new ListenableFutureCallback<>() {

                        @Override
                        public void onSuccess(SendResult<String, String> result) {
                            System.out.println("Sent message=[" + it.getT2() +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
                        }

                        @Override
                        public void onFailure(Throwable ex) {
                            System.out.println("Unable to send message=["
                                    + it.getT2() + "] due to : " + ex.getMessage());
                        }
                    });
                    return it;
                }
        ).blockLast();
    }
}
