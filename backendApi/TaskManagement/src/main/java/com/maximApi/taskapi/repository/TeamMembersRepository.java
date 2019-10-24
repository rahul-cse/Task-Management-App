package com.maximApi.taskapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maximApi.taskapi.model.TeamMembers;

public interface TeamMembersRepository extends JpaRepository<TeamMembers, Long>{

	List<TeamMembers> findByTeamId(Long teamId);
	List<TeamMembers> findByUserId(Long userId);
}
