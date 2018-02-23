package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.Lesson;
import pl.java.model.Subject;
import pl.java.repository.LessonRepository;

@Service
public class LessonService {

	 private LessonRepository lessonRepository;
	 
	 @Autowired
	 public void setLessonRepository(LessonRepository lessonRepository) {
		 this.lessonRepository = lessonRepository;
	 }
	 
	 public void addNewLessons(Lesson lesson) {
		 lessonRepository.save(lesson);
	 }
	 
	 public List<Lesson> findAllLessons(){
		 return lessonRepository.findAll();
	 }
	 
	 public Lesson findLessonByID(Long idLesson) {
		 return lessonRepository.getOne(idLesson);
	 }
	 public List<Lesson> findLessonBySubjectId(Subject subject) {
		 return lessonRepository.findBySubject(subject);
	 }
	 
	 public void deleteLessonByID(Long idLesson) {
		 lessonRepository.deleteByIdLesson(idLesson);
	 } 
}
