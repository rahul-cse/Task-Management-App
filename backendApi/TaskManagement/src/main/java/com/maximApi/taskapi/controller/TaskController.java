package com.maximApi.taskapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maximApi.taskapi.exception.DateRangeException;
import com.maximApi.taskapi.exception.InvalidRequestException;
import com.maximApi.taskapi.exception.ResourceNotCreateException;
import com.maximApi.taskapi.exception.ResourceNotFoundException;
import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.Department;
import com.maximApi.taskapi.model.OMS_USERS;
import com.maximApi.taskapi.model.Task;
import com.maximApi.taskapi.model.TaskRepeat;
import com.maximApi.taskapi.model.TestDate;
import com.maximApi.taskapi.model.payload.ApiResponse;
import com.maximApi.taskapi.model.projection.TaskUiView;
import com.maximApi.taskapi.repository.TestDateRepository;
import com.maximApi.taskapi.service.AppUserService;
import com.maximApi.taskapi.service.TaskRepeatService;
import com.maximApi.taskapi.service.TaskService;
import com.maximApi.taskapi.util.CurrentDateTimeChecker;
import com.maximApi.taskapi.validation.annotation.ValidUuid;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("api/task")
@Api(value="Task Rest Api", description="Defines endpoints for creating, finding various tasks")
@CrossOrigin("*")
public class TaskController {
	
	TaskService taskService;
	AppUserService appUserService;
	TestDateRepository testDateRepository;
	TaskRepeatService taskRepeatService;
		
	@Autowired
	public TaskController(TaskService taskService, AppUserService appUserService, TestDateRepository testDateRepository, TaskRepeatService taskRepeatService) {
		this.taskService = taskService;
		this.appUserService = appUserService;
		this.testDateRepository = testDateRepository;
		this.taskRepeatService = taskRepeatService;
	}


