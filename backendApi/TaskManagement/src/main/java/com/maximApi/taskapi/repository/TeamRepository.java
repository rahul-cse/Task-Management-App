package com.maximApi.taskapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maximApi.taskapi.model.TEAMS;

public interface TeamRepository extends JpaRepository<TEAMS, Long>{

	List<TEAMS> findByDeptId(Long deptId);
}
