package com.github.elizabetht.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.elizabetht.model.Capture;
import com.github.elizabetht.repository.CaptureRepository;




@Service
public class CaptureServiceImpl implements CaptureService {
	 
	@Resource
	private CaptureRepository captureRepository;

	@Transactional
	public Capture create(Capture capture) {
		Capture createdCapture = capture;
		return captureRepository.save(createdCapture);
	}
	
	@Transactional
	public Capture findById(int id) {
		return captureRepository.findOne(id);
	}

	@Transactional
	public Capture delete(int id) {
		Capture deletedCapture = captureRepository.findOne(id);
			
		captureRepository.delete(deletedCapture);
		return deletedCapture;
	}

	@Transactional
	public List<Capture> findAll() {
		return captureRepository.findAll();
	}

	@Transactional
	public Capture update(Capture capture) {
		Capture updatedCapture = captureRepository.findOne(capture.getId());
		
	
		updatedCapture.setName(capture.getName());
		return updatedCapture;
	}


	


}
