package com.yan.todo.service;

import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaSender {

	@Value("${kafka.task.topic}")
	public String topic;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@Autowired
	public KafkaTemplate<String, String> kafkaTemplate;
	
	//@Async("customerAsyncExecutor")
	public void sendMessage(String key, Object object){
		try {
			String message = objectMapper.writeValueAsString(object);
			kafkaTemplate.send(topic, message);
		} catch (JsonProcessingException e) {
			log.error("向kafka发送信息失败", e);
		}
	}
	
}
