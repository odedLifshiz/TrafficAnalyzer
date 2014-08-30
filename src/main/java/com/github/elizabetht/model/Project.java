package com.github.elizabetht.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.github.elizabetht.service.CaptureService;




@Entity
@Table(name = "projects")

public class Project {

	@Id
	@GeneratedValue
	private Integer project_id;

	private String name;

	private String creator;
	
	private Date creationDate;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "project_capture",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "capture_id")
    )
	private Set<Capture> captures = new HashSet<Capture>();
	
	
	@PrePersist
    void createdAt() {
    	setCreationDate();
    }
	  
	private void setCreationDate() {
		this.creationDate = new Date();
	}

	public Integer getId() {
		return project_id;
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
		this.project_id=id;		
	}
	
	
	public Set<Capture> getCaptures() {
		return captures;
	}

	public void setCaptures(Set<Capture> captures) {
		this.captures = captures;
	}

}
