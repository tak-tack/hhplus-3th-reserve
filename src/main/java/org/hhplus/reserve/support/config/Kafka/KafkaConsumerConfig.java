package org.hhplus.reserve.support.config.Kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
//@Data
public class KafkaConsumerConfig  {

    private final KafkaConfig kafkaConfig;

    public KafkaConsumerConfig (KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    // Kafka 컨슈머 팩토리 빈 정의
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        // kafka 컨슈머 설정을 위한 맵 생성
        Map<String, Object> props = new HashMap<>();
        // 
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getConsumer().getGroupIds().getSuccess());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConfig.getConsumer().getEnableAutoCommit());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfig.getConsumer().getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaConfig.getConsumer().getMaxPollRecords());

        // Error handling deserializer
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
