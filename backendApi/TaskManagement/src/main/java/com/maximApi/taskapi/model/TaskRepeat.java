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
@Table(name="TASK_REPEAT")
public class TaskRepeat {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_repeat_seq")
	@SequenceGenerator(name = "task_repeat_seq", allocationSize = 1)
	@Column(name="id")
	private Long id;

	@Column(name="task_Repeat_Date_Time")
	private LocalDateTime taskRepeatDatetime;
	@Column(name="task_Repeat_Week_Day")
	private String taskRepeatWeekDay;
	@Column(name="assignee_Remarks")
	private String assigneeRemarks;
	@Column(name="task_Creator_Remarks")
	private String taskCreatorRemarks;
	@Column(name="task_repeat_started_datetime")
	private LocalDateTime taskStartDateTime;
	@Column(name="assign_type")
	private String assignType;
	

	@Column(name="status")
	private String status;
	@ManyToOne
	@JoinColumn(name="task_id", nullable = false)
	private Task task;
	@OneToOne
	@JoinColumn(name="user_id")
	private AppUser appuser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public LocalDateTime getTaskRepeatDatetime() {
		return taskRepeatDatetime;
	}

	public void setTaskRepeatDatetime(LocalDateTime taskRepeatDatetime) {
		this.taskRepeatDatetime = taskRepeatDatetime;
	}

	public String getTaskRepeatWeekDay() {
		return taskRepeatWeekDay;
	}

	public void setTaskRepeatWeekDay(String taskRepeatWeekDay) {
		this.taskRepeatWeekDay = taskRepeatWeekDay;
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
	
	public LocalDateTime getTaskStartDateTime() {
		return taskStartDateTime;
	}

	public void setTaskStartDateTime(LocalDateTime taskStartDateTime) {
		this.taskStartDateTime = taskStartDateTime;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public AppUser getAppuser() {
		return appuser;
	}

	public void setAppuser(AppUser appuser) {
		this.appuser = appuser;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
