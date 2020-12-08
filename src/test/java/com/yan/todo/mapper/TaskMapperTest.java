package com.yan.todo.mapper;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.yan.todo.schema.Task;

import lombok.extern.slf4j.Slf4j;

// Junit4
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = { ToDoApplication.class })

@Slf4j
// Junit5
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("TaskMapperTest")
public class TaskMapperTest {

	@Autowired
	public TaskMapper taskMapper;
	
	//@Test
	public void testInsertTask() throws Exception {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Random random = new Random();
		
		for(int i=0;i<25;i++) {
			int randomId = random.nextInt(1000);
			
			Task task = new Task();
			task.setTitle("测试任务" + randomId);
			task.setEmergencyFlag("0");
			task.setContent("这是一条测试任务的内容描述");
			task.setUserCode("张三");
			task.setAssignTo("张三");
			task.setDeadLine(sdformat.parse("2021-01-01"));
			task.setStartTimeExpected(sdformat.parse("2020-12-14"));
			task.setEndTimeExpected(sdformat.parse("2020-12-18"));
			task.setFinishFlag("0");
			task.setRemark("这是个备注字段");
			task.setValidStatus("1");
			task.setInsertTime(new Date());
			task.setUpdateTime(new Date());
			
			taskMapper.insertTask(task);
		}
	}

	//@Test
	public void testFindTaskByPK() {
		Task task = taskMapper.findTaskByPK(30L);
		log.info("id:{}, title:{}", task.getId(), task.getTitle());
	}

	//@Test
	public void testFindTaskListByCondition() {
	}

	//@Test
	public void testCountTaskByCondition() {
	}

	//@Test
	public void testUpdateTaskByPK() {
	}

	//@Test
	public void testUpdateTaskValidStatusByPK() {
	}

	//@Test
	public void testUpdateTaskFinishFlagByPK() {
	}

	//@Test
	public void testUpdateTaskEmergencyFlagByPK() {
	}

	//@Test
	public void testUpdateTaskDeadLineByPK() {
	}

}
