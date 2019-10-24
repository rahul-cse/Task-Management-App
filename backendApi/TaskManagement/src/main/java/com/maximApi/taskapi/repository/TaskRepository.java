package com.maximApi.taskapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maximApi.taskapi.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	List<Task> findByAreaId(Long areaId);
	
	List<Task> findByEntryBy(Long entryBy);
	
	List<Task> findByAssignedTo(Long assignedTo);
	
	List<Task> findByTaskTypeId(Long taskTypeId);
	
	List<Task> findByEntryByOrAssignedTo(Long entryBy, Long assignedTo);
	
	@Query("Select t from Task t where t.areaId = :areaId and (TRUNC(t.entryDate) >= :fromDateLocal and TRUNC(t.entryDate)<= :toLocalDate)")
	List<Task> findTaskByAreaIdAndFromToDate(@Param("areaId") Long areaId, @Param("fromDateLocal") LocalDateTime fromDateLocal, @Param("toLocalDate") LocalDateTime toLocalDate);
	
	
	@Query("Select t from Task t where t.entryBy = : entryBy") 
	List<Task>findTaskByEntryBy(@Param("entryBy") Long entryBy);
	 
	@Query("Select t from Task t where t.entryBy = :entryBy and (TRUNC(t.entryDate) >= :fromDateLocal and TRUNC(t.entryDate)<= :toLocalDate)")
	List<Task> findTaskByEntryByAndFromToDate(@Param("entryBy") Long entryBy, @Param("fromDateLocal") LocalDateTime fromDateLocal, @Param("toLocalDate") LocalDateTime toLocalDate);
	 
	@Query("Select t from Task t where t.assignedToDept = :deptId")
	List<Task> findTaskByDeptId(@Param("deptId") Long deptId);
	
	@Query("Select t from Task t where t.assignedToDept = :deptId and (TRUNC(t.startDate)>= :startDate and TRUNC(t.endDate)<= :endDate)")
	List<Task> findTaskByDeptStartAndEndDateWise(@Param("deptId") Long deptId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
	
	@Query("Select t from Task t where t.areaId = :areaId and t.taskTypeId = :taskTypeId")
	List<Task> findTaskByAreaIdAndTaskTypeId(@Param("areaId") Long areaId, @Param("taskTypeId") Long taskTypeId);
	
	@Query("Select t from Task t where t.areaId = :areaId and (TRUNC(t.startDate)>= :startDate and TRUNC(t.endDate)<= :endDate)")
	List<Task> findTaskByAreaIdStartAndEndDateWise(@Param("areaId") Long areaId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
