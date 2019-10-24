package com.maximApi.taskapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class TEAMS {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_SQ")
	    @SequenceGenerator(sequenceName = "TEAMS_SQ", allocationSize = 1, name = "TEAMS_SQ")
	    private long id ;
	    @NotNull
	    @Column
	    @Size(min=3,max=100)
	    private String  name;
	    @Column
	    private long area_id;
	    @Column
	    private long  incharge;
	    @Column
	    private int status=0;

	    //@Column
	    @ManyToOne()
	    @JoinColumn(name = "dept_id", nullable = false)
	    private Department dept;


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

	    public long getIncharge() {
	        return incharge;
	    }

	    public void setIncharge(long incharge) {
	        this.incharge = incharge;
	    }

	    public int getStatus() {
	        return status;
	    }

	    public void setStatus(int status) {
	        this.status = status;
	    }

	    
	    
		public Department getDept() {
			return dept;
		}

		public void setDept(Department dept) {
			this.dept = dept;
		}

		@Override
		public String toString() {
			return "TEAMS [id=" + id + ", name=" + name + ", area_id=" + area_id + ", incharge=" + incharge
					+ ", status=" + status + ", dept=" + dept + "]";
		}

	   

}
