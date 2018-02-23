package pl.java.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.java.model.Email;
import pl.java.repository.EmailSender;
import pl.java.service.EmailSenderService;
import pl.java.service.UserService;

@Controller
public class EmailController {
	
	private EmailSender emailSender;
	private UserService userService;
	
	@Autowired
	public void setEmailService(EmailSenderService emailSender) {
		this.emailSender = emailSender;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@PostMapping("/user/contact")
    public String addLesson( @ModelAttribute Email email, Model model) {
		if (email.getTitle().equals("") || email.getEmailFrom().equals("") || !userService.verifyMail(email.getEmailFrom()) || email.getContent().equals(""))
			return "user/contact";
		email.setEmailTo("remotegallery@gmail.com");
		emailSender.sendEmail(email.getEmailFrom(), email.getEmailTo(), email.getTitle(), email.getContent());
        return "user/home";
    }
}
