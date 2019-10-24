package com.maximApi.taskapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maximApi.taskapi.model.TEAMS;
import com.maximApi.taskapi.repository.TeamRepository;
import com.maximApi.taskapi.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	TeamRepository teamRepository;
	
	@Override
	public List<TEAMS> getTeamByDeptId(Long deptId) {

		
		  List<TEAMS> teamList = teamRepository.findByDeptId(deptId);
		  if(!teamList.isEmpty()) 
			  return teamList; 
		  else		 
			return null;
	}

}
