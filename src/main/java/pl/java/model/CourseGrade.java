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
@Table(name="coursegrade")
public class CourseGrade {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idcoursegrade")
    private Long idcoursegrade;
	
	@Column(name="userid")
	private Long userid;
	
	
	@Column(name="grade")
	private Integer grade;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
	private Subject subject;

	public Long getIdcoursegrade() {
		return idcoursegrade;
	}

	public void setIdcoursegrade(Long idcoursegrade) {
		this.idcoursegrade = idcoursegrade;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public CourseGrade() {}
	
	public CourseGrade(Long userid, int grade, Subject subject) {
		super();
		this.userid = userid;
		this.grade = grade;
		this.subject = subject;
	}
	
	
}