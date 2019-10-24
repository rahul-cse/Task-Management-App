package com.maximApi.taskapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximApi.taskapi.exception.ResourceNotFoundException;
import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.payload.ApiResponse;
import com.maximApi.taskapi.repository.EmployeeConfigRepository;
import com.maximApi.taskapi.repository.TeamMembersRepository;
import com.maximApi.taskapi.service.TeamMemberService;

@RestController
@RequestMapping("api/task")
@CrossOrigin("*")
@Validated
public class TeamMembersController {
	
	private static final Logger logger = LoggerFactory.getLogger(TeamMembersController.class);
	
	TeamMemberService teamMemberService;
	
	@Autowired
	public TeamMembersController(TeamMemberService teamMemberService) {
		this.teamMemberService = teamMemberService;
	}



	@GetMapping("get-nonteam-members/{dept_id}")
	public ResponseEntity getTeamMembersByDept(@PathVariable String dept_id) {
		Long deptId = null;
		try {
			deptId = Long.parseLong(dept_id);
		}
		catch(Exception ex){
			logger.error("Long Parse Error");
			ex.printStackTrace();
		}
		if(deptId!=null) {
			List<AppUser> nonTeamMembersList = teamMemberService.getNonTeamMembers(deptId);
			if(nonTeamMembersList!=null) {
				return ResponseEntity.ok(new ApiResponse(true,nonTeamMembersList));
			}
			else {
				throw new ResourceNotFoundException("team members","department Id",dept_id);
			}
		}
		else {
			throw new ResourceNotFoundException("teams","department Id",dept_id);
		}
	}
}
