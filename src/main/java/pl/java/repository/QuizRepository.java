package pl.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.java.model.Lesson;
import pl.java.model.Quiz;
import pl.java.model.Subject;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

	// public List<Quiz> findBySubject(Subject subject);

	 

	public List<Quiz> findBySubject(Subject subject);

}
