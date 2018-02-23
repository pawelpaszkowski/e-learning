package pl.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.java.model.Lesson;
import pl.java.model.Subject;

public interface LessonRepository extends JpaRepository<Lesson, Long>{

	public List<Lesson> findBySubject(Subject subject);

	@Modifying
	@Transactional
	@Query("delete from Lesson l where l.idLesson = ?1")
	public void deleteByIdLesson(Long idLesson);
}
