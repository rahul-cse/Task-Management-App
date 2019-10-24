package com.maximApi.taskapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maximApi.taskapi.model.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long>{
	
	List<TaskType> findByAreaId(long areaId);

}
