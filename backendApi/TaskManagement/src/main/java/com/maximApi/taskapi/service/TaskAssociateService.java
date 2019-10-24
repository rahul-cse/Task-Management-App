package com.maximApi.taskapi.service;

import java.util.List;

import com.maximApi.taskapi.model.TaskAssociate;

public interface TaskAssociateService {

	public void createAllTaskAssociate(List<TaskAssociate> taskAssociateList);
	
	public List<TaskAssociate> findTaskAssociateByTaskId(Long taskId);
	
	public void updateAllTaskAssociate(List<TaskAssociate> taskAssociateList);
	
	public List<Long> findTaskAssociateUserIdByTaskId(Long taskId);
}
