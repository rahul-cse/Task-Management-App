package com.maximApi.taskapi.service;

import java.util.List;

import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.Department;
import com.maximApi.taskapi.model.OMS_USERS;
import com.maximApi.taskapi.model.Task;
import com.maximApi.taskapi.model.projection.TaskUiView;

public interface TaskService {

	Task createTask(Task task);
	
	List<TaskUiView> getAllTasks();
	
	Task getTask(long taskId);
	
	TaskUiView getTaskDetails(long taskId);
	
	Task updateTask(Task task);
	
	List<AppUser> getAllUser();
	
	List<Department> getAllDept();
	
	Department getDept(Long deptId);
	
	List<TaskUiView> getAllTasksByAreaId(long areaId);
	
	List<TaskUiView> getAllTaskByAreaIdAndEntryDateWise(long areaId,String fromDate, String toDate);
	
	List<TaskUiView> getAllTaskByTaskCreator(long entryBy);
	
	List<TaskUiView> getAllTaskByTaskCreatorAndEntryDateWise(long entryBy, String fromDate, String toDate);
	
	List<TaskUiView> getAllTaskByAssignedTo(long assignedTo);
	
	List<TaskUiView> getAllTaskByDeptId(long userId, long deptId);
	
	List<TaskUiView> getAllTaskByDeptIdAndStartDateEndDateWise(long userId, long deptId,String startDate, String endDate);
	
	List<TaskUiView> getAllTaskByAreaIdANDTaskType(Long areaId,List<Long> listOfTaskTypeId);
	
	List<TaskUiView> getAllTaskByAreaIdAndStartDateEndDateWise(long areaId, String startDate, String endDate);
	
	List<TaskUiView> getAllTaskByUserId(Long userId);
	
	List<TaskUiView> getAllTaskByUserIdAndTaskStatus(Long userId, String status);
}
