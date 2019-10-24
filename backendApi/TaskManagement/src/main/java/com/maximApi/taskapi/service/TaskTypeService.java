package com.maximApi.taskapi.service;

import java.util.List;
import java.util.Optional;

import com.maximApi.taskapi.model.TaskType;

public interface TaskTypeService {

	TaskType createTaskType(TaskType taskType); 				/* creating/saving new task type */
	
	List<TaskType> showAllTaskType(); 							/* find all task types from database tables */
	
	List<TaskType> findTaskTypesByAreaId(long areaId); 			/* find all task types according to supplied areaId */
	
	Optional<TaskType> getTaskTypeById(long id); 				/* get task type according to task type id */
	
	TaskType editTaskType(TaskType taskType); 					/* edit previous task type. */
}
