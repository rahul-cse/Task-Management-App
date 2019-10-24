package com.maximApi.taskapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.stereotype.Service;

import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.EmployeeConfig;
import com.maximApi.taskapi.model.TEAMS;
import com.maximApi.taskapi.model.TeamMembers;
import com.maximApi.taskapi.repository.AppUserRepository;
import com.maximApi.taskapi.repository.EmployeeConfigRepository;
import com.maximApi.taskapi.repository.TeamMembersRepository;
import com.maximApi.taskapi.repository.TeamRepository;
import com.maximApi.taskapi.service.TeamMemberService;
import com.maximApi.taskapi.service.TeamService;

@Service
public class TeamMembersServiceImpl implements TeamMemberService{

	
	TeamMembersRepository teamMembersRepository;
	TeamRepository teamRepository;
	TeamService teamService;
	EmployeeConfigRepository employeeConfigRepository;
	AppUserRepository appUserRepository;
	
		
	@Autowired
	public TeamMembersServiceImpl(TeamMembersRepository teamMembersRepository, TeamRepository teamRepository,
			TeamService teamService, EmployeeConfigRepository employeeConfigRepository,
			AppUserRepository appUserRepository) {
		this.teamMembersRepository = teamMembersRepository;
		this.teamRepository = teamRepository;
		this.teamService = teamService;
		this.employeeConfigRepository = employeeConfigRepository;
		this.appUserRepository = appUserRepository;
	}




	@Override
	public List<AppUser> getNonTeamMembers(Long deptId) {

		List<AppUser> nonTeamMemberList  = new ArrayList<AppUser>();
		List<EmployeeConfig> employeeConfigList = employeeConfigRepository.findByDeptId(deptId);
		List<TeamMembers> teamMembersList = new ArrayList<TeamMembers>();
		List<TEAMS> teamLists = teamService.getTeamByDeptId(deptId);
		for(TEAMS team: teamLists) {
			teamMembersList = teamMembersRepository.findByTeamId(team.getId());
			
		}
		
		for(EmployeeConfig employeeConfig: employeeConfigList) {
			for(TeamMembers teamMembers: teamMembersList) {
				if(employeeConfig.getUserId().longValue()!=teamMembers.getUserId().longValue()) {
					AppUser appUser = new AppUser();
					Optional<AppUser> optionalAppUser = appUserRepository.findById(employeeConfig.getUserId());
					if(optionalAppUser.isPresent()) {
						appUser = optionalAppUser.get();
						nonTeamMemberList.add(appUser);
					}
				}
			}
			
		}
		
		if(!nonTeamMemberList.isEmpty()) {
			return nonTeamMemberList;
		}
		else {
			return null;
		}
			
	}




	@Override
	public List<TeamMembers> getTeamMembersListByTeamId(Long teamId) {
		
		List<TeamMembers> teamMemebersList = teamMembersRepository.findByTeamId(teamId);
		if(!teamMemebersList.isEmpty())
			return teamMemebersList;
		else
			return null;
	}

}
