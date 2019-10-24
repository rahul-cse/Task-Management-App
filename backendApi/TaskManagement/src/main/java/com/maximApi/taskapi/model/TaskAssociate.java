package com.maximApi.taskapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="task_associate")
public class TaskAssociate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_association_seq")
	@SequenceGenerator(name = "task_association_seq", allocationSize = 1)
	@Column(name="id")
	private Long id;

	@Column(name="assignee_remarks")
	private String assigneeRemarks;
	@Column(name="task_creator_remarks")
	private String taskCreatorRemarks;
	@Column(name="task_started_datetime")
	private LocalDateTime taskStartedDateTime;
	@Column(name="status")
	private String status;
	@Column(name="user_status")
	private boolean userStatus; 		/* 0 = Disable, 1 = Active */
	@Column(name="assign_type")
	private String assignType;


	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private AppUser appUser;
	@ManyToOne
	@JoinColumn(name="task_id", nullable = false)
	private Task task;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getAssigneeRemarks() {
		return assigneeRemarks;
	}

	public void setAssigneeRemarks(String assigneeRemarks) {
		this.assigneeRemarks = assigneeRemarks;
	}

	public String getTaskCreatorRemarks() {
		return taskCreatorRemarks;
	}

	public void setTaskCreatorRemarks(String taskCreatorRemarks) {
		this.taskCreatorRemarks = taskCreatorRemarks;
	}
	
	public LocalDateTime getTaskStartedDateTime() {
		return taskStartedDateTime;
	}

	public void setTaskStartedDateTime(LocalDateTime taskStartedDateTime) {
		this.taskStartedDateTime = taskStartedDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public boolean isUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}
	
}
