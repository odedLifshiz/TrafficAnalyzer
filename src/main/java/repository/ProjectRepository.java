package repository;

import model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Query("select s from Project s where s.name = :name")
	Project findByName(@Param("name") String name);
	
}
