package pl.java.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.CourseGrade;
import pl.java.model.Subject;
import pl.java.repository.CourseGradeRepository;

@Service
public class CourseGradeService {
	private CourseGradeRepository courseGradeRepository;

	@Autowired
	public void setCourseGradeRepository(CourseGradeRepository courseGradeRepository) {
		this.courseGradeRepository = courseGradeRepository;
	}

	public void addNewCourseGrade(CourseGrade courseGrade) {
		courseGradeRepository.save(courseGrade);
	}

	public List<CourseGrade> findIfYouRatedCourse(Long userid, Subject subject) {// :D
		return courseGradeRepository.findAllByUseridAndSubject(userid, subject);
	}

	public String findAverageGradeInCourse(Subject subject) {
		List<CourseGrade> grades = courseGradeRepository.findAllBySubject(subject);
		Double average = 0.0;
		if (!grades.isEmpty()) {
			for (CourseGrade grade : grades) {
				average += grade.getGrade();
			}
			average = average / grades.size();
			average = round(average, 2);
		}
		else
			return "Brak ocen";

		return average.toString();
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
