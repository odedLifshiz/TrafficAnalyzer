package com.github.elizabetht.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.elizabetht.model.Project;
import com.github.elizabetht.repository.*;


@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Transactional
	public Project create(Project project) {
		return projectRepository.save(project);
	}
	
	@Transactional
	public void delete(int id) {
		projectRepository.delete(id);
	}
	
	@Transactional
	public List<Project> findAll() {
		return projectRepository.findAll();
	}
	
	@Transactional
	public Project updateName(Project project)  {
		Project updatedProject = projectRepository.findOne(project.getId());
		
		if (updatedProject == null) {
			return null;
		}
		updatedProject.setName(project.getName());
		return updatedProject;
	}
	
	@Transactional
	public Project findById(int id) {
		return projectRepository.findOne(id);
	}
	
	@Transactional
	public Project findByName(String name) {
		return projectRepository.findByName(name);
	}
	
}
