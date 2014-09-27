package repository;


import model.Capture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface CaptureRepository extends JpaRepository<Capture, Integer> {
}