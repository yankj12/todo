package com.yan.todo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yan.todo.schema.Meeting;
import com.yan.todo.schema.Task;

@Mapper
public interface MeetingMapper {

	void insertMeeting(Meeting meeting);
	
	Task findMeetingByPK(Long id);
	
	List<Task> findMeetingListByCondition(Map<String, Object> condition);
	
	Long countMeetingByCondition(Map<String, Object> condition);	
	
	void updateMeetingByPK(Meeting meeting);
	
	void updateMeetingValidStatusByPK(Meeting meeting);
	
}
