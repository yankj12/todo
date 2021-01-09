package com.yan.todo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.yan.todo.schema.Task;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ToDoRestControllerTest")
class ToDoRestControllerTest {
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	void testSaveTask() throws Exception {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		
		Task task = new Task();
		task.setTitle("测试api");
		task.setEmergencyFlag("0");
		task.setContent("这是一条测试api任务的内容描述");
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
		
		mockMvc.perform(MockMvcRequestBuilders.post("/todo/api/task/saveTask", task).accept(MediaType.APPLICATION_JSON))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	    	.andDo(MockMvcResultHandlers.print())
	    	.andReturn();
	}

}
