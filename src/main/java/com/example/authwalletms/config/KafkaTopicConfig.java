package com.example.authwalletms.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name("user-register")
                .partitions(8)
                .replicas(7)
                .build();
    }

    @Bean
    public NewTopic newTopicVerifiedUser() {
        return TopicBuilder.name("verified-user")
                .partitions(8)
                .replicas(7)
                .build();
    }
}
