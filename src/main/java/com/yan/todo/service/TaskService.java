package com.yan.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yan.todo.mapper.TaskMapper;
import com.yan.todo.schema.Task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskService {

	@Autowired
	public TaskMapper taskMapper;
	
	@Autowired
	public KafkaSender kafkaSender;
	
	public void saveTask(Task task) {
		log.info(String.format("准备保存一个任务:%s", task.getTitle()));
		taskMapper.insertTask(task);
		
		log.info(String.format("任务保存完成:%s", task.getTitle()));
		// 将任务信息放入消息队列，进行后续的邮件通知服务
		kafkaSender.sendMessage(String.format("task_%d", task.getId()), task);
	}
}