	@PostMapping("create-task") 	/* create new Task */
	public ResponseEntity createTask(@Valid @RequestBody Task task) {
		System.out.println(task.getTitle()+",start-date:"+task.getStartDate()+",end-date:"+task.getEndDate()+"repeat:"+task.isRepeat());
	
		if((task.getStartDate()!=null && task.getEndDate()!=null)&&!task.getStartDate().isBefore(task.getEndDate())) {
			throw new DateRangeException(task.getStartDate().toString(),task.getEndDate().toString());
		}
		Task newTask = taskService.createTask(task);
		if(newTask!=null)
			return ResponseEntity.ok(new ApiResponse(true, newTask));
		else
			throw new ResourceNotCreateException(task.getTitle());

	}
	
	
	@GetMapping("list-all-tasks") 	/* list out all the tasks */
	public ResponseEntity getAllTasks() {
		List<TaskUiView> taskLists =  taskService.getAllTasks();
		if(taskLists!=null) {
			return ResponseEntity.ok(new ApiResponse(true,taskLists));
		}
		else {
			throw new ResourceNotFoundException("task","empty records","");
		}
		
		
	}
	
	
	@GetMapping("/get-task/{uuId}/{task_id}") 		/* find a task by task creator */
	public ResponseEntity getTaskByCreatorId(@PathVariable @ValidUuid String uuId, @PathVariable String task_id) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task type","user_id",uuId);
		}
		else {
			Long entryBy = appUser.getId();
			Long taskId = Long.parseLong(task_id); 
			Task task  = taskService.getTask(taskId);
			if(task!=null) 
				return ResponseEntity.ok(new ApiResponse(true,task));
			else
				throw new ResourceNotFoundException("task","Task Id",task_id);
		}
		
	}
	
	
	@GetMapping("get-task-details/{uuId}/{task_id}")
	public ResponseEntity getTaskDetails(@PathVariable @ValidUuid String uuId, @PathVariable String task_id) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task type","user_id",uuId);
		}
		else {
			Long entryBy = appUser.getId();
			Long taskId = Long.parseLong(task_id); 
			TaskUiView taskUiView  = taskService.getTaskDetails(taskId);
			if(taskUiView!=null) 
				return ResponseEntity.ok(new ApiResponse(true,taskUiView));
			else
				throw new ResourceNotFoundException("task","Task Id",task_id);
		}
	}
	
	
	@PutMapping("update-task/{uuId}/{task_id}") 		/* update a task */
	public ResponseEntity updateTask(@RequestBody @Valid Task task,@PathVariable String task_id) {
		System.out.println(task.getStartDate()+" "+task.getEntryDate());
		Long taskId = Long.parseLong(task_id);
		if(taskId!=task.getId()) {
			throw new InvalidRequestException("TaskId: "+task_id);
		}
		else {
			Task updatedTask = taskService.updateTask(task);
			//Task updatedTask = task;
			if(updatedTask!=null)
				return ResponseEntity.ok(new ApiResponse(true,updatedTask));
			else
				throw new ResourceNotCreateException(task.getTitle());
		}
		
		
	}
	
	
	@GetMapping("/listTasks/{uuId}") 		/* list all tasks by areaId */
	public ResponseEntity getAllTasksByAreaId(@PathVariable @ValidUuid String uuId) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task type","area_id",uuId);
		}
		else {
			Long areaId = appUser.getAreaId();
			List<TaskUiView> taskUiViewLists =  taskService.getAllTasksByAreaId(areaId);
			if(taskUiViewLists != null) {
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			}
			else {
				throw new ResourceNotFoundException("task","area id",areaId);
			}
				
		}				
	}
	
	
	@GetMapping("/listTasks/{uuId}/{from_date}/{to_date}") 		/* list out all the tasks by areaId and between some dates */
	public ResponseEntity getAllTasksByAreaIdAndEntryDate(@PathVariable @ValidUuid String uuId, @PathVariable String from_date, @PathVariable String to_date) {
		System.out.println("from_date"+from_date);
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task type","area_id",uuId);
		}
		else {
			Long areaId = appUser.getAreaId();
			List<TaskUiView> taskUiViewLists =  taskService.getAllTaskByAreaIdAndEntryDateWise(areaId, from_date, to_date);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","area id",areaId);
		}
		
		
	}
	
	
	@GetMapping("/listTasksByCreator/{uuId}") 		/* find out all the tasks by task creator/entry by */
	public ResponseEntity getAllTasksByTaskCreator(@PathVariable @ValidUuid String uuId) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		else {
			Long entryBy = appUser.getId();
			List<TaskUiView> taskUiViewLists = taskService.getAllTaskByTaskCreator(entryBy);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","created by you",entryBy);
		}
		
	}
	
