package com.yan.todo.mapper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.yan.todo.schema.Meeting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//Junit5
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("TaskMapperTest")
class MeetingMapperTest {

	@Autowired
	public MeetingMapper meetingMapper;
	
	@Test
	void testInsertMeeting() throws Exception {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		
		Meeting meeting = new Meeting();
		meeting.setTitle("会议测试数据");
		meeting.setMeetingDay(sdformat.parse("2020-12-10"));
		meeting.setMeetingType("每日晨会");
		meeting.setProjectName("定报价");
		meeting.setContent("会议纪要");
		meeting.setValidStatus("1");
		meeting.setInsertTime(new Date());
		meeting.setUpdateTime(new Date());
		
		meetingMapper.insertMeeting(meeting);
		
	}

	@Test
	void testFindMeetingByPK() {
	}

	@Test
	void testFindMeetingListByCondition() {
	}

	@Test
	void testCountMeetingByCondition() {
	}

	@Test
	void testUpdateMeetingByPK() {
	}

	@Test
	void testUpdateMeetingValidStatusByPK() {
	}

}
