package com.maximApi.taskapi.controller;


import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximApi.taskapi.exception.InvalidRequestException;
import com.maximApi.taskapi.exception.ResourceNotCreateException;
import com.maximApi.taskapi.exception.ResourceNotFoundException;
import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.TaskType;
import com.maximApi.taskapi.model.payload.ApiResponse;
import com.maximApi.taskapi.service.AppUserService;
import com.maximApi.taskapi.service.TaskTypeService;
import com.maximApi.taskapi.validation.annotation.ValidUuid;

@RestController
@RequestMapping("api/task")
@Api(value="Task Rest Api", description="Defines endpoints for creating, finding various task types")
@CrossOrigin("*")
@Validated
public class TaskTypeController { 				/* It is a RestController for all the endpoints of various task types. */
	
	private static final Logger logger = LoggerFactory.getLogger(TaskTypeController.class);
	
	@Autowired
	TaskTypeService taskTypeService;
	
	@Autowired
	AppUserService appuserService;
	
	
    @ApiOperation(value = "Creates new Task Type")
	@PostMapping("/create-taskType")				/* Method for saving/creating new task type */
	public ResponseEntity saveTaskType(@Valid @RequestBody TaskType taskType) {
		//System.out.println("task:"+taskType.getAreaId()+taskType.getName()+taskType.getId());
		logger.info("task:"+taskType.getAreaId()+taskType.getName()+taskType.getId());
		TaskType newTaskType = taskTypeService.createTaskType(taskType);
		if(newTaskType!=null)
			return ResponseEntity.ok(new ApiResponse(true, newTaskType));
		else
			throw new ResourceNotCreateException(taskType.getName());
		
	}
	
	
	@GetMapping("/list-taskType") 					/* Method for showing all available task types */
	public ResponseEntity listTaskType() {
		List<TaskType> taskTypeList = taskTypeService.showAllTaskType();
		if(taskTypeList!=null)
			return ResponseEntity.ok(new ApiResponse(true,taskTypeList));
		else
			throw new ResourceNotFoundException("task type","empty records","");
		
	}
	
	
	@GetMapping("/list-taskType/{uuId}") 		/* Method for showing all task types by areaId */
	public ResponseEntity listTaskTypeByAreaId(@PathVariable @ValidUuid String uuId) {
		System.out.println("uuId:"+uuId);
		AppUser appUser =  appuserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task type","area_id",uuId);
		}
		else {
			Long area_id = appUser.getAreaId();
			List<TaskType> taskTypeLists = taskTypeService.findTaskTypesByAreaId(area_id);
			if(!taskTypeLists.isEmpty())
				return ResponseEntity.ok(new ApiResponse(true, taskTypeLists));
			else
			 throw new ResourceNotFoundException("task type","area_id",area_id);
		}
		
		
		
	}
	
	
	@GetMapping("/get-taskType/{id}") 				/* Method for showing task type by task type id */
	public ResponseEntity getTaskTypeById(@PathVariable long id) {
		System.out.println("id:"+id);
		Optional<TaskType> taskType = taskTypeService.getTaskTypeById(id);
		
		 if(taskType.isPresent())
			 return ResponseEntity.ok(new ApiResponse(true,taskType));  
		 else 
			 throw new ResourceNotFoundException("task type","id",id);
		 
		
				
	}
	
	
	@PutMapping("/edit-taskType/{id}") 				/* Method for editing a task type */
	public ResponseEntity editTaskType(@Valid @RequestBody TaskType taskType, @PathVariable long id) {
		System.out.println("edit"+taskType.getId()+taskType.getName()+taskType.getDescription());
		if (id == taskType.getId()) { 				/* checking if the url id and the task_type_id is same */
			TaskType newTaskType = taskTypeService.editTaskType(taskType);
			return ResponseEntity.ok(new ApiResponse(true, newTaskType));
		}
		else {
			throw new InvalidRequestException("task type update");
		}
		
		
		
	}
	
}
