package pl.java.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import pl.java.model.CourseGrade;
import pl.java.model.Lesson;
import pl.java.model.Message;
import pl.java.model.Question;
import pl.java.model.Quiz;
import pl.java.model.RequestAccess;
import pl.java.model.StudentGrade;
import pl.java.model.Subject;
import pl.java.model.User;
import pl.java.model.UserRole;
import pl.java.service.AccessService;
import pl.java.service.CourseGradeService;
import pl.java.service.LessonService;
import pl.java.service.MessageService;
import pl.java.service.QuizService;
import pl.java.service.RequestAccessService;
import pl.java.service.StudentGradeService;
import pl.java.service.SubjectService;
import pl.java.service.UserService;

@Controller
public class LessonController {

	private String currentIdSubject;
	private Subject currentSubject;
	private UserRole currentAccessRole;
	private Quiz currentQuiz;
	private int questionNr = 0;
	private List<String> answers = new ArrayList<>();
	private String studentEmail;
	
	private UserService userService;
	private SubjectService subjectService;
	private LessonService lessonService;
	private AccessService accessService;
	private QuizService quizService;
	private RequestAccessService requestAccessService;
	private CourseGradeService courseGradeService;
	private MessageService messageService;
	private StudentGradeService studentGradeService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	@Autowired
	public void setLessonService(LessonService lessonService) {
		this.lessonService = lessonService;
	}
	@Autowired
	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}
	@Autowired
	public void setRequestAccessService(RequestAccessService requestAccessService) {
		this.requestAccessService = requestAccessService;
	}
	@Autowired
	public void setQuizService(QuizService quizService) {
		this.quizService = quizService;
	}
	@Autowired
	public void setCourseGradeService(CourseGradeService courseGradeService) {
		this.courseGradeService = courseGradeService;
	}
	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	@Autowired
	public void setStudentGradeService(StudentGradeService studentGradeService) {
		this.studentGradeService = studentGradeService;
	}
	@GetMapping("/user/promoteToTeacher")
    public String promoteUserToTeacher(@RequestParam(defaultValue="1") String idUser, Model model) {
		User user = userService.findUserById(Long.parseLong(idUser, 10));
		userService.deleteUserByID(Long.parseLong(idUser, 10));
		user.getRoles().clear();
//		Set<UserRole> userRole = new TreeSet<>();
//		userRole.add(userService.findRoleById(202L));
//		user.setRoles(userRole);
		userService.addWithTeacherRole(user);
		List <User> allUsers = userService.findAllUser();
		model.addAttribute("allUsers", allUsers);
		return "user/home";
    }
	
	@GetMapping("/user/allLessons")
	public String showLessonsInSubject(@RequestParam(defaultValue="1") String idSubject, Model model, Principal principal) {
		//DODANIE ZNALEZIENIA ROLI UZYTKOWNIKA W DANYM PRZEDMIOCIE
		currentIdSubject = idSubject;
		User user =userService.findUserByEmail(principal.getName());
		List<Access> access = accessService.findAccess(user.getId(), Long.parseLong(currentIdSubject, 10));
		if(!access.isEmpty()) {
			
			currentAccessRole = userService.findRoleById(access.get(0).getRoleid());
			//System.out.println(currentAccessRole.getRole());
		} else {
			currentAccessRole = userService.findRoleById(201L);
			//System.out.println(currentAccessRole.getRole());
		}
//			quizService.findAllQuizesForSubject(Long.parseLong(currentIdSubject, 10));	
			
			
		model.addAttribute("idSubject", currentIdSubject);
		model.addAttribute("currentAccessRole", currentAccessRole.getRole());
		currentSubject = subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10));
		model.addAttribute("currentSubject", currentSubject);
		return "user/allLessons";
	}
	
	@GetMapping("/user/userRequestToCourse")
	public String userRequestToCourse(@RequestParam(defaultValue="1") String idSubject, Model model, Principal principal) {
		User user =userService.findUserByEmail(principal.getName());
		//List<Access> accesses = accessService.findAccess(user.getId(), Long.parseLong(idSubject, 10));
		List<RequestAccess> requestedAccesses = requestAccessService.findRequestAccess(user.getId(), Long.parseLong(idSubject, 10));
		if(requestedAccesses.isEmpty()) { //oznacza że rola to ROLE_USER - zarejestrowany uzytkownik bez specjalnych praw
			RequestAccess access = new RequestAccess(user.getId(),204L,Long.parseLong(idSubject, 10));
			requestAccessService.addNewAccess(access);
			//dodanie do access(userid,roleid=204Taa,subjectid)
		} else {
			System.out.println("ROLA Z WYZSZYM DOSTEPEM");
		}
		return "user/home";
	}
	
	
	//TO JESZCZE W PANELU NAUCZYCIELA!
	@GetMapping("/user/addUserToCourse")
	public String addUserToCourse(@RequestParam(defaultValue="1") String idUser, Model model, Principal principal) {
		User user =userService.findUserById(Long.parseLong(idUser, 10));
		List<Access> accesses = accessService.findAccess(user.getId(), currentSubject.getId());
		if(accesses.isEmpty()) { //oznacza że rola to ROLE_USER - zarejestrowany uzytkownik bez specjalnych praw
			Access access = new Access(user.getId(),204L,currentSubject.getId());
			accessService.addNewAccess(access);
			//dodanie do access(userid,roleid=204Taa,subjectid)
		} else {
			System.out.println("ROLA Z WYZSZYM DOSTEPEM");
		}
		return "user/home";
	}
	
	@GetMapping("/user/addNewLesson")
	public String addLesson(Model model) {
		System.out.println(currentIdSubject);
		model.addAttribute("lesson", new Lesson());
		 model.addAttribute("idSubject", currentIdSubject);
		return "user/addNewLesson";
	}
	
	@PostMapping("/user/addNewLesson")
    public String addLesson(
    		@ModelAttribute @Valid Lesson lesson, Model model,
            BindingResult bindResult, Principal principal) {
        if(bindResult.hasErrors())
            return "user/addNewLesson";
        else {
        	lesson.setSubject(currentSubject);
        	model.addAttribute("currentSubject", subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10)));
            lessonService.addNewLessons(lesson);
            model.addAttribute("idSubject", currentIdSubject);
            //DODANE TESTOWO!
            User user =userService.findUserByEmail(principal.getName());
    		List<Access> access = accessService.findAccess(user.getId(), Long.parseLong(currentIdSubject, 10));
    		if(!access.isEmpty()) {
    			
    			currentAccessRole = userService.findRoleById(access.get(0).getRoleid());
    			//System.out.println(currentAccessRole.getRole());
    		} else {
    			currentAccessRole = userService.findRoleById(201L);
    			//System.out.println(currentAccessRole.getRole());
    		}
