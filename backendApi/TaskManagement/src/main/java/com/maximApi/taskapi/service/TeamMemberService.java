package com.maximApi.taskapi.service;

import java.util.List;

import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.TeamMembers;

public interface TeamMemberService {

	List<TeamMembers> getTeamMembersListByTeamId(Long teamId);
	
	List<AppUser> getNonTeamMembers(Long deptId);
}
