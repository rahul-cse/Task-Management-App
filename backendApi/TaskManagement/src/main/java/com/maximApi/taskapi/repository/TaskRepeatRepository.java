package com.maximApi.taskapi.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maximApi.taskapi.model.TaskRepeat;

public interface TaskRepeatRepository extends JpaRepository<TaskRepeat, Long>{

	List<TaskRepeat> findByTaskId(Long taskId);
	
	List<TaskRepeat> findByTaskIdAndTaskRepeatDatetime(Long taskId, LocalDateTime taskRepeatDatetime);
	
	@Query(value="select tr from  TaskRepeat tr inner join Task t on tr.task.id = t.id inner join AppUser a on tr.appuser.id = a.id WHERE tr.task.id = :taskId AND tr.taskRepeatDatetime = :ldt And a.id = :userId")
	TaskRepeat findByTaskIdAndTaskRepeatDatetimeAndAppUserId(@Param("taskId") Long taskId,@Param("ldt") LocalDateTime ldt, @Param("userId") Long userId);
	
	@Query(value="select tr from  TaskRepeat tr inner join Task t on tr.task.id = t.id WHERE tr.task.id = :taskId AND tr.taskRepeatDatetime > :ldt")
	List<TaskRepeat> findByTaskIdAndCurrentTime(@Param("taskId") Long taskId, @Param("ldt") LocalDateTime ldt);
	
	@Query(value = "SELECT  tr.taskRepeatWeekDay, tr.assignType,  tr.appuser.id FROM TaskRepeat tr inner join Task t on tr.task.id = t.id WHERE tr.task.id = :taskId AND tr.taskRepeatDatetime > :ldt")
	List<Object[]> findTaskRepeatWeekDayUserByTaskId(@Param("taskId") Long taskId, @Param("ldt") LocalDateTime ldt);
}