//    			quizService.findAllQuizesForSubject(Long.parseLong(currentIdSubject, 10));	
    			
    			
    		model.addAttribute("idSubject", currentIdSubject);
    		model.addAttribute("currentAccessRole", currentAccessRole.getRole());
    		currentSubject = subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10));
    		model.addAttribute("currentSubject", currentSubject);
            return "user/allLessons";
        }
    }
	@GetMapping("/user/addNewQuiz")
	public String addQiuz(Model model) {
//		System.out.println(currentIdSubject);
		model.addAttribute("quiz", new Quiz());
		model.addAttribute("idSubject", currentIdSubject);
		return "user/addNewQuiz";
	}
	
	@PostMapping("/user/addNewQuiz")
    public String addQuiz(@ModelAttribute Quiz quiz, Model model, BindingResult bindResult, Principal principal) {
		if(bindResult.hasErrors() || quiz.getName().equals("")) {		
            return "user/addNewQuiz";
		}
        else {
        System.out.println("sdd2");
		quiz.setSubject(currentSubject);
		model.addAttribute("currentSubject", subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10)));
		quizService.addNewQuiz(quiz);
		User user =userService.findUserByEmail(principal.getName());
		List<Access> access = accessService.findAccess(user.getId(), Long.parseLong(currentIdSubject, 10));
		if(!access.isEmpty()) {
			
			currentAccessRole = userService.findRoleById(access.get(0).getRoleid());
			//System.out.println(currentAccessRole.getRole());
		} else {
			currentAccessRole = userService.findRoleById(201L);
			//System.out.println(currentAccessRole.getRole());
		}
