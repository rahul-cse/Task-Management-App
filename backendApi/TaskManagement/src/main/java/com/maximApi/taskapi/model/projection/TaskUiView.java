package com.maximApi.taskapi.model.projection;

import java.time.LocalDateTime;
import java.util.Date;

public class TaskUiView {
	
	private Long taskId;
	private String title;
	private String description;
	private String taskTypeName;
	private String assignedType;
	private String assignedUser;
	private String assignedDept;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String status;
	private boolean repeat; 
	
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTaskTypeName() {
		return taskTypeName;
	}
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}
	public String getAssignedType() {
		return assignedType;
	}
	public void setAssignedType(String assignedType) {
		this.assignedType = assignedType;
	}
	public String getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	public String getAssignedDept() {
		return assignedDept;
	}
	public void setAssignedDept(String assignedDept) {
		this.assignedDept = assignedDept;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	
	
	
}
