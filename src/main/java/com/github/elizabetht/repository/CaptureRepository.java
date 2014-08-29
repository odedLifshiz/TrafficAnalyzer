package com.github.elizabetht.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.elizabetht.model.Capture;




public interface CaptureRepository extends JpaRepository<Capture, Integer> {
 
}