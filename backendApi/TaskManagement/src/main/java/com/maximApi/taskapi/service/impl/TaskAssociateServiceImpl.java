package com.maximApi.taskapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maximApi.taskapi.model.TaskAssociate;
import com.maximApi.taskapi.repository.TaskAssociateRepository;
import com.maximApi.taskapi.service.TaskAssociateService;

@Service
public class TaskAssociateServiceImpl implements TaskAssociateService{

	@Autowired
	TaskAssociateRepository taskAssociateRepository;
	
	@Override
	public void createAllTaskAssociate(List<TaskAssociate> taskAssociateList) {
		taskAssociateRepository.saveAll(taskAssociateList);
		
	}

	@Override
	public List<TaskAssociate> findTaskAssociateByTaskId(Long taskId) {
		
		List<TaskAssociate> taskAssociateList =  taskAssociateRepository.findByTaskId(taskId);
		return taskAssociateList;
	}

	@Override
	public void updateAllTaskAssociate(List<TaskAssociate> taskAssociateList) {
		
		taskAssociateRepository.saveAll(taskAssociateList);
	}

	@Override
	public List<Long> findTaskAssociateUserIdByTaskId(Long taskId) {
		
		List<Long> taskAssociateUserId = taskAssociateRepository.findTaskAssociateByTaskId(taskId);
		return taskAssociateUserId;
	}

}
