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

@Entity
@Table(name="studentgrade")
public class StudentGrade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idstudentgrade")
    private Long idstudentgrade;
	
	@Column(name="userid")
	private Long userid;
	
	@Column(name="grade")
	private Double grade;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
	private Subject subject;
	
	@Column(name="comment")
	private String comment;

	public Long getIdstudentgrade() {
		return idstudentgrade;
	}


	public void setIdstudentgrade(Long idstudentgrade) {
		this.idstudentgrade = idstudentgrade;
	}


	public Long getUserid() {
		return userid;
	}


	public void setUserid(Long userid) {
		this.userid = userid;
	}


	public Double getGrade() {
		return grade;
	}


	public void setGrade(Double grade) {
		this.grade = grade;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public StudentGrade() {}


	public StudentGrade(Long idstudentgrade, Long userid, Double grade, Subject subject) {
		super();
		this.userid = userid;
		this.grade = grade;
		this.subject = subject;
	}

	

}
