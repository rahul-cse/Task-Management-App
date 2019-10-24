package com.maximApi.taskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maximApi.taskapi.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
