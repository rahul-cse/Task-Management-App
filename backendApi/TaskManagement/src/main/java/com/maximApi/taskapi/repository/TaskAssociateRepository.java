package com.maximApi.taskapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maximApi.taskapi.model.TaskAssociate;

public interface TaskAssociateRepository extends JpaRepository<TaskAssociate, Long>{
	
	List<TaskAssociate> findByTaskId(Long taskId);
	
	@Query("Select ta.appUser.id from TaskAssociate ta inner join Task t on ta.task.id = t.id where ta.task.id = :taskId and ta.assignType ='other'")
	List<Long> findTaskAssociateByTaskId(@Param("taskId") Long taskId);
}
