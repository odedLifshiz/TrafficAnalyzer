package com.github.elizabetht.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "captures")

public class Capture {

	@Id
	@GeneratedValue
	private Integer capture_id;
	
	private String name;
	private String path;

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
	
	@Override
	public String toString(){
		return "capture_id: " + capture_id + " name: " + name + " path: " + path;
	}
	
	
	
}
