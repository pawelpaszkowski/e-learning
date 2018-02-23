package pl.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="lesson")
public class Lesson {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_lesson")
    private Long idLesson;
	@NotEmpty
	private String name;
	@NotEmpty
	@Column(length = 50000)
	@Type(type="text")
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
	private Subject subject;
	
	public Long getIdLesson() {
		return idLesson;
	}
	public void setIdLesson(Long idLesson) {
		this.idLesson = idLesson;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Lesson() {}
	
	public Lesson(Long idLesson, String name, String content, Subject subject) {
		super();
		this.idLesson = idLesson;
		this.name = name;
		this.content = content;
		this.subject = subject;
	}
	
}
