package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.Question;
import pl.java.model.Quiz;
import pl.java.model.Subject;
import pl.java.repository.QuestionRepository;
import pl.java.repository.QuizRepository;

@Service
public class QuizService {
	 
 	private QuizRepository quizRepository;
 	private QuestionRepository questionRepository;
 
	 @Autowired
	 public void setQuizRepository(QuizRepository quizRepository) {
		 this.quizRepository = quizRepository;
	 }
	 
	 @Autowired
	 public void setQestionRepository(QuestionRepository questionRepository) {
		 this.questionRepository = questionRepository;
	 }
	 
	 
	 public void addNewQuiz(Quiz quiz) {
		 quizRepository.save(quiz);
	 }
	 
	 public List <Quiz> findBySubjectid(Subject subject){
		 return this.quizRepository.findBySubject(subject);
	 }

	public Quiz findQuizByID(long idQuiz) {
		return quizRepository.getOne(idQuiz);
	}

	public void addNewQuestion(Question question) {
		questionRepository.save(question);
		
	}

	 
//	 public List<Quiz> findAllQuizesForSubject(Long idSubject){
//		 
//		 
//		 
//		 
////		 return quizRepository.findQuizesForCourse(idSubject); 
//	 }
	 
	
}
