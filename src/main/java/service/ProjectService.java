package service;

import java.util.List;

import model.Project;



public interface ProjectService {
	
	public Project create(Project project);
	public void delete(int id);
	public List<Project> findAll();
	public Project findById(int id);
	public Project findByName(String name);
	public void updateProject(Project project);
}
