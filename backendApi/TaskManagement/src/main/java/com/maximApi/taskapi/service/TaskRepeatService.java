package com.maximApi.taskapi.service;

import java.time.LocalDateTime;
import java.util.List;

import com.maximApi.taskapi.model.TaskRepeat;

public interface TaskRepeatService {

	public void saveAllTaskRepeat(List<TaskRepeat> taskRepeatList);
	
	public TaskRepeat getOneTaskRepeat(Long taskRepeatId);
	
	public List<TaskRepeat> findAllTaskRepeat();
	
	public List<TaskRepeat> findOneTaskRepeatDetails(Long taskId);
	
	public TaskRepeat updateTaskRepeat(TaskRepeat taskRepeat);
	
	public List<TaskRepeat> findAllTaskRepeatByTaskId(Long taskId);
	
	public void deleteAllTaskRepeat(List<TaskRepeat> taskRepeatList);
	
	public void deleteOneTaskRepeat(TaskRepeat taskRepeat);
	
	public List<Object[]> findAllRepeatWeekdayAndUserByTaskId(Long taskId, LocalDateTime ldt);
	
	public List<TaskRepeat> findTaskRepeatByTaskIdAndCurrentTime(Long taskId, LocalDateTime ldt);
	
	public List<TaskRepeat> findByTaskIdAndTaskRepeatDatetime (Long taskId, LocalDateTime ldt);
	
	public TaskRepeat findByTaskIdAndRepeatDateTimeUserId(Long taskId, LocalDateTime ldt, Long userId);
}
