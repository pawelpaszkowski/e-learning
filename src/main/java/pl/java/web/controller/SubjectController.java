package pl.java.web.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.java.model.Access;
import pl.java.model.Lesson;
import pl.java.model.Subject;
import pl.java.model.User;
import pl.java.model.UserRole;
import pl.java.service.AccessService;
import pl.java.service.CourseGradeService;
import pl.java.service.LessonService;
import pl.java.service.SubjectService;
import pl.java.service.UserService;
import pl.java.util.Utilities;

@Controller
public class SubjectController {
	
	private SubjectService subjectService;
	private UserService userService;
	private AccessService accessService;
	//private LessonService lessonService;
	private CourseGradeService courseGradeService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	@Autowired
	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}
	@Autowired
	public void setCourseGradeService(CourseGradeService courseGradeService) {
		this.courseGradeService = courseGradeService;
	}
	
	@GetMapping("/user/addSubject")
	public String addNewSubject(Model model) {
		model.addAttribute("subject", new Subject());
		return "user/addNewSubject";
	}
	
	@PostMapping("/user/addSubject")
    public String addUser(@ModelAttribute @Valid Subject subject,
            BindingResult bindResult, Principal principal, Model model) {
        if(bindResult.hasErrors())
            return "user/addNewSubject";
        else {
            subjectService.addNewSubject(subject);
            Access access = new Access(userService.findUserByEmail(principal.getName()).getId(), 202L, subject.getId());
            accessService.addNewAccess(access);
            
            User user = userService.findUserByEmail(principal.getName());
            String role = "";
    		Set <UserRole> userRoles = user.getRoles();
    		for(UserRole userRole : userRoles) {
    			role = userRole.getRole();
    		}
    		model.addAttribute("role", role);
            return "user/courses";
        }
    }
	
	@GetMapping("/user/allSubjects")
	public String showAllSubjects(Model model) {
		List<Subject> allSubjects = subjectService.findAllSubjects();
		model.addAttribute("courseGradeService", courseGradeService);
		model.addAttribute("allSubjects", allSubjects);
		model.addAttribute("chosenSubject", new Subject());
		return "user/allSubjects";
	}
	
	@GetMapping("/user/manageUsers")
	public String manageUsers(Model model) {
		List <User> allUsers = userService.findAllUser();
		model.addAttribute("allUsers", allUsers);
		
		return "user/manageUsers";
	}
	
	@GetMapping("/user/deleteUser")
    public String removeUser(@RequestParam(defaultValue="1") String idUser, Model model) {
		userService.deleteUserByID(Long.parseLong(idUser, 10));
		List <User> allUsers = userService.findAllUser();
		model.addAttribute("allUsers", allUsers);
		return "user/manageUsers";
    }
	
	
	
	@GetMapping("/user/deleteSubjects")
    public String displaySubject(Model model) {
		List<Subject> allSubjects = subjectService.findAllSubjects();
		model.addAttribute("allSubjects", allSubjects);
		return "user/deleteSubjects";
    }
	
	@PostMapping("/user/deleteSubjects")
    public String removeSubject(@RequestParam(defaultValue="1") String idSubject, Model model) {
		subjectService.deleteSubjectByID(Long.parseLong(idSubject, 10));
		List<Subject> allSubjects = subjectService.findAllSubjects();
		model.addAttribute("allSubjects", allSubjects);
		return "user/deleteSubjects";
    }
	
	
//	@GetMapping("/user/subject")
//	public String showLessonInSubject(@RequestParam(defaultValue="id") String idSubject, Model model) {
//		//List<Lesson> allLessons = lessonService.findAllLessons();
//		//model.addAttribute("allLessons", allLessons);
//		model.addAttribute("idSubject", idSubject);
//		return "user/subject";
//	}
	
//	@GetMapping("/user/subject")
//	public String showLessonInSubject(Model model) {
//		//List<Lesson> allLessons = lessonService.findAllLessons();
//		//model.addAttribute("allLessons", allLessons);
//		return "user/subject";
//	}
}
