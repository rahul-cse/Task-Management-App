package com.maximApi.taskapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.maximApi.taskapi.model.OMS_USERS;
import com.maximApi.taskapi.model.TaskType;
import com.maximApi.taskapi.model.projection.TaskTypeUiView;
import com.maximApi.taskapi.model.projection.TaskUiView;
import com.maximApi.taskapi.repository.OmsUsersRepository;
import com.maximApi.taskapi.repository.TaskTypeRepository;
import com.maximApi.taskapi.service.TaskTypeService;
import com.maximApi.taskapi.util.UuIdGenerator;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

	@Autowired
	TaskTypeRepository taskTypeRepository;
	@Autowired
	OmsUsersRepository omsUsersRepository;
	
	@Override
	public TaskType createTaskType(TaskType taskType) {
		// TODO Auto-generated method stub
		TaskType newTaskType = taskTypeRepository.save(taskType);
		//TaskType newTaskType = new TaskType();
		return newTaskType;
	}

	@Override
	public List<TaskType> showAllTaskType() {
		// TODO Auto-generated method stub
		List<TaskType> taskTypeLists = taskTypeRepository.findAll();
		//List<TaskUiView> taskUiViewList = new ArrayList<TaskUiView>(taskTypeLists.size());
		/*
		 * for(TaskType taskType:taskTypeLists) { TaskTypeUiView taskUiView = new
		 * TaskTypeUiView(); BeanUtils.copyProperties(taskType, taskUiView);
		 * taskUiView.setAreaName(omsUsersRepository.fin);
		 * taskUiViewList.add(taskUiView); }
		 */
		
		if(!taskTypeLists.isEmpty())
			return taskTypeLists;
		else
			return null;
	}

	@Override
	public List<TaskType> findTaskTypesByAreaId(long areaId) {
		// TODO Auto-generated method stub
		List<TaskType> taskTypeLists = taskTypeRepository.findByAreaId(areaId);
		
		return 	taskTypeLists;
					
	}

	@Override
	public TaskType editTaskType(TaskType taskType) {
		// TODO Auto-generated method stub
		TaskType newTaskType = taskTypeRepository.save(taskType);
		return newTaskType;
	}

	@Override
	public Optional<TaskType> getTaskTypeById(long id) {
		// TODO Auto-generated method stub
		 Optional<TaskType> optionalTaskType = taskTypeRepository.findById(id);
		return optionalTaskType;
	}

}
