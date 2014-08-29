package com.github.elizabetht.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "captures")

public class Capture {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String path;
	
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Project getProject(){
		return this.project;
	}
	
	public void setProject(Project project){
		this.project = project;
	}

	
	
}
