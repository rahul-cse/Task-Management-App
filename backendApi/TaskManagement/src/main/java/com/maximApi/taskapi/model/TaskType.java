package com.maximApi.taskapi.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;




@Entity
@Table(name="TASK_TYPE")
public class TaskType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_type_seq")
	@SequenceGenerator(name = "task_type_seq", allocationSize = 1)
	@Column(name="ID")
	private Long id;
	@Column(name="TASK_TYPE_NAME")
	@NotEmpty(message="Task Type Name can not be blank")
	private String name;
	@Column(name="AREA_ID")
	@NotNull
	@Min(1)
	private Long areaId = (long) 6113;
	@Column(name="DESCRIPTION")
	private String description;

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	







	
	
	
}
