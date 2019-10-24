package com.maximApi.taskapi.service.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import java.time.temporal.ChronoUnit.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maximApi.taskapi.exception.DateRangeException;
import com.maximApi.taskapi.exception.ResourceNotCreateException;
import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.Department;
import com.maximApi.taskapi.model.EmployeeConfig;
import com.maximApi.taskapi.model.OMS_USERS;
import com.maximApi.taskapi.model.Task;
import com.maximApi.taskapi.model.TaskAssociate;
import com.maximApi.taskapi.model.TaskRepeat;
import com.maximApi.taskapi.model.TeamMembers;
import com.maximApi.taskapi.model.projection.TaskUiView;
import com.maximApi.taskapi.repository.AppUserRepository;
import com.maximApi.taskapi.repository.DepartmentRepository;
import com.maximApi.taskapi.repository.EmployeeConfigRepository;
import com.maximApi.taskapi.repository.OmsUsersRepository;
import com.maximApi.taskapi.repository.TaskRepository;
import com.maximApi.taskapi.repository.TaskTypeRepository;
import com.maximApi.taskapi.service.TaskAssociateService;
import com.maximApi.taskapi.service.TaskRepeatService;
import com.maximApi.taskapi.service.TaskService;
import com.maximApi.taskapi.service.TeamMemberService;
import com.maximApi.taskapi.util.CurrentDateTimeChecker;
import com.maximApi.taskapi.util.LocalTimeParser;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	TaskRepository taskRepository;
	TaskRepeatService taskRepeatService;
	OmsUsersRepository omsUsersRepository;
	DepartmentRepository departmentRepository;
	TaskTypeRepository taskTypeRepository;
	AppUserRepository appUserRepository;
	EmployeeConfigRepository employeeConfigRepository;
	TeamMemberService teamMembersService;
	TaskAssociateService taskAssociateService;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository, TaskRepeatService taskRepeatService,
			OmsUsersRepository omsUsersRepository, DepartmentRepository departmentRepository,
			TaskTypeRepository taskTypeRepository, AppUserRepository appUserRepository,
			EmployeeConfigRepository employeeConfigRepository, TeamMemberService teamMembersService,
			TaskAssociateService taskAssociateService) {
		super();
		this.taskRepository = taskRepository;
		this.taskRepeatService = taskRepeatService;
		this.omsUsersRepository = omsUsersRepository;
		this.departmentRepository = departmentRepository;
		this.taskTypeRepository = taskTypeRepository;
		this.appUserRepository = appUserRepository;
		this.employeeConfigRepository = employeeConfigRepository;
		this.teamMembersService = teamMembersService;
		this.taskAssociateService = taskAssociateService;

	}

	@Override
	public Task createTask(Task task) {
		System.out.println(task.getTitle() + ",start-date:" + task.getStartDate() + ",end-date:" + task.getEndDate());
		LocalDateTime localDateTime = LocalDateTime.now();
		task.setEntryDate(LocalDateTime.now()); /* Assigning Entry Date by Current LocalDateTime */
		task.setStartDate(setToLocalDateTime(localDateTime, task.getStartDate()));
		task.setEndDate(setToLocalDateTime(localDateTime, task.getEndDate()));
		List<TaskRepeat> taskRepeatList = new ArrayList<TaskRepeat>();
		List<TaskAssociate> taskAssociateList = new ArrayList<TaskAssociate>();
		if (task.getStatus() == null)
			task.setStatus("TO_DO");
		if (task.isRepeat()) {
			if (task.getRepeatType() != null) {
				taskRepeatList = calculateTaskRepeat(task);
			}
			else {
				return null;
			}

		}
		else {		
			taskAssociateList =  setAllTaskAssociate(task);
		}
		CurrentDateTimeChecker currentDateTimeChecker = new CurrentDateTimeChecker();
		Task newTask=null;
		try {
			String startdate = "";
			String enddate = "";
			if(currentDateTimeChecker.check(task.getStartDate()) == false) {
				if(task.getStartDate()!=null)
					startdate = task.getStartDate().toString();
				throw new DateRangeException(startdate, enddate,"is not greater than current time.");
			}
			if(currentDateTimeChecker.check(task.getEndDate()) == false) {				
				if(task.getEndDate()!=null)
					enddate = task.getEndDate().toString();
				throw new DateRangeException(startdate, enddate,"is not greater than current time.");
			}	
			newTask = taskRepository.save(task);
			if(!taskRepeatList.isEmpty())
				taskRepeatService.saveAllTaskRepeat(taskRepeatList);
			if(taskAssociateList!=null)
				taskAssociateService.createAllTaskAssociate(taskAssociateList);
			System.out.println(task.getTitle() + ",start-date:" + task.getStartDate() + ",end-date:" + task.getEndDate());
			return newTask;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		/*
		 * // else { // throw new DateRangeException(task.getStartDate().toString(),
		 * task.getEndDate().toString(),"is not greater than current time."); // }
		 */
		return newTask;		
	}

	@Override
	public List<TaskUiView> getAllTasks() {

		List<Task> taskLists = taskRepository.findAll();
		List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
		if (!taskUiViewLists.isEmpty())
			return taskUiViewLists;
		else {
			return null;
		}
	}

	@Override
	public Task getTask(long taskId) {

		Optional<Task> optionalTask = taskRepository.findById(taskId);
		// System.out.println(task.getTitle());
		if (optionalTask.isPresent()) {
			// TaskUiView taskUiView = getUserInfo(task);
			Task task = optionalTask.get();
			System.out.println("task.getTitle()" + task.getTitle());
			Set<String> weekday = new HashSet<String>();
			Set<Long> userIdList = new HashSet<Long>();
			if(task.isRepeat()) {
				List<Object[]> weekDayUserIdList = taskRepeatService.findAllRepeatWeekdayAndUserByTaskId(taskId, LocalDateTime.now());
				for(Object[] object: weekDayUserIdList) {


					weekday.add((String) object[0]);
					String assignType = (String) object[1];
					if(object[2]!=null && assignType.equals("other"))
						userIdList.add((Long) object[2]);
				}
				
				
				task.setTaskRepeatDay(weekday.stream().collect(Collectors.toList()));
				task.setAssignedToOtherNonTeamUser(userIdList.stream().collect(Collectors.toList()));
			}
			else {
				task.setAssignedToOtherNonTeamUser(taskAssociateService.findTaskAssociateUserIdByTaskId(taskId));
			}
	
			
				
			return task;
		} else
			return null;
	}

	@Override
	public TaskUiView getTaskDetails(long taskId) { // this method is for getting task details
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (optionalTask.isPresent()) {
			// TaskUiView taskUiView = getUserInfo(task);
			Task task = optionalTask.get();
			TaskUiView taskUiView = getUserInfo(task);
			return taskUiView;
		} else
			return null;
	}

	@Override
	public Task updateTask(Task task) { 								// this method is for updating task details

		LocalDateTime localDateTime = LocalDateTime.now();
		Task currentTask = taskRepository.getOne(task.getId());
		CurrentDateTimeChecker currentDateTimeChecker = new CurrentDateTimeChecker();
		if (task.getStartDate() == null) {
			task.setStartDate(currentTask.getStartDate());
		} else {
			task.setStartDate(setToLocalDateTime(localDateTime, task.getStartDate()));
			/*checking startdate with currentdatetime*/
			if(currentDateTimeChecker.check(task.getStartDate()) == false)
				throw new DateRangeException(task.getStartDate().toString(), task.getEndDate().toString(),"is not greater than current time.");
				
				
		}
		if (task.getEndDate() == null) {
			task.setEndDate(currentTask.getEndDate());
		} else {
			task.setEndDate(setToLocalDateTime(localDateTime, task.getEndDate()));
			if(currentDateTimeChecker.check(task.getEndDate()) == false)
				throw new DateRangeException(task.getStartDate().toString(), task.getEndDate().toString(),"is not greater than current time.");
			/*checking enddate with currentdatetime*/	
		}
		task.setEntryDate(currentTask.getEntryDate());
		List<TaskRepeat> taskRepeatList = new ArrayList<TaskRepeat>();
		List<TaskAssociate> taskAssociateList = new ArrayList<TaskAssociate>();
		if (task.getStatus() == null)
			task.setStatus("TO_DO");
		if (task.isRepeat()) {
			if (task.getRepeatType() != null) {
				taskRepeatList = calculateTaskRepeat(task);
 				boolean successfulTaskRepeatCheck = deleteTaskRepeat(task, taskRepeatList);
				if(successfulTaskRepeatCheck==false) {
					throw new ResourceNotCreateException(task.getTitle());
				}
			}
			else {
				return null;
			}

		}
		else {		
			taskAssociateList =  setAllTaskAssociate(task);
			boolean successfulTaskAssociateCheck =  checkAndUpdateTaskAssociate(task,taskAssociateList);
			 
				
				if(successfulTaskAssociateCheck==false) 
					throw new ResourceNotCreateException(task.getTitle());
			
		}
	
			
			taskRepository.save(task);
			//if(!taskRepeatList.isEmpty())
				//taskRepeatService.saveAllTaskRepeat(taskRepeatList);
			
				
		
		return task;

	}

	@Override
	public List<TaskUiView> getAllTasksByAreaId(long areaId) {
		
		List<Task> taskLists = taskRepository.findByAreaId(areaId);
		if (!taskLists.isEmpty()) {
			/*
			 * List<TaskUiView> taskUiViewLists = new
			 * ArrayList<TaskUiView>(taskLists.size()); for(Task task: taskLists) {
			 * TaskUiView taskUiView = new TaskUiView(); BeanUtils.copyProperties(task,
			 * taskUiView); taskUiView.setTaskId(task.getId()); OMS_USERS omsUsers = null;
			 * if(task.getAssignedTo() !=null) { omsUsers =
			 * omsUsersRepository.getOne(task.getAssignedTo());
			 * if(omsUsers.getLAST_NAME()!=null)
			 * taskUiView.setAssignedUser(omsUsers.getFIRST_NAME() + " " +
			 * omsUsers.getLAST_NAME()); else
			 * taskUiView.setAssignedUser(omsUsers.getFIRST_NAME()); }
			 * 
			 * taskUiViewLists.add(taskUiView); }
			 */
			List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
			return taskUiViewLists;
		}

		else {
			return null;
		}

	}

	@Override
	public List<TaskUiView> getAllTaskByAreaIdAndEntryDateWise(long areaId, String fromDate, String toDate) {

		LocalTimeParser localTimeParser = new LocalTimeParser();
		LocalDateTime fromDateLocal = LocalDateTime.parse(fromDate, localTimeParser.getTimeParser());
		LocalDateTime toDateLocal = LocalDateTime.parse(toDate, localTimeParser.getTimeParser());
		System.out.println("localDateTime:" + fromDateLocal);

		List<Task> taskLists = taskRepository.findTaskByAreaIdAndFromToDate(areaId, fromDateLocal, toDateLocal);
		// System.out.println("taskLists.size():"+taskLists.size());
		List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
		return taskUiViewLists;
	}

	/** - - - - Getting Task User and Department Details **/

	List<TaskUiView> getUserInfoList(List<Task> taskLists) {
		if (!taskLists.isEmpty()) {
			List<TaskUiView> taskUiViewLists = new ArrayList<TaskUiView>(taskLists.size());
			for (Task task : taskLists) {
				TaskUiView taskUiView = new TaskUiView();
				BeanUtils.copyProperties(task, taskUiView);
				taskUiView.setTaskId(task.getId());
				String taskTypeName = taskTypeRepository.getOne(task.getTaskTypeId()).getName();
				taskUiView.setTaskTypeName(taskTypeName);
				OMS_USERS omsUsers = null;
				if (task.getAssignedTo() != null) {
					omsUsers = omsUsersRepository.getOne(task.getAssignedTo());
					if (omsUsers.getLAST_NAME() != null)
						taskUiView.setAssignedUser(omsUsers.getFIRST_NAME() + " " + omsUsers.getLAST_NAME());
					else
						taskUiView.setAssignedUser(omsUsers.getFIRST_NAME());
				}
				if (task.getAssignedToDept() != null) {

					String deptName = departmentRepository.getOne(task.getAssignedToDept()).getName();
					taskUiView.setAssignedDept(deptName);
				}

				taskUiViewLists.add(taskUiView);
			}

			return taskUiViewLists;
		} else
			return null;

	}

	/* This method is for only one Task */

	public TaskUiView getUserInfo(Task task) {
		TaskUiView taskUiView = new TaskUiView();
		BeanUtils.copyProperties(task, taskUiView);
		taskUiView.setTaskId(task.getId());
		String taskTypeName = taskTypeRepository.getOne(task.getTaskTypeId()).getName();
		taskUiView.setTaskTypeName(taskTypeName);
		OMS_USERS omsUsers = null;
		if (task.getAssignedTo() != null) {
			omsUsers = omsUsersRepository.getOne(task.getAssignedTo());
			if (omsUsers.getLAST_NAME() != null)
				taskUiView.setAssignedUser(omsUsers.getFIRST_NAME() + " " + omsUsers.getLAST_NAME());
			else
				taskUiView.setAssignedUser(omsUsers.getFIRST_NAME());
		}
		if (task.getAssignedToDept() != null) {

			String deptName = departmentRepository.getOne(task.getAssignedToDept()).getName();
			taskUiView.setAssignedDept(deptName);
		}
		return taskUiView;
	}

	/** - - - - Getting Task User and Department Details End **/

	@Override
	public List<TaskUiView> getAllTaskByTaskCreator(long entryBy) {

		List<Task> taskLists = taskRepository.findByEntryBy(entryBy);
		List<TaskUiView> taskUiView = getUserInfoList(taskLists);
		if (taskUiView != null)
			return taskUiView;
		else
			return null;
	}

	@Override
	public List<TaskUiView> getAllTaskByAssignedTo(long assignedTo) {

		List<Task> taskLists = taskRepository.findByAssignedTo(assignedTo);
		List<TaskUiView> taskUiViews = getUserInfoList(taskLists);
		if (taskUiViews != null)
			return taskUiViews;
		else
			return null;

	}

	@Override
	public List<TaskUiView> getAllTaskByTaskCreatorAndEntryDateWise(long entryBy, String fromDate, String toDate) {

		LocalTimeParser localTimeParser = new LocalTimeParser();
		LocalDateTime fromDateLocal = LocalDateTime.parse(fromDate, localTimeParser.getTimeParser());
		LocalDateTime toDateLocal = LocalDateTime.parse(toDate, localTimeParser.getTimeParser());

		List<Task> taskLists = taskRepository.findTaskByEntryByAndFromToDate(entryBy, fromDateLocal, toDateLocal);
		List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
		if (taskUiViewLists != null)
			return taskUiViewLists;
		else
			return null;
	}

	@Override
	public List<TaskUiView> getAllTaskByDeptId(long userId, long deptId) {
		
		List<Task> taskLists = taskRepository.findTaskByDeptId(deptId);
		List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
		if (taskUiViewLists != null)
			return taskUiViewLists;
		else
			return null;
	}

	@Override
	public List<TaskUiView> getAllTaskByDeptIdAndStartDateEndDateWise(long userId, long deptId, String startDate,
			String endDate) { /* -- get Task assigned to department between start date and end date wise -- */

		LocalTimeParser localTimeParser = new LocalTimeParser();
		LocalDateTime startDateLocal = LocalDateTime.parse(startDate, localTimeParser.getTimeParser());
		LocalDateTime endDateLocal = LocalDateTime.parse(endDate, localTimeParser.getTimeParser());
		List<Task> taskLists = taskRepository.findTaskByDeptStartAndEndDateWise(deptId, startDateLocal, endDateLocal);
		List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
		if (taskUiViewLists != null)
			return taskUiViewLists;
		else
			return null;
	}

	@Override
	public List<TaskUiView> getAllTaskByAreaIdANDTaskType(Long areaId, List<Long> taskTypeIdArray) {

		List<Task> fullTaskLists = new ArrayList<Task>();
		for (Long taskTypeId : taskTypeIdArray) {
			List<Task> taskLists = taskRepository.findTaskByAreaIdAndTaskTypeId(areaId, taskTypeId);
			fullTaskLists.addAll(taskLists);
		}
		List<TaskUiView> taskUiViewLists = getUserInfoList(fullTaskLists);
		if (taskUiViewLists != null)
			return taskUiViewLists;
		else
			return null;
	}

	@Override
	public List<TaskUiView> getAllTaskByAreaIdAndStartDateEndDateWise(long areaId, String startDate, String endDate) {

		LocalTimeParser localTimeParser = new LocalTimeParser();
		LocalDateTime startDateLocal = LocalDateTime.parse(startDate, localTimeParser.getTimeParser());
		LocalDateTime endDateLocal = LocalDateTime.parse(endDate, localTimeParser.getTimeParser());
		List<Task> taskLists = taskRepository.findTaskByAreaIdStartAndEndDateWise(areaId, startDateLocal, endDateLocal);
		List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
		if (taskUiViewLists != null)
			return taskUiViewLists;
		else
			return null;
	}

	@Override
	public List<TaskUiView> getAllTaskByUserIdAndTaskStatus(Long userId, String status) {

		List<Task> taskLists = taskRepository.findByEntryByOrAssignedTo(userId, userId);
		List<Task> fullTaskLists = new ArrayList<Task>();
		for (Task task : taskLists) { /* Loop for checking individual task's current status */
			if (task.getStatus().equals(status)) {
				fullTaskLists.add(task);
			}
		}
		List<TaskUiView> taskUiViewLists = getUserInfoList(fullTaskLists);
		if (taskUiViewLists != null)
			return taskUiViewLists;
		else
			return null;
	}
	
	
	@Override
	public List<TaskUiView> getAllTaskByUserId(Long userId) {
		List<Task> taskLists = taskRepository.findByEntryByOrAssignedTo(userId, userId);
		List<TaskUiView> taskUiViewLists = getUserInfoList(taskLists);
		if (taskUiViewLists != null)
			return taskUiViewLists;
		else
			return null;
	}
	

	LocalDateTime setToLocalDateTime(LocalDateTime ldtNow, LocalDateTime ldt) {
		if (ldt != null) {
			/*
			 * if(ldtNow.getHour()!=ldt.getHour()) { ldt = ldt.plusHours(6); }
			 */

			Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			ldt = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		}

		return ldt;
	}

	@Override
	public List<AppUser> getAllUser() {
		
		List<AppUser> omsUsersList = appUserRepository.findAll();
		return omsUsersList;
	}

	@Override
	public List<Department> getAllDept() {

		List<Department> departmentList = departmentRepository.findAll();
		return departmentList;
	}

	@Override
	public Department getDept(Long deptId) {

		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		Department department = null;
		if (optionalDepartment.isPresent()) {
			department = optionalDepartment.get();
			return department;
		} else
			return null;
	}

	/* calculating task repetition */
	public List<TaskRepeat> calculateTaskRepeat(Task task) {
		List<TaskRepeat> taskRepeatList = new ArrayList<TaskRepeat>();
		Map<Long,String> userMapList = assignTeamAndNonTeamUser(task);
		List<AppUser>appUserList = appUserRepository.findAllById(userMapList.keySet());
		if (task.getRepeatType().equals("daily")) {
			Long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(task.getStartDate(), task.getEndDate());
			for (int i = 0; i < daysBetween.intValue() + 1; i++) {
				TaskRepeat taskRepeat = null;
				if(!userMapList.isEmpty()) {
					for(Map.Entry<Long, String> user: userMapList.entrySet()) {
						taskRepeat = new TaskRepeat();
						taskRepeat.setTask(task);
						taskRepeat.setStatus("TO_DO");
						taskRepeat.setAppuser(appUserRepository.getOne(user.getKey()));
						taskRepeat.setAssignType(user.getValue());
						taskRepeatList.add(taskRepeat);
						if (i > 0)
							taskRepeat.setTaskRepeatDatetime(task.getStartDate().plusHours(24));
						else
							taskRepeat.setTaskRepeatDatetime(task.getStartDate());
					}
				}
					
				else {
					taskRepeat = new TaskRepeat();
					taskRepeat.setTask(task);
					taskRepeat.setStatus("TO_DO");
					taskRepeatList.add(taskRepeat);
					if (i > 0)
						taskRepeat.setTaskRepeatDatetime(task.getStartDate().plusHours(24));
					else
						taskRepeat.setTaskRepeatDatetime(task.getStartDate());
				}

	

			}

		} else if (task.getRepeatType().equals("weekly")) {
			if (task.getRepeatType().equals("weekly")) {
				LocalDateTime ldt = task.getStartDate();

				while (ldt.isBefore(task.getEndDate())) {

					/*
					 * task.getTaskRepeatDay().forEach(d->{
					 * 
					 * if(d.toUpperCase().equals(ldt.getDayOfWeek())) { TaskRepeat taskRepeat = new
					 * TaskRepeat(); taskRepeat.setTaskRepeatDatetime(ldt);
					 * taskRepeat.setStatus("TO_DO"); } });
					 */
					
					
					  for(String weekDay:task.getTaskRepeatDay()) {
					  if(ldt.getDayOfWeek().name().equals(weekDay.toUpperCase())) 
					  	{ 
						  if(!userMapList.isEmpty()) {
							  for(Map.Entry<Long, String> user: userMapList.entrySet()) {
									  TaskRepeat taskRepeat = new TaskRepeat(); 
									  taskRepeat.setTaskRepeatDatetime(ldt);
									  taskRepeat.setStatus("TO_DO"); 
									  taskRepeat.setTaskRepeatWeekDay(weekDay);
									  System.out.println("weekDay"+weekDay); 
									  taskRepeat.setTask(task);
									  taskRepeat.setAppuser(appUserList.stream().filter(u->u.getId().longValue()
											  ==user.getKey()).findAny().orElse(null));
									  taskRepeat.setAssignType(user.getValue());
									  taskRepeatList.add(taskRepeat);
								}
							}
						   
					  	} 
					  }
					 
					ldt = ldt.plusHours(24);
				}
			}
		} else if (task.getRepeatType().equals("monthly")) {
			if (task.getRepeatType().equals("monthly")) {
				Long monthsBetween = java.time.temporal.ChronoUnit.MONTHS.between(task.getStartDate(),
						task.getEndDate());
				for (int i = 0; i < monthsBetween + 1; i++) {
					if(!userMapList.isEmpty()) {
						for(Map.Entry<Long, String> user: userMapList.entrySet()) {
							TaskRepeat taskRepeat = new TaskRepeat();
							taskRepeat.setTask(task);
							taskRepeat.setStatus("TO_DO");
							taskRepeat.setTask(task);
							taskRepeat.setAppuser(appUserList.stream().filter(u->u.getId()==user.getKey()).findFirst().get());
							taskRepeat.setAssignType(user.getValue());
							if (i > 0)
								taskRepeat.setTaskRepeatDatetime(task.getStartDate().plusMonths(1));
							else
								taskRepeat.setTaskRepeatDatetime(task.getStartDate());
							taskRepeatList.add(taskRepeat);
						}
					}
					
				}
			}

		} else {
			Long yearsBetween = java.time.temporal.ChronoUnit.YEARS.between(task.getStartDate(), task.getEndDate());
			for (int i = 0; i < yearsBetween + 1; i++) {
				if(!userMapList.isEmpty()) {
					for(Map.Entry<Long, String> user: userMapList.entrySet()) {
						TaskRepeat taskRepeat = new TaskRepeat();
						taskRepeat.setTask(task);
						taskRepeat.setStatus("TO_DO");
						taskRepeat.setAppuser(appUserList.stream().filter(u->u.getId()==user.getKey()).findFirst().get());
						taskRepeat.setAssignType(user.getValue());
						if (i > 0)
							taskRepeat.setTaskRepeatDatetime(task.getStartDate().plusYears(1));
						else
							taskRepeat.setTaskRepeatDatetime(task.getStartDate());
					}

				}
				
			}

		}
		return taskRepeatList;
	}
	
	
	/**** find out the list of users of assigned dept/team/other users ****/
	public Map<Long,String> assignTeamAndNonTeamUser(Task task) {
		Map<Long,String> userMapList = new HashMap<Long,String>();
		List<TeamMembers> teamMembersList = null;
		if(task.getAssignedToTeam()!=null) {
			teamMembersList = teamMembersService.getTeamMembersListByTeamId(task.getAssignedToTeam());			
		}
		if(teamMembersList!=null) {
			for(TeamMembers teamMember: teamMembersList) {
				userMapList.put(teamMember.getUserId(), "team_task");
			}
			
		} 
		if(!task.getAssignedToOtherNonTeamUser().isEmpty()) {
			for(Long otherUser : task.getAssignedToOtherNonTeamUser()) {
				userMapList.put(otherUser, "other");
			}
		}
		if(userMapList.isEmpty()) {
			if(task.getAssignedToDept()!=null) {
				List<EmployeeConfig> employeeConfigList = employeeConfigRepository.findByDeptId(task.getAssignedToDept()); 
				for(EmployeeConfig employeeConfig: employeeConfigList) {
					userMapList.put(employeeConfig.getUserId(), "deptwise_team_task");
				}
			}
			
		}
		return userMapList;
	}
	
	
	/**** deleting and updating task repeat ****/
	boolean deleteTaskRepeat(Task task,List<TaskRepeat> taskRepeatList){
		taskRepeatList=taskRepeatList.stream().sorted(Comparator.comparing(TaskRepeat::getTaskRepeatDatetime).reversed()).collect(Collectors.toList());
		boolean success = false;
		LocalDateTime ldt = LocalDateTime.now();
		String str = ldt.toString().split("T")[0];
		System.out.println(str);
		LocalTimeParser localTimeParser = new LocalTimeParser();
		LocalDateTime currentDate = LocalDateTime.parse(str, localTimeParser.getTimeParser());
		List<TaskRepeat> previousTaskRepeatList = taskRepeatService.findTaskRepeatByTaskIdAndCurrentTime(task.getId(),ldt);
		List<TaskRepeat> deleteTaskRepeat = new ArrayList<TaskRepeat>();
		List<TaskRepeat> compareTaskRepeatList = new ArrayList<TaskRepeat>();
		Map<String,TaskRepeat> updateTaskRepeat = new HashMap<String,TaskRepeat>();
		boolean isExist = true; 
		if(!previousTaskRepeatList.isEmpty()) {
			for(TaskRepeat prevtaskRepeat: previousTaskRepeatList) {
				//LocalDateTime ldt2 = prevtaskRepeat.getTaskRepeatDatetime();
				//System.out.println(ldt2);
				//Long userId = prevtaskRepeat.getAppuser().getId();
				for(TaskRepeat newTaskRepeat: taskRepeatList) {
					if((prevtaskRepeat.getTaskRepeatDatetime().isEqual(newTaskRepeat.getTaskRepeatDatetime())) && prevtaskRepeat.getAppuser().getId().longValue() ==  newTaskRepeat.getAppuser().getId().longValue()) {
						if(updateTaskRepeat.containsKey(newTaskRepeat.getAppuser().getId()+newTaskRepeat.getTaskRepeatDatetime().toString())) {
							updateTaskRepeat.replace(newTaskRepeat.getAppuser().getId()+newTaskRepeat.getTaskRepeatDatetime().toString(), prevtaskRepeat);
						}
						else {
							updateTaskRepeat.put(newTaskRepeat.getAppuser().getId()+newTaskRepeat.getTaskRepeatDatetime().toString(),prevtaskRepeat);
						}
						
						isExist=true;
						break;
					}
					if(!(prevtaskRepeat.getTaskRepeatDatetime().isEqual(newTaskRepeat.getTaskRepeatDatetime())) && prevtaskRepeat.getAppuser().getId().longValue() ==  newTaskRepeat.getAppuser().getId().longValue()) {
						isExist = false;
					}
					if((prevtaskRepeat.getTaskRepeatDatetime().isEqual(newTaskRepeat.getTaskRepeatDatetime())) && prevtaskRepeat.getAppuser().getId().longValue() !=  newTaskRepeat.getAppuser().getId().longValue()) {
						isExist = false;
					}
					else {
						if(!updateTaskRepeat.containsKey(newTaskRepeat.getAppuser().getId()+newTaskRepeat.getTaskRepeatDatetime().toString())) {
							if(newTaskRepeat.getTaskRepeatDatetime().isAfter(ldt)) {
								updateTaskRepeat.put(newTaskRepeat.getAppuser().getId()+newTaskRepeat.getTaskRepeatDatetime().toString(),newTaskRepeat);
							}
							
						}

					}
				}
				//List<TaskRepeat> list= taskRepeatList.stream().filter(t->(!t.getTaskRepeatDatetime().isEqual(ldt2)) && t.getAppuser().getId().longValue()==userId.longValue()).collect(Collectors.toList());
				if(!isExist) {
					deleteTaskRepeat.add(prevtaskRepeat);
					isExist = true;
				}
			}
		List<TaskRepeat> list = new ArrayList<TaskRepeat>(updateTaskRepeat.values());
			try {
				taskRepeatService.deleteAllTaskRepeat(deleteTaskRepeat);
				taskRepeatService.saveAllTaskRepeat(list);
				success = true;
			}
			catch(Exception ex ){
				ex.printStackTrace();
			}
		}
		else {
			try {
				for(TaskRepeat taskRepeat: taskRepeatList) {
					TaskRepeat existingTaskRepeat = taskRepeatService.findByTaskIdAndRepeatDateTimeUserId(taskRepeat.getTask().getId(), taskRepeat.getTaskRepeatDatetime(), taskRepeat.getAppuser().getId());
					//List<TaskRepeat> taskRepeatListByDate = taskRepeatService.findByTaskIdAndTaskRepeatDatetime(taskRepeat.getTask().getId(), taskRepeat.getTaskRepeatDatetime());
					
					if(existingTaskRepeat!=null) {
						taskRepeat.setId(existingTaskRepeat.getId());
					}
					

						/*boolean exist = taskRepeatListByDate.stream().filter(t->t.getAppuser().getId().longValue()==taskRepeat.getAppuser().getId().longValue()).findFirst().isPresent();
						if(exist) {
							taskRepeatService.deleteOneTaskRepeat(taskRepeat);
						}*/
					
				}
				
				taskRepeatService.saveAllTaskRepeat(taskRepeatList);
				success = true;
			}
			catch(Exception ex ){
				ex.printStackTrace();
			}
		}

		
		
	
		/*if(deleteTaskRepeat.isEmpty())
			success = true;
		else {
			try {
				taskRepeatService.deleteAllTaskRepeat(deleteTaskRepeat);
				success = true;
			}
			catch(Exception ex ){
				ex.printStackTrace();
			}
		}*/
		
		return success;
		
	}
	
	
	
	
	
	/**** creating the list of task associate ****/
	List<TaskAssociate> setAllTaskAssociate(Task task){
		Map<Long,String> userMapList = new HashMap<Long,String>();
		List<AppUser>appUserList = null;
		if(task.getAssignedToTeam()!=null || task.getAssignedToOtherNonTeamUser()!=null) {
			userMapList = assignTeamAndNonTeamUser(task);
		}
		if(!userMapList.isEmpty())
			appUserList = appUserRepository.findAllById(userMapList.keySet());
		List<TaskAssociate> taskAssociateList = new ArrayList<TaskAssociate>();
		if(appUserList!=null) {
			for(AppUser appUser: appUserList) {
				TaskAssociate taskAssociate = new TaskAssociate();
				taskAssociate.setTask(task);
				taskAssociate.setUserStatus(true);
				taskAssociate.setAppUser(appUser);
				if(task.getStatus().equals("TO_DO"))
					taskAssociate.setStatus("TO_DO");
				else if(task.getStatus().equals("IN_PROGRESS"))
					taskAssociate.setStatus("IN_PROGRESS");
				else
					taskAssociate.setStatus("COMPLETED");

				String assignType = userMapList.get(appUser.getId());
				taskAssociate.setAssignType(assignType);
				taskAssociateList.add(taskAssociate);
			}
		}
		
		if(!taskAssociateList.isEmpty()) {
			return taskAssociateList;
		}
		else {
			return null;
		}
		
		
	}
	
	/**** updating & checking the list of task associate ****/
	boolean checkAndUpdateTaskAssociate(Task task, List<TaskAssociate> newTaskAssociateList){
		boolean success = false;
		List<TaskAssociate> taskAssociateList = taskAssociateService.findTaskAssociateByTaskId(task.getId());
		List<TaskAssociate> updatedTaskAssociateList = new ArrayList<TaskAssociate>();
		for(TaskAssociate taskAssociate: taskAssociateList) {
			/*for(Long userId: task.getAssignedToOtherNonTeamUser()) {
				if(taskAssociate.getId().longValue()!=userId.longValue()) {
					//taskAssociate.setUserStatus(false);
					System.out.println(taskAssociate.getId()+"user"+userId.longValue());
				}
			}*/
			if(!task.getAssignedToOtherNonTeamUser().contains(taskAssociate.getAppUser().getId()) && taskAssociate.getAssignType().equals("other"))
				taskAssociate.setUserStatus(false);
			updatedTaskAssociateList.add(taskAssociate);
		}
		Optional<TaskAssociate> optionalTaskAssociate = null;
		if(newTaskAssociateList!=null) {
			for(TaskAssociate newTaskAssociate: newTaskAssociateList) {
				boolean exist = false;
				for(TaskAssociate updatedTaskAssociate: updatedTaskAssociateList) {
					if(newTaskAssociate.getAppUser().getId().longValue()==updatedTaskAssociate.getAppUser().getId().longValue()) {
						exist = true;
						break;
					}
			
				}
				if(exist==false) {
					System.out.println(newTaskAssociate.getAppUser().getId());
					updatedTaskAssociateList.add(newTaskAssociate);
				}
				
			}
		}
		
		
		
		if(updatedTaskAssociateList.isEmpty())
			success=true;
		else {
			try {
				taskAssociateService.updateAllTaskAssociate(updatedTaskAssociateList);
				success = true;
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		//!task.getAssignedToOtherNonTeamUser().contains(taskAssociate.getAppUser().getId().longValue())
		return success;
	}

	

}
