package com.yan.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ToDoController {
	
	@RequestMapping("/list")
	public String file() {
		return "taskToDo";
	}
}
