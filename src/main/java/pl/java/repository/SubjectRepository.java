package pl.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	
	//Subject findFirstByIdSubject(Long idSubject);

}