//	@GetMapping("getTask/{taskId}")
//	public ResponseEntity getTaskbyId(@PathVariable Long taskId) {
//		return null;
//		
//	}
	
	
	@GetMapping("/listTasksByCreator/{uuId}/{from_date}/{to_date}") /*
																	 * find out all the tasks by task creator/entry by between
																	 * certain entry dates
																	 */
	public ResponseEntity getAllTasksByTaskCreatorAndEntryDate(@PathVariable @ValidUuid String uuId, @PathVariable String from_date, @PathVariable String to_date) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		else {
			Long entryBy = appUser.getId();
			List<TaskUiView> taskUiViewLists = taskService.getAllTaskByTaskCreatorAndEntryDateWise(entryBy, from_date, to_date);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","created by you in these dates",from_date+"-"+to_date);
		}
	}
	
	
	@GetMapping("/listTaskByAssignedUser/{uuId}") 	/* find all tasks to an assigned user */
	public ResponseEntity getAllTasksByAssignedTo(@PathVariable @ValidUuid String uuId) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		else {
			Long assignedTo = appUser.getId();
			List<TaskUiView> taskUiViewLists = taskService.getAllTaskByAssignedTo(assignedTo);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","assigned to you",assignedTo);
		}
	}
	
	
	@GetMapping("/listTasksByDept/{uuId}/{dept_id}") 	/* find all tasks assigned to a department */
	public ResponseEntity getAllTasksByDept(@PathVariable @ValidUuid String uuId, @PathVariable String dept_id) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		else {
			Long userId = appUser.getAreaId();
			Long deptId = Long.parseLong(dept_id);
			List<TaskUiView> taskUiViewLists =  taskService.getAllTaskByDeptId(userId, deptId);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","department name",dept_id);
		}
	}
	
	@GetMapping("/listTasksByUserId/{uuId}") 	/* find all tasks by userId */
	public ResponseEntity getAllTasksByUserId(@PathVariable @ValidUuid String uuId){
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		else {
			Long userId = appUser.getId();
			List<TaskUiView> taskUiViewLists = taskService.getAllTaskByUserId(userId);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","department name",userId);
		}
	}
	
	@GetMapping("/listTasksByAreaIdAndStartEndDate/{uuId}/{start_date}/{end_date}") /*
																					 * find out all the tasks by areaId
																					 * and between start date and end date
																					 */
	public ResponseEntity getAllTasksByDeptFromToDate(@PathVariable @ValidUuid String uuId, @PathVariable String start_date, @PathVariable String end_date) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		Long areaId = appUser.getAreaId();
		List<TaskUiView> taskUiViewLists = taskService.getAllTaskByAreaIdAndStartDateEndDateWise(areaId, start_date, end_date);
		if(taskUiViewLists !=null)
			return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
		else
			throw new ResourceNotFoundException("task","in these dates",start_date+" "+end_date);

		
	}
	
	
	@GetMapping("/listTasksByDept/{uuId}/{dept_id}/{start_date}/{end_date}")
	public ResponseEntity getAllTasksByDeptFromToDate(@PathVariable @ValidUuid String uuId, @PathVariable String dept_id, @PathVariable String start_date, @PathVariable String end_date) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		else {
			Long userId = appUser.getId();
			Long deptId = null;
			try {
				 deptId = Long.parseLong(dept_id);
			}
			catch(Exception ex){
				throw new InvalidRequestException("Department Id-"+ dept_id);
			}
			List<TaskUiView> taskUiViewLists = taskService.getAllTaskByDeptIdAndStartDateEndDateWise(userId, deptId, start_date, end_date);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","department name",dept_id);
		}
				
	}
	
	
	
	@GetMapping("/listTasksByUserIdAndStatus/{uuId}/{task_status}") 	/* find out all tasks which are yet to start by userId */
	public ResponseEntity getAllTasksByUserIdAndStatus(@PathVariable @ValidUuid String uuId , @PathVariable("task_status") String task_status) {
		AppUser appUser =  appUserService.findAppUser(uuId);
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		Long userId = appUser.getId();
		List<TaskUiView> taskUiViewLists = taskService.getAllTaskByUserIdAndTaskStatus(userId, task_status);
		if(taskUiViewLists !=null)
			return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
		else
			throw new ResourceNotFoundException("task","task status",task_status);
	}
	
	
	
	
	@GetMapping("listTaskByAreaIdAndTaskType/{uuId}/{task_type_id}") /* ---- find tasks by AreaId and one or more task type Id  ---- */
	public ResponseEntity getAllTasksAreaIdByTaskType(@PathVariable @ValidUuid String uuId, @PathVariable String task_type_id) { 		
		AppUser appUser =  appUserService.findAppUser(uuId);
		Long areaId;
		if(appUser == null) {
			throw  new ResourceNotFoundException("task","user_id",uuId);
		}
		else 
			areaId = appUser.getAreaId();
		System.out.println(task_type_id.length());
		List<Long> listOfTaskTypeId =  new ArrayList<Long>();
		
		if (!task_type_id.contains(",")) { 		/* -- checking if the supplied task_type_id parameter contains comma or not -- */
			try {
				Long l = Long.parseLong(task_type_id);
				listOfTaskTypeId.add(l);System.out.println("l"+l);
				
			}
			catch(Exception ex){
				throw new InvalidRequestException("Task Type Id-"+ task_type_id);
			}
		}
		else {
			String[] taskTypeArray = task_type_id.split(",");
			
			for(String s : taskTypeArray) {
				try {
					Long l = Long.parseLong(s);
					listOfTaskTypeId.add(l);System.out.println(l);
				}
				catch(Exception ex){
					throw new InvalidRequestException("Task Type Id-"+ s);
				}
			}
			
			
		}
			//throw new InvalidRequestException("No Comma:- "+ task_type_id);
		if(listOfTaskTypeId.isEmpty()) {
			throw  new ResourceNotFoundException("task","task type id",task_type_id);
		}
		else {
			List<TaskUiView> taskUiViewLists =  taskService.getAllTaskByAreaIdANDTaskType(areaId,listOfTaskTypeId);
			if(taskUiViewLists !=null)
				return ResponseEntity.ok(new ApiResponse(true,taskUiViewLists));
			else
				throw new ResourceNotFoundException("task","task type Id",task_type_id);
		}
		
		
		
	}
	
	@GetMapping("getUserInfo")
	public ResponseEntity getUserInfo() {
		List<AppUser> omsUsersList = taskService.getAllUser();
		return ResponseEntity.ok(new ApiResponse(true,omsUsersList));
		
	}
	
	@GetMapping("getDeptInfo")
	public ResponseEntity getDepartmentInfo() {
		List<Department> deptList = taskService.getAllDept();
		return ResponseEntity.ok(new ApiResponse(true,deptList));
	}
	
	@GetMapping("getDept/{deptId}")
	public ResponseEntity getOneDepartment(@PathVariable String deptId) {
		if(deptId!=null) {
			Long departmentId = Long.parseLong(deptId);
			Department department = taskService.getDept(departmentId);
			if(department!=null) {
				return ResponseEntity.ok(new ApiResponse(true,department));
			}
			else {
				throw new ResourceNotCreateException(deptId);
			}
		}
		else {
			throw new InvalidRequestException("Department Id-"+ deptId);
		}
		
	}
	
	@GetMapping("alltaskrepeat")
	public ResponseEntity getAllTaskRepeat() {
		List<TaskRepeat> taskRepeatList=taskRepeatService.findAllTaskRepeat();
		return ResponseEntity.ok(new ApiResponse(true, taskRepeatList));
	}
	
	@GetMapping("calculate")
	public ResponseEntity doCalculate() {
		String str = "2019-09-23 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldPast = LocalDateTime.parse(str, formatter);
        LocalDateTime ldNow = LocalDateTime.now();
        List<String> repeatDay= new ArrayList<String>();
        repeatDay.add("Saturday");
        repeatDay.add("Sunday");
        repeatDay.add("Wednesday");
        int count = 0;
		while(ldPast.isBefore(ldNow)) {
			System.out.println(ldPast.getDayOfWeek().name());
			String day = ldPast.getDayOfWeek().name();
			repeatDay.forEach(d->{
				if(d.toUpperCase().equals(day)) {
					System.out.println("inside:"+day);
				}
			});
			ldPast=ldPast.plusHours(24);
			count++;
		}
        System.out.println("days:"+ldPast);
		return ResponseEntity.ok(new ApiResponse(true, count));
	}
	
	
	/*
	 * @PostMapping("/tasks") public void postTask(@RequestBody TestDate testDate)
	 * throws ParseException {
	 * System.out.println(testDate.getData()+":"+testDate.getLocalDate());
	 * //testDate.setLocalDate(testDate.getLocalDate().plusHours(6));
	 * //System.out.println(testDate.getData()+":"+testDate.getLocalDate());
	 * //testDate.setLocalDate(testDate.getLocalDate().atZone(ZoneId.systemDefault()
	 * )); SimpleDateFormat isoFormat = new
	 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	 * isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); Date date =
	 * isoFormat.parse(testDate.getLocalDate().toString());
	 * System.out.println("date:"+date); //testDateRepository.save(testDate); }
	 */
}
