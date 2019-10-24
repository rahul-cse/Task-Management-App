package com.maximApi.taskapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maximApi.taskapi.model.EmployeeConfig;

public interface EmployeeConfigRepository extends JpaRepository<EmployeeConfig, Long>{

	List<EmployeeConfig> findByDeptId(Long deptId);
}
