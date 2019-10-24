package com.maximApi.taskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maximApi.taskapi.model.TestDate;

public interface TestDateRepository extends JpaRepository<TestDate, Long>{

}
