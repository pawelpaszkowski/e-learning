package pl.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.model.StudentGrade;
import pl.java.model.Subject;

public interface StudentGradeRepository extends JpaRepository <StudentGrade, Long> {

	List<StudentGrade> findAllGradesByUseridAndSubject(Long userid, Subject subject);
}
