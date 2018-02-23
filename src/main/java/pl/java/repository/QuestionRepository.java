package pl.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.model.Question;

public interface QuestionRepository  extends JpaRepository<Question, Long>{

}
