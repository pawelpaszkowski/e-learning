package pl.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.model.CourseGrade;
import pl.java.model.Subject;

public interface CourseGradeRepository extends JpaRepository<CourseGrade, Long>{

	List<CourseGrade> findAllByUseridAndSubject(Long userid, Subject subject);
	
	List<CourseGrade> findAllBySubject(Subject subject);
}
