package pl.java.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import pl.java.repository.EmailSender;

@Service
public class EmailSenderService implements EmailSender {

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(String from, String to, String title, String content) {
		MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo(from);
            helper.setFrom(from);
            helper.setSubject(title);
            helper.setText(content, true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mail);
		
	}

}
