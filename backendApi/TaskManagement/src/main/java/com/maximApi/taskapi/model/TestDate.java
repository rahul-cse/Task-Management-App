package com.maximApi.taskapi.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;






@Entity
@Table(name="TESTDATE")
public class TestDate {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_date_seq")
	@SequenceGenerator(name = "test_date_seq", allocationSize = 1)
	Long id;
	@Column(name="localdate")
	LocalDateTime localDate;
	@Column(name="DATA_")
	String data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public LocalDateTime getLocalDate() {
		return localDate;
	}
	
	public void setLocalDate(LocalDateTime localDate) {
		this.localDate = localDate;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}




	
	
	
}






//System.out.println(task.getTitle()+",start-date:"+task.getStartDate()+",end-date:"+task.getEndDate());
//LocalDateTime ldt = LocalDateTime.now();
//if(ldt.getHour()!=task.getStartDate().getHour()) {
//	task.setStartDate(task.getStartDate().plusHours(6));
//}
//System.out.println(task.getTitle()+",new-start-date:"+task.getStartDate()+",end-date:"+task.getEndDate());
//return null;
