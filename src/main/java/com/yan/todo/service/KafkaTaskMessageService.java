package com.yan.todo.service;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaTaskMessageService {

	@Autowired
    ObjectMapper objectMapper;
    
    @KafkaListener(topics = {"${spring.kafka.message.consumer.topics}"}, containerFactory = "messageKafkaFactory")
    public void consumerForriskPremiumSimu(List<ConsumerRecord> recordList, Acknowledgment ack) {
        
    	try {
			if (recordList != null) {
				for (ConsumerRecord record : recordList) {
					// TODO 处理每一条信息
					
				}
				ack.acknowledge();
				log.info("消费kafka信息成功，合计{}条", recordList.size());
			}
		} catch (Exception e) {
			log.error("消费kafka信息出现异常", e);
		}
    	
    }
}
