package service;

import java.util.List;

import model.Capture;


public interface CaptureService {
	
	public Capture create(Capture project);
	public Capture delete(int id)  ;
	public List<Capture> findAll();
	public Capture update(Capture project)  ;
	public Capture findById(int id);
	//public List findByProjectId(int project_id);
}
