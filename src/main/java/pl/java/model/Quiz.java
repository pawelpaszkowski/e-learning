package pl.java.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "quiz")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_quiz")
	private Long idQuiz;
	
	@NotEmpty
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
	private Subject subject;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<Question> questions;
	
	public Long getIdQuiz() {
		return idQuiz;
	}

	public void setIdQuiz(Long idQuiz) {
		this.idQuiz = idQuiz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Quiz() {}

	public Quiz(Long idQuiz, String name, Subject subject) {
		super();
		this.idQuiz = idQuiz;
		this.name = name;
		this.subject = subject;
	}
	
	
	

}
