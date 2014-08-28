package com.github.elizabetht.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.elizabetht.model.Project;

@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Query("select s from Project s where s.name = :name")
	Project findByName(@Param("name") String name);
	
}
