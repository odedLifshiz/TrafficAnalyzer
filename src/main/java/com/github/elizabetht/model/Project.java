package com.github.elizabetht.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;




@Entity
@Table(name = "projects")

public class Project {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String creator;
	
	private Date creationDate;
	
//	@OneToMany(cascade=CascadeType.ALL)
//	@JoinColumn(name="project_id")
//	private Set<Capture> captures = new HashSet<Capture>();
	
	@PrePersist
    void createdAt() {
    	setCreationDate();
    }
	  
	private void setCreationDate() {
		this.creationDate = new Date();
	}

	public Integer getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreationDate(){
		return this.creationDate;
	}

	public void setId(Integer id) {
		this.id=id;		
	}
	
	
//	public Set<Capture> getCaptures() {
//		return captures;
//	}
//
//	public void setCaptures(Set<Capture> captures) {
//		this.captures = captures;
//	}

}
