package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.StudentGrade;
import pl.java.model.Subject;
import pl.java.repository.StudentGradeRepository;

@Service
public class StudentGradeService {
	private StudentGradeRepository studentGradeRepository;

	@Autowired
	public void setStudentGradeRepository(StudentGradeRepository studentGradeRepository) {
		this.studentGradeRepository = studentGradeRepository;
	}
	
	public void addNewStudentGrade(StudentGrade studentGrade) {
		studentGradeRepository.save(studentGrade);
	}
	
	public List<StudentGrade> findStudentGrades(Long userid, Subject subject) {
		return studentGradeRepository.findAllGradesByUseridAndSubject(userid, subject);
	}
	
}
