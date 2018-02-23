package pl.java.util;

import org.springframework.beans.factory.annotation.Autowired;

import pl.java.model.Subject;
import pl.java.service.CourseGradeService;

public class Utilities {
	
	private CourseGradeService courseGradeService;
	
	@Autowired
	public void setCourseGradeService(CourseGradeService courseGradeService) {
		this.courseGradeService = courseGradeService;
	}
	
	public String findAverageGradeForCourse(Subject subject) {
		return courseGradeService.findAverageGradeInCourse(subject);
	}
}
