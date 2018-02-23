package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.Message;
import pl.java.repository.MessageRepository;

@Service
public class MessageService {
	
	
	private MessageRepository messageRepository;
	
	@Autowired
	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public void sendMessage(Message message) {
		messageRepository.save(message);
	}
	
	public List<Message> findAllMessages(String towho) {
		return messageRepository.findAllByTowho(towho);
	}
	
	public Message findMessageById(long id)
	{
		return messageRepository.findOne(id);
	}
	
}
