package com.maximApi.taskapi.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="TASK")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
	@SequenceGenerator(name = "task_seq", allocationSize = 1)
	@Column(name="id")
	private Long id;
	@NotEmpty(message="Enter atleast 3 characters")
	@Size(min=3)
	@Column(name="title")
	//@Column(name="title",unique=true)
	private String title;
	@NotNull
	@Column(name="task_type_id")
	private Long taskTypeId;
	@Column(name="description")
	private String description;
	@Column(name="assigned_type")
	private String assignedType;
	@Column(name="assigned_to")
	private Long assignedTo;
	@Column(name="assigned_dept")
	private Long assignedToDept;
	@Column(name="assigned_team")
	private Long assignedToTeam;
	//private Long associates;
	@Column(name="repeat")
	private boolean repeat;
	@Column(name="repeat_type")
	private String repeatType;
	@NotNull
	@Column(name="area_id")
	private Long areaId;
	//private Long sharedWith;
	@Column(name="entry_date")
	private LocalDateTime entryDate;
	//@FutureOrPresent
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	@Column(name="start_date")
	private LocalDateTime startDate;
	//@FutureOrPresent
	@Column(name="end_date")
	private LocalDateTime endDate;
	@NotNull
	@Column(name="entry_by")
	private Long entryBy;
	@Column(name="status")
	private String status;
	//@OneToOne//(fetch= FetchType.LAZY)
	//@JoinColumn(name="assigned_dept", referencedColumnName = "id")
	//private Department department;
	//@ElementCollection
	@Transient
	private List<String> taskRepeatDay;
	//@ElementCollection
	@Transient
	private List<Long> assignedToOtherNonTeamUser;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAssignedType() {
		return assignedType;
	}
	public void setAssignedType(String assignedType) {
		this.assignedType = assignedType;
	}
	public Long getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	public Long getAssignedToDept() {
		return assignedToDept;
	}
	public void setAssignedToDept(Long assignedToDept) {
		this.assignedToDept = assignedToDept;
	}
	public Long getAssignedToTeam() {
		return assignedToTeam;
	}
	public void setAssignedToTeam(Long assignedToTeam) {
		this.assignedToTeam = assignedToTeam;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	public String getRepeatType() {
		return repeatType;
	}
	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}
	public Long getAreaId()
	{ 
		return areaId; 
	} 
	public void setAreaId(Long areaId)
	{ 
		this.areaId = areaId; 
	} 
	
	public LocalDateTime getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(LocalDateTime entryDate) {
		this.entryDate = entryDate;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	/**public Long getSharedWith() { return sharedWith; }
	 * public void setSharedWith(Long sharedWith) { this.sharedWith = sharedWith; }
	 */
	
	
	
	public Long getEntryBy() {
		return entryBy;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public void setEntryBy(Long entryBy) {
		this.entryBy = entryBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*
	 * public Department getDepartment() { return department; } public void
	 * setDepartment(Department department) { this.department = department; }
	 */
	public List<String> getTaskRepeatDay() {
		return taskRepeatDay;
	}
	public void setTaskRepeatDay(List<String> taskRepeatDay) {
		this.taskRepeatDay = taskRepeatDay;
	}
	public List<Long> getAssignedToOtherNonTeamUser() {
		return assignedToOtherNonTeamUser;
	}
	public void setAssignedToOtherNonTeamUser(List<Long> assignedToOtherNonTeamUser) {
		this.assignedToOtherNonTeamUser = assignedToOtherNonTeamUser;
	}
	
	
	
}
