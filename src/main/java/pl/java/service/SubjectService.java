package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.Lesson;
import pl.java.model.Subject;
import pl.java.repository.SubjectRepository;

@Service
public class SubjectService {

	 private SubjectRepository subjectRepository;
	 
	 @Autowired
	 public void setSubjectRepository(SubjectRepository subjectRepository) {
		 this.subjectRepository = subjectRepository;
	 }
	 
	 public void addNewSubject(Subject subject) {
		 subjectRepository.save(subject);
	 }
	 
	 public List<Subject> findAllSubjects(){
		 return subjectRepository.findAll(); 
	 }
	 
	 public Subject findSubjectByID(Long idSubject) {
		 return subjectRepository.getOne(idSubject);
	 }
	 
	 public void deleteSubjectByID(Long idSubject) {
		 subjectRepository.delete(idSubject);
	 } 
	 
	 
}
