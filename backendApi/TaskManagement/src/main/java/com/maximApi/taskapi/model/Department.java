package com.maximApi.taskapi.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Department {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sq")
    @SequenceGenerator(sequenceName = "department_sq", allocationSize = 1, name = "department_sq")
    @Column(name="id")
    private long id ;
    @NotNull
    @Column
    @Size(min=3,max=100)
    private String  name;
    @Column
    private long area_id;
    @Column
    private Long  incharge;
    @Column
    private int status=0;

    @OneToMany(mappedBy = "dept")
    private List<TEAMS> teams;
    
    //@OneToOne(mappedBy="department")
    //private Task task;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getArea_id() {
        return area_id;
    }

    public void setArea_id(long area_id) {
        this.area_id = area_id;
    }


    public Long getIncharge() {
		return incharge;
	}

	public void setIncharge(Long incharge) {
		this.incharge = incharge;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area_id=" + area_id +
                ", incharge=" + incharge +
                ", status=" + status +
                '}';
    }

}