//			quizService.findAllQuizesForSubject(Long.parseLong(currentIdSubject, 10));	
			
			
		model.addAttribute("idSubject", currentIdSubject);
		model.addAttribute("currentAccessRole", currentAccessRole.getRole());
		currentSubject = subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10));
		model.addAttribute("currentSubject", currentSubject);
		return "user/allLessons";
		}
    }
	
	@GetMapping("/user/lesson")
	public String showLesson(@RequestParam(defaultValue="1") String idLesson, Model model) {
		model.addAttribute("currentLesson", lessonService.findLessonByID(Long.parseLong(idLesson, 10)));
		model.addAttribute("idSubject", currentIdSubject);
		return "user/lesson";
	}
	@GetMapping("/user/quiz")
	public String showQuiz(@RequestParam(defaultValue="1") String idQuiz, Model model) {
		currentQuiz = quizService.findQuizByID(Long.parseLong(idQuiz, 10));
		model.addAttribute("currentQuiz", currentQuiz);
		model.addAttribute("idSubject", currentIdSubject);
		model.addAttribute("currentAccessRole", currentAccessRole.getRole());
		return "user/quiz";
	}
	
	@GetMapping("/user/usersOfCourse")
	public String manageUsersInCourse(Model model) {
		
		
		//ZNAJDZ USER_ID WSZYSTKICH Z ROLA STUDENTA I Z ID PRZEDMIOTU currentIdSubject
				//ZNAJDZ WSZYSTKICH UZYTKOWNIKOW O PODANYCH ID!
				List<Access> accesses = accessService.findAccessWithUsers(204L, Long.parseLong(currentIdSubject, 10));
				List<User> allStudentsInCourse = new LinkedList<>();
				for(Access access : accesses) {
					//METODA
					allStudentsInCourse.add(userService.findUserById(access.getUserid()));
				}
				model.addAttribute("allUsers", allStudentsInCourse );
				model.addAttribute("idSubject", currentIdSubject);
				//model.addAttribute("allUsers", allUsers);
		return "user/usersOfCourse";
	}
	
	@GetMapping("/user/addUsersToCourse")
	public String addUsersToCourse(Model model) {
		
		
		//ZNAJDZ USER_ID WSZYSTKICH Z ROLA STUDENTA I Z ID PRZEDMIOTU currentIdSubject
				//ZNAJDZ WSZYSTKICH UZYTKOWNIKOW O PODANYCH ID!
				List<RequestAccess> accesses = requestAccessService.findAccessWithUsers(204L, Long.parseLong(currentIdSubject, 10));
				List<User> allStudentsJoiningCourse = new LinkedList<>();
				for(RequestAccess access : accesses) {
					//METODA
					allStudentsJoiningCourse.add(userService.findUserById(access.getUserid()));
				}
				model.addAttribute("allUsers", allStudentsJoiningCourse );
				model.addAttribute("idSubject", currentIdSubject);
				//model.addAttribute("allUsers", allUsers);
		return "user/addUsersToCourse";
	}
	
	@GetMapping("/user/statistics")
	public String statistics(Model model, Principal principal) {
		//LICZBA LEKCJI W KURSIE
		//LICZBA QUIZOW W KURSIE
		//LICZBA UCZNIOW W KURSIE
		List<Access> accesses = accessService.findAccessWithUsers(204L, Long.parseLong(currentIdSubject, 10));
		model.addAttribute("numberOfLessons", currentSubject.getLessons().size());
		model.addAttribute("numberOfQuizes", currentSubject.getQuizes().size());
		model.addAttribute("idSubject", currentIdSubject);
		model.addAttribute("numberOfStudents", accesses.size());
		return "user/statistics";
	}
	
	@GetMapping("/user/checkGrades")
	public String checkingGrades(Model model, Principal principal) {
		List <StudentGrade> allGrades = studentGradeService.findStudentGrades(userService.findUserByEmail(principal.getName()).getId(), currentSubject);
		model.addAttribute("allGrades", allGrades);
		model.addAttribute("currentSubject", currentSubject);
		model.addAttribute("idSubject", currentIdSubject);
		String info="";
		if (allGrades.isEmpty())
			info="Jeszcze nie dostałeś/aś żadnej oceny.";
		model.addAttribute("info", info);
		return "user/checkGrades";
	}
	
	
	@GetMapping("/user/insertGrade")
	public String insertingGrades(Model model, Principal principal) {
		List<Access> accesses = accessService.findAccessWithUsers(204L, Long.parseLong(currentIdSubject, 10));
		List<User> allStudentsInCourse = new LinkedList<>();
		for(Access access : accesses) {
			//METODA
			allStudentsInCourse.add(userService.findUserById(access.getUserid()));
		}

		model.addAttribute("studentGradeService", studentGradeService);
		model.addAttribute("currentSubject", currentSubject);
		model.addAttribute("allUsers", allStudentsInCourse );
		model.addAttribute("studentGrade", new StudentGrade());
		model.addAttribute("idSubject", currentIdSubject);
		
		return "user/insertGrade";
	}
	
	@PostMapping("/user/insertGrade")
	public String insertingGradesToDatabase(@RequestParam(defaultValue="1") String idUser, @ModelAttribute StudentGrade studentGrade, Model model, Principal principal, BindingResult bindResult) {
		List<Access> accesses = accessService.findAccessWithUsers(204L, Long.parseLong(currentIdSubject, 10));
		List<User> allStudentsInCourse = new LinkedList<>();
		for(Access access : accesses) {
			//METODA
			allStudentsInCourse.add(userService.findUserById(access.getUserid()));
		}
		model.addAttribute("studentGradeService", studentGradeService);
		model.addAttribute("currentSubject", currentSubject);
		model.addAttribute("allUsers", allStudentsInCourse );
		model.addAttribute("idSubject", currentIdSubject);
		model.addAttribute("studentGrade", new StudentGrade());
		if (studentGrade.getGrade()==null)
		    return "user/insertGrade";
		if(bindResult.hasErrors() || (studentGrade.getGrade()!=2 && studentGrade.getGrade()!=3 && studentGrade.getGrade()!=3.5 && studentGrade.getGrade()!=4 && studentGrade.getGrade()!=4.5 && studentGrade.getGrade()!=5)) {		
			System.out.println("blad");
            return "user/insertGradeError";
		}	
	
		studentGrade.setSubject(currentSubject);
		studentGrade.setUserid(Long.parseLong(idUser, 10));
		
		studentGradeService.addNewStudentGrade(studentGrade);
		System.out.println(userService.findUserByEmail(principal.getName()).getId());
		return "user/insertGrade";
	}
	

	@GetMapping("/user/receiveMessage")
	public String receiveMessage(Model model, Principal principal) {
		List<Message> allMessages=messageService.findAllMessages(principal.getName());
		model.addAttribute("allMessages", allMessages);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/receiveMessage";
	}
	
	@GetMapping("/user/displayMessage")
	public String displayMessage(@RequestParam(defaultValue="1") String idMessage, Model model, Principal principal) {
	    System.out.println(idMessage);
		model.addAttribute("idSubject", currentIdSubject);
		Message msg=messageService.findMessageById(Long.parseLong(idMessage, 10));
		model.addAttribute("message", msg);
		studentEmail = msg.getFrom();
		return "user/displayMessage";
	}
	
	@PostMapping("/user/goBack")
    public String goBack(Model model) {
        	model.addAttribute("currentSubject", subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10)));
            return "user/allLessons";
    }
	
	@GetMapping("/user/removeUserFromCourse")
    public String removeUser(@RequestParam(defaultValue="1") String idUser, Model model) {
		List<Access> accesses = accessService.findAccess(Long.parseLong(idUser, 10), Long.parseLong(currentIdSubject, 10));
		if(!accesses.isEmpty()) {
			accessService.removeUserFromSubject(accesses.get(0).getIdaccess());
		}
		
	//INACZEJ USUNIECIE Z KURSU NIE Z PLATFORMY
//		userService.deleteUserByID(Long.parseLong(idUser, 10));	
//		List <User> allUsers = userService.findAllUser();
//		model.addAttribute("allUsers", allUsers);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/usersOfCourse";
    }
	
	@GetMapping("/user/deleteLessons")
    public String displaySubject(Model model) {
		List<Lesson> allLessons = lessonService.findLessonBySubjectId(currentSubject);
		model.addAttribute("allLessons", allLessons);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/deleteLessons";
    }
	
	@PostMapping("/user/deleteLesson")
    public String deleteLesson(@RequestParam(defaultValue="1") String idLesson, Model model) {
		lessonService.deleteLessonByID(Long.parseLong(idLesson, 10));
		System.out.println(Long.parseLong(idLesson, 10));
		List<Lesson> allLessons = lessonService.findLessonBySubjectId(currentSubject);
		model.addAttribute("allLessons", allLessons);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/deleteLessons";
    }
	
	
	
	@GetMapping("/user/addNewQuestions")
	public String addQuestions(Model model) {
		model.addAttribute("question", new Question());
		 model.addAttribute("idSubject", currentIdSubject);
		return "user/addNewQuestions";
	}
	
	@PostMapping("/user/addNewQuestions")
	public String addQuestionToDataBase(@ModelAttribute Question question, Model model, BindingResult bindResult) {
		if(bindResult.hasErrors() || question.getQuest().equals("")) {		
            return "user/addNewQuestions";
		}
		System.out.println("sdd2");
		question.setQuiz(currentQuiz);
		//quiz.setSubject(currentSubject);
		model.addAttribute("currentSubject", subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10)));
		quizService.addNewQuestion(question);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/addNewQuestions";
	}
	
	@GetMapping("/user/takeAnQuiz")
	public String takeAnQuiz(@RequestParam(defaultValue="1") String idSubject, Model model, Principal principal) {
		//DODANIE ZNALEZIENIA ROLI UZYTKOWNIKA W DANYM PRZEDMIOCIE
		currentIdSubject = idSubject;
		
		model.addAttribute("currentQuiz", currentQuiz);
		
		//ZABEZPIECZYĆ TO BO MOŻE NIE BYĆ PYTAŃ
		model.addAttribute("question", currentQuiz.getQuestions().get(questionNr));
		model.addAttribute("idSubject", currentIdSubject);
		model.addAttribute("answers", new String());
		currentSubject = subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10));
		model.addAttribute("currentSubject", currentSubject);
		return "user/takeAnQuiz";
	}
	
	@PostMapping("/user/takeAnQuiz")
	public String sendAnQuiz(@RequestParam("answer") String answer, Model model, Principal principal) {
		//DODANIE ZNALEZIENIA ROLI UZYTKOWNIKA W DANYM PRZEDMIOCIE
//		currentIdSubject = idSubject;
		answers.add(answer);
		System.out.println("Odpowiedzi "+ answers.get(questionNr) + " "+ questionNr);
		questionNr++;
		if(questionNr == currentQuiz.getQuestions().size()) {
			List<Question> questions = currentQuiz.getQuestions();
			int correctAnswers = 0;
			for(int i = 0; i < questionNr; i++) {
				if(questions.get(i).getCorrect_answer().equals(Long.parseLong(answers.get(i)))) {
					correctAnswers++;
				}
			}
			System.out.println("Poprawnych odpowiedzi "+correctAnswers);
			double grade = 0;
			questionNr = 0;
			int userResultInPercentage = (correctAnswers*100)/questions.size();
			StudentGrade studentGrade=new StudentGrade();
			if(userResultInPercentage > 91) {
				studentGrade.setGrade(5.0);
				grade = 5;
			} else if(userResultInPercentage > 81) {
				studentGrade.setGrade(4.5);
				grade = 4.5;
			} else if(userResultInPercentage > 71) {
				studentGrade.setGrade(4.0);
				grade = 4;
			} else if(userResultInPercentage > 61) {
				studentGrade.setGrade(3.5);
				grade = 3.5;
			} else if(userResultInPercentage > 51) {
				studentGrade.setGrade(3.0);
				grade = 3;
			} else {
				studentGrade.setGrade(2.0);
				grade = 2;
			}
			answers = new ArrayList<>();
			model.addAttribute("idSubject", currentIdSubject);
			model.addAttribute("userResult", correctAnswers);
			model.addAttribute("bestResult", questions.size());
			model.addAttribute("userResultInPercentage", userResultInPercentage);
			model.addAttribute("grade" , grade);
			
			
			studentGrade.setSubject(currentSubject);
			studentGrade.setUserid((userService.findUserByEmail(principal.getName()).getId()));
			studentGradeService.addNewStudentGrade(studentGrade);
			return "user/quizResult";
		}
//		model.addAttribute("currentQuiz", currentQuiz);
//		model.addAttribute("idSubject", currentIdSubject);
//		currentSubject = subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10));
//		model.addAttribute("currentSubject", currentSubject);
		model.addAttribute("currentQuiz", currentQuiz);
		model.addAttribute("idSubject", currentIdSubject);
		model.addAttribute("question", currentQuiz.getQuestions().get(questionNr));
		
		return "user/takeAnQuiz";
	}

	@GetMapping("/user/rateTheCourse")
	public String rateTheCourse(@RequestParam(defaultValue="1") String idSubject,@RequestParam(defaultValue="1") String grade,
			Model model, Principal principal) {
		// SPRAWDZENIE CZY UZYTKOWNIK JEST W KURSIE JAKO STUDENT JESLI TAK TO MOZE WSTAWIC OCENE
		User user =userService.findUserByEmail(principal.getName());
		List<Access> access = accessService.findAccess(user.getId(), Long.parseLong(idSubject, 10));
		if(!access.isEmpty()) {
			
			currentAccessRole = userService.findRoleById(access.get(0).getRoleid());
			System.out.println(currentAccessRole.getRole());
			//System.out.println(currentAccessRole.getRole());
			if(currentAccessRole.getRole().equals("ROLE_STUDENT")) {
				List<CourseGrade> courseGradeByUser = courseGradeService.findIfYouRatedCourse(userService.
						findUserByEmail(principal.getName()).getId(), subjectService.findSubjectByID(Long.parseLong(idSubject, 10)));//prinicpal i subject znaleźć 
				if(courseGradeByUser.isEmpty()) {
					//Wstawienie oceny
					CourseGrade courseGrade = new CourseGrade(userService.findUserByEmail(principal.getName()).getId(), Integer.parseInt(grade),subjectService.findSubjectByID(Long.parseLong(idSubject, 10)));
					courseGradeService.addNewCourseGrade(courseGrade);
				}
			}
		} else {
			currentAccessRole = userService.findRoleById(201L);
			//System.out.println(currentAccessRole.getRole());
		}
		
		List<Subject> allSubjects = subjectService.findAllSubjects();
		model.addAttribute("courseGradeService", courseGradeService);
		model.addAttribute("allSubjects", allSubjects);
		return "user/allSubjects";
	}
	
	@GetMapping("/user/sendMessage")
	public String sendMessage(Model model) {
		model.addAttribute("message", new Message());
		model.addAttribute("idSubject", currentIdSubject);
		return "user/sendMessage";
	}
	
	@PostMapping("/user/sendMessage")
	public String sendMessage(@ModelAttribute Message message, Model model,BindingResult bindResult, Principal principal) {
		if(bindResult.hasErrors()) {		
            return "user/sendUser";
		}
		List<Access> accesses = accessService.findAccessWithUsers(202L, Long.parseLong(currentIdSubject, 10));
		//userService.findUserById(accesses.get(0).getUserid()).getEmail();
		String teacherEmail = userService.findUserById(accesses.get(0).getUserid()).getEmail();
		System.out.println("email nauczyciela " + teacherEmail+ " " + message.getTitle());
		message.setTo(teacherEmail);
		message.setFrom(principal.getName());
		messageService.sendMessage(message);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/sendMessage";
	}
	
	@GetMapping("/user/sendMessageToStudent")
	public String sendMessageToStudent(Model model) {
		model.addAttribute("message", new Message());
		model.addAttribute("idSubject", currentIdSubject);
		return "user/sendMessageToStudent";
	}
	
	@PostMapping("/user/sendMessageToStudent")
	public String sendMessageToStudent(@ModelAttribute Message message, Model model,BindingResult bindResult, Principal principal) {
		if(bindResult.hasErrors()) {		
            return "user/sendUser";
		}
		List<Access> accesses = accessService.findAccessWithUsers(202L, Long.parseLong(currentIdSubject, 10));
		//userService.findUserById(accesses.get(0).getUserid()).getEmail();
		String teacherEmail = userService.findUserById(accesses.get(0).getUserid()).getEmail();
		System.out.println("email nauczyciela " + teacherEmail+ " " + message.getTitle());
		message.setTo(studentEmail); // ZMIENIC
		message.setFrom(teacherEmail);
		messageService.sendMessage(message);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/sendMessageToStudent";
	}
	
	@GetMapping("/user/receiveMessageStudent")
	public String receiveMessageStudent(Model model, Principal principal) {
		List<Message> allMessages=messageService.findAllMessages(principal.getName());
		model.addAttribute("allMessages", allMessages);
		model.addAttribute("idSubject", currentIdSubject);
		return "user/receiveMessageStudent";
	}
	
	@GetMapping("/user/displayMessageStudent")
	public String displayMessageStudent(@RequestParam(defaultValue="1") String idMessage, Model model, Principal principal) {
	    System.out.println(idMessage);
		model.addAttribute("idSubject", currentIdSubject);
		Message msg=messageService.findMessageById(Long.parseLong(idMessage, 10));
		model.addAttribute("message", msg);
		studentEmail = msg.getFrom();
		return "user/displayMessageStudent";
	}
	
	
//	@PostMapping("/user/addNewLesson")
//    public String addLesson(@ModelAttribute @Valid Lesson lesson, 
//    		@ModelAttribute @Valid Subject currentSubject,
//            BindingResult bindResult) {
//        if(bindResult.hasErrors())
//            return "user/addNewLesson";
//        else {
//        	List<Lesson> currentLessons = currentSubject.getLessons();
//        	currentLessons.add(lesson);
//        	currentSubject.setLessons(currentLessons);
//            //lessonService.addNewLessons(lesson);
//            return "user/allLessons";
//        }
//    }
}
