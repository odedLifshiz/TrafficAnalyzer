package com.github.elizabetht.model;


import javax.persistence.Column;
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
	//@Column(name = "capture_id")
	private Integer capture_id;

	private String name;

	private String path;
	
	//@ManyToOne
	//@JoinColumn(name = "project_id")
		
	public Integer getId() {
		return capture_id;
	}

	public void setId(Integer id) {
		this.capture_id = id;
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
	

	
	
	
}
