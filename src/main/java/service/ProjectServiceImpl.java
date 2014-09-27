package service;

import java.util.List;

import model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.*;


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
	public void updateProject(Project project)  {
		projectRepository.save(project);
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
