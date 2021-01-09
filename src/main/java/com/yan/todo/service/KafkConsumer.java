package com.yan.todo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.todo.schema.Task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkConsumer {

	@Autowired
	public ObjectMapper objectMapper;
	
	@KafkaListener(topics= {"${kafka.task.topic}"})
	public void onMessage(ConsumerRecord<?, ?> record) {
		log.info("简单消费:{}-{}-{}", record.topic(), record.partition(), record.value());
		
		try {
			Task task = objectMapper.readValue(record.value().toString(), Task.class);
			// TODO 向指定的收件人发送任务邮件
			
			log.info(String.format("向[%s]发送任务邮件，任务：%s", task.getAssignTo(), task.getTitle()));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("向指定的收件人发送任务邮件失败", e);
		} 
	}
}
