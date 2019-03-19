package com.yan.todo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yan.todo.schema.Task;

@Mapper
public interface TaskMapper {

	void insertTask(Task task);
	
	Task getTaskByPK(Long id);
	
	List<Task> getTaskListByCondition(Map<String, Object> condition);
	
	Long countTaskByCondition(Map<String, Object> condition);	
	
	void updateTaskByPK(Task task);
	
	void updateTaskValidStatusByPK(Task task);
	
	void updateTaskFinishFlagByPK(Task task);
	
	void updateTaskEmergencyFlagByPK(Task task);
	
	void updateTaskDeadLineByPK(Task task);
	
}
