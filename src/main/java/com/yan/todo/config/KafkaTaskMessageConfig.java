package com.yan.todo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class KafkaTaskMessageConfig {

	@Value("${spring.kafka.riskpremiumsimu.consumer.bootstrap-servers}")
	private String bootstrap_servers;
	@Value("${spring.kafka.riskpremiumsimu.consumer.topics}")
	private String topics;
	@Value("${spring.kafka.riskpremiumsimu.consumer.group-id}")
	private String group_id;
	@Value("${spring.kafka.riskpremiumsimu.consumer.auto-offset-reset}")
	private String auto_offset_reset;
	@Value("${spring.kafka.riskpremiumsimu.consumer.fetch-min-size}")
	private String fetch_min_size;
	@Value("${spring.kafka.riskpremiumsimu.consumer.max-poll-records}")
	private String max_poll_records;
	@Value("${spring.kafka.riskpremiumsimu.consumer.enable-auto-commit}")
	private String enable_auto_commit;
	@Value("${spring.kafka.riskpremiumsimu.consumer.auto-commit-interval}")
	private String auto_commit_interval;
	@Value("${spring.kafka.riskpremiumsimu.consumer.value-deserializer}")
	private String value_deserializer;
	@Value("${spring.kafka.riskpremiumsimu.consumer.key-deserializer}")
	private String key_deserializer;

	@Bean("messageKafkaFactory")
	public KafkaListenerContainerFactory messageKafkaFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(messageKafkaConsumerFactory());
		factory.setConcurrency(1);
		factory.setBatchListener(true);
		// Changes Between 2.1 and 2.2
		// The AckMode enum has been moved from AbstractMessageListenerContainer to ContainerProperties.
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		return factory;
	}

	@Bean
	public ConsumerFactory messageKafkaConsumerFactory() {

		return new DefaultKafkaConsumerFactory(messageKafkaConfig());
	}

	@Bean
	public Map<String, Object> messageKafkaConfig() {
		HashMap<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, group_id);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, auto_offset_reset);
		config.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetch_min_size);
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, max_poll_records);
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enable_auto_commit);
		config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, auto_commit_interval);
		// key和value的序列化方式
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, value_deserializer);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, key_deserializer);
		return config;
	}
}
