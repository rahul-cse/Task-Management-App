package com.maximApi.taskapi.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.maximApi.taskapi.validation.annotation.ValidUuid;

@Entity
@Table(name="OMS_USERS")
public class AppUser {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="UUID")
	@ValidUuid
	private String uuId;
	@Column(name="USERNAME")
	@NotEmpty(message="User Name can not be blank")
	private String userName;
	@Column(name="PASS")
	@NotEmpty(message="Password can not be blank")
	private String password;
	@Column(name="AREA_ID")
	@NotNull(message="Area Id can not be blank")
	@Min(1)
	private Long areaId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUuId() {
		return uuId;
	}
	public void setUuId(String uuId) {
		this.uuId = uuId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	
	
}
