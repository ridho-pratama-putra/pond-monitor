package com.lotus.pond.monitor.configuration;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class Producer {
    Logger logger = LoggerFactory.getLogger(Producer.class);

    private final KafkaTemplate<Integer, String> template;

    Faker faker;

    @EventListener(ApplicationStartedEvent.class)
    public void sendMessage() {
        logger.info("SENDING MESSAGE");
        faker = Faker.instance();
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

        Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.weather().temperatureCelsius()));
        Flux.zip(interval, quotes).map(it -> {
                    System.out.println("ZIP RUNNING WITH T1:" + it.getT1() + " T2:" + it.getT2() );
                    return template.send("pond", faker.random().nextInt(42), it.getT2());
                }
        ).blockLast();
    }
}
