package com.maximApi.taskapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximApi.taskapi.exception.InvalidRequestException;
import com.maximApi.taskapi.exception.ResourceNotCreateException;
import com.maximApi.taskapi.exception.ResourceNotFoundException;
import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.TaskRepeat;
import com.maximApi.taskapi.model.payload.ApiResponse;
import com.maximApi.taskapi.repository.TaskRepeatRepository;
import com.maximApi.taskapi.service.AppUserService;
import com.maximApi.taskapi.service.TaskRepeatService;
import com.maximApi.taskapi.service.TaskService;
import com.maximApi.taskapi.util.Utility;
import com.maximApi.taskapi.validation.annotation.ValidUuid;

@RestController
@Validated
@RequestMapping("api/task")
@CrossOrigin("*")
public class TaskRepeatController {
	
	TaskRepeatService taskRepeatService;
	AppUserService appUserService;
	
	
	@Autowired
	public TaskRepeatController(TaskRepeatService taskRepeatService, AppUserService appUserService) {
		this.taskRepeatService = taskRepeatService;
		this.appUserService = appUserService;
	}
	@Autowired
	TaskRepeatRepository taskRepeatRepository;


	@GetMapping("get-one-task-repeat-detail/{task_id}")
	public ResponseEntity getTaskRepeatDetails(@PathVariable String task_id) {
		Long taskId = null;
		try {
			 taskId = Long.parseLong(task_id); 
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok(new ApiResponse(false, "Invalid task Id"));
		}
		List<TaskRepeat> taskRepeatList = taskRepeatService.findOneTaskRepeatDetails(taskId);
		if(taskRepeatList !=null) {
			return ResponseEntity.ok(new ApiResponse(true,taskRepeatList));
		}
		else
			throw new ResourceNotFoundException("task repeat", "task id", task_id);
		
		
	}
	
	
	@PutMapping("update-one-task-repeat/{uuId}")
	public ResponseEntity saveOneTaskRepeat(@PathVariable @ValidUuid String uuId, @RequestBody TaskRepeat taskRepeat) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task type","user_id",uuId);
		}
		TaskRepeat updatedTaskRepeat = taskRepeatService.updateTaskRepeat(taskRepeat);
		if(updatedTaskRepeat != null) {
			return ResponseEntity.ok(new ApiResponse(true, updatedTaskRepeat));
		}
		else {
			throw new ResourceNotCreateException("task repeat");
		}
	}
	
	
	
	  /*@GetMapping("sc/{task_id}") 
	  public ResponseEntity getTaskRepeat(@PathVariable String task_id) { 
	  Long taskId = Long.parseLong(task_id); 
	  List<TaskRepeat>weekdayList = taskRepeatRepository.findTaskRepeatWeekDayByTaskId(taskId);
	  Map<String, String> weekday = new HashMap<String, String>(); 
//	  for(String day: weekdayList) 
//		  weekday.put(day, day); 
	  return ResponseEntity.ok(new ApiResponse(true, weekdayList));
	  
	  }*/
	 
	
}
