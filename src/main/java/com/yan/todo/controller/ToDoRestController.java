package com.yan.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yan.todo.mapper.TaskMapper;
import com.yan.todo.schema.Task;
import com.yan.todo.vo.DataGridVo;

@RestController
public class ToDoRestController {
	
	@Autowired
	private TaskMapper taskMapper;
	
	@RequestMapping("/taskdatagrid")
	@ResponseBody
	public DataGridVo imagesDataGrid(Integer page, Integer rows, String validStatus) {
		DataGridVo dataGrid = new DataGridVo();
		dataGrid.setSuccess(false);
		
		int offset = 0;
		int pageSize = 10;
		
		if(rows > 0){
			pageSize = rows;
		}
		
		if(page > 0){
			offset = (page - 1) * pageSize;
		}
		
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("validStatus", validStatus);
		condition.put("offset", offset);
		condition.put("pageSize", pageSize);
		
		List<Task> tasks = taskMapper.findTaskListByCondition(condition);
		Long total = taskMapper.countTaskByCondition(condition);
		
		dataGrid.setSuccess(true);
		dataGrid.setErrorMsg("");
		dataGrid.setTotal(total.intValue());
		dataGrid.setRows(tasks);
		
		return dataGrid;
	}
}
