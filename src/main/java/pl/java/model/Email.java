package pl.java.model;

public class Email {
	private String emailFrom;
	private String emailTo;
	private String title;
	private String content;
	
	
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Email() {};
	public Email(String emailFrom,String emailTo, String title, String content) {
		super();
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.title = title;
		this.content = content;
	}
	@Override
	public String toString() {
		return "Email [emailFrom=" + emailFrom + ", emailTo=" + emailTo +", title=" + title + ", content=" + content + "]";
	}
	
	
}
