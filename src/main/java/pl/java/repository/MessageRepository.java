package pl.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
	
	List<Message> findAllByTowho(String towho);
	
	
}
