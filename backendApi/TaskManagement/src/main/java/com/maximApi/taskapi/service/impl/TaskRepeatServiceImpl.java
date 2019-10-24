package com.maximApi.taskapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maximApi.taskapi.model.TaskRepeat;
import com.maximApi.taskapi.repository.TaskRepeatRepository;
import com.maximApi.taskapi.service.TaskRepeatService;

@Service
public class TaskRepeatServiceImpl implements TaskRepeatService{

	@Autowired
	TaskRepeatRepository taskRepeatRepository;
	
	@Override
	public void saveAllTaskRepeat(List<TaskRepeat> taskRepeatList) {
		// TODO Auto-generated method stub
		taskRepeatRepository.saveAll(taskRepeatList);
	}

	@Override
	public List<TaskRepeat> findAllTaskRepeat() {
		// TODO Auto-generated method stub
		return taskRepeatRepository.findAll();
	}

	@Override
	public List<TaskRepeat> findOneTaskRepeatDetails(Long taskId) {
		// TODO Auto-generated method stub
		TaskRepeat taskRepeat = new TaskRepeat();
		//taskRepeat.getTask().
		List<TaskRepeat> taskRepeatList = taskRepeatRepository.findByTaskId(taskId);
		if(taskRepeatList.isEmpty())
			return null;
		else
			return taskRepeatList;
	}

	@Override
	public TaskRepeat updateTaskRepeat(TaskRepeat taskRepeat) {
		try {
			TaskRepeat currentTaskRepeat = getOneTaskRepeat(taskRepeat.getId());
			taskRepeat.setTaskRepeatDatetime(currentTaskRepeat.getTaskRepeatDatetime());
			if(currentTaskRepeat.getStatus().equals("TO_DO") && taskRepeat.getStatus().equals("IN_PROGRESS"))
				taskRepeat.setTaskStartDateTime(LocalDateTime.now());
			taskRepeatRepository.save(taskRepeat);
			return taskRepeat;
		}
		catch(Exception ex){
			return null;
		}

	}

	@Override
	public TaskRepeat getOneTaskRepeat(Long taskRepeatId) {
		Optional<TaskRepeat> optionalTaskRepeat = taskRepeatRepository.findById(taskRepeatId);
		TaskRepeat taskRepeat = new TaskRepeat();
		if(optionalTaskRepeat.isPresent()) {
			taskRepeat = optionalTaskRepeat.get();
		}
		return taskRepeat;
	}

	@Override
	public List<TaskRepeat> findAllTaskRepeatByTaskId(Long taskId) {
		
		List<TaskRepeat> taskRepeatList = taskRepeatRepository.findByTaskId(taskId);
		return null;
	}

	@Override
	public void deleteAllTaskRepeat(List<TaskRepeat> taskRepeatList) {
		taskRepeatRepository.deleteAll(taskRepeatList);
		
	}

	@Override
	public List<Object[]> findAllRepeatWeekdayAndUserByTaskId(Long taskId, LocalDateTime ldt) {
		
		List<Object[]> weekdays = taskRepeatRepository.findTaskRepeatWeekDayUserByTaskId(taskId, ldt); 
		return weekdays;
	}

	@Override
	public List<TaskRepeat> findTaskRepeatByTaskIdAndCurrentTime(Long taskId, LocalDateTime ldt) {
		
		List<TaskRepeat> taskRepeatList = taskRepeatRepository.findByTaskIdAndCurrentTime(taskId, ldt);
		return taskRepeatList;
	}

	@Override
	public TaskRepeat findByTaskIdAndRepeatDateTimeUserId(Long taskId, LocalDateTime ldt, Long userId) {
		
		TaskRepeat taskRepeat = taskRepeatRepository.findByTaskIdAndTaskRepeatDatetimeAndAppUserId(taskId, ldt, userId);
		return taskRepeat;
	}

	@Override
	public List<TaskRepeat> findByTaskIdAndTaskRepeatDatetime(Long taskId, LocalDateTime ldt) {
		
		List<TaskRepeat> taskRepeatList = taskRepeatRepository.findByTaskIdAndTaskRepeatDatetime(taskId, ldt);
		return taskRepeatList;
	}

	@Override
	public void deleteOneTaskRepeat(TaskRepeat taskRepeat) {
		
		taskRepeatRepository.delete(taskRepeat);
	}

}
