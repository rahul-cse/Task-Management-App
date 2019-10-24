package com.maximApi.taskapi.service;

import java.util.List;

import com.maximApi.taskapi.model.TEAMS;

public interface TeamService {

	public List<TEAMS> getTeamByDeptId(Long deptId);

}
