package com.maximApi.taskapi.model.projection;

public class TaskTypeUiView {

	private Long id;
	private String name;
	private Long areaName;
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
	public Long getAreaName() {
		return areaName;
	}
	public void setAreaName(Long areaName) {
		this.areaName = areaName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
