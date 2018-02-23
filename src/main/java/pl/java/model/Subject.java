package pl.java.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="subject")
public class Subject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_subject")
    private Long idSubject;
	@NotEmpty
	private String name;
	@NotEmpty
	@Column(length = 1000)
	@Type(type="text")
	private String description;
	
	//@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Lesson> lessons;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<Quiz> quizes;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<CourseGrade> grades;
	
	public Subject() {}
	
	public Subject(Long id, String name, String description) {
		super();
		this.idSubject = id;
		this.name = name;
		this.description = description;
	}
	
	public Long getId() {
		return idSubject;
	}
	public void setId(Long id) {
		this.idSubject = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Lesson> getLessons() {
        return lessons;
    }
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    
    public List<Quiz> getQuizes() {
        return quizes;
    }
    public void setQuizes(List<Quiz> quizes) {
        this.quizes = quizes;
    }
    
    
	
	public List<CourseGrade> getGrades() {
		return grades;
	}

	public void setGrades(List<CourseGrade> grades) {
		this.grades = grades;
	}

	@Override
	public String toString() {
		return "Subject [id=" + idSubject + ", name=" + name + ", description=" + description + ", lessons=" + lessons + ", quizes=" + quizes + "]";
	}
	
}
