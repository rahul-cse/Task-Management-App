package com.maximApi.taskapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximApi.taskapi.exception.ResourceNotFoundException;
import com.maximApi.taskapi.model.TEAMS;
import com.maximApi.taskapi.model.payload.ApiResponse;
import com.maximApi.taskapi.service.TeamService;

@RestController
@RequestMapping("api/task")
@CrossOrigin("*")
public class TeamController {
	
	TeamService teamService;

	@Autowired
	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping("/getTeam-byDept/{deptId}")
	public ResponseEntity getTeamByDept(@PathVariable Long deptId) {
		List<TEAMS> teamList = teamService.getTeamByDeptId(deptId);
		if(teamList!=null)
			return ResponseEntity.ok(new ApiResponse(true, teamList));
		else
			throw new ResourceNotFoundException("team","empty records","");
		
	} 
}
