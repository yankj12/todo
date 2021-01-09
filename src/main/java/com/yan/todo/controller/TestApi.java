package com.yan.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.todo.schema.Task;
import com.yan.todo.vo.ResponseVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/demo")
public class TestApi {

	@Autowired
	public ObjectMapper objectMapper;
	
	@Autowired
	public KafkaTemplate<String, String> kafkaTemplate;
	
	@GetMapping("/kafka/callbackOne/{message}")
	public void sendMessage2(@PathVariable("message") String message) {
	    kafkaTemplate.send("test", message).addCallback(success -> {
	        // 消息发送到的topic
	        String topic = success.getRecordMetadata().topic();
	        // 消息发送到的分区
	        int partition = success.getRecordMetadata().partition();
	        // 消息在分区内的offset
	        long offset = success.getRecordMetadata().offset();
	        log.info("发送消息成功:" + topic + "-" + partition + "-" + offset);
	    }, failure -> {
	    	log.info("发送消息失败:" + failure.getMessage());
	    });
	}
	
}
