package org.hhplus.reserve.support.config.Kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("kafka")
@Data
public class KafkaConfig {

    private String bootstrapServers;
    private Producer producer;
    private Consumer consumer;
    private Topic topic;

    @Data
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
        private String acks; // 추가된 속성: producer.acks
    }

    @Data
    public static class Consumer {
        private GroupIds groupIds;

        @Data
        public static class GroupIds {
            private String success; // 추가된 속성: consumer.group-ids.success
        }

        private String keyDeserializer;
        private String valueDeserializer;
        private String maxPollRecords;
        private String enableAutoCommit;
        private String autoOffsetReset;
    }

    @Data
    public static class Topic {
        private String paymentSuccess; // 추가된 속성: topic.payment-success
    }
}