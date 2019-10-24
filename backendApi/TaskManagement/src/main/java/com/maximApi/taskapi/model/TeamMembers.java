package com.maximApi.taskapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TEAM_MEMBER")
public class TeamMembers {
	@Id
	@Column(name="id")
	private Long id;
	@ManyToOne
	@JoinColumn(name="team_id")
	private TEAMS team;
	@Column(name="user_id")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TEAMS getTeam() {
		return team;
	}

	public void setTeam(TEAMS team) {
		this.team = team;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	
	

}
