package pl.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import pl.java.model.RequestAccess;

public interface RequestAccessRepository extends JpaRepository<RequestAccess, Long>{
	
	List<RequestAccess> findAllByUseridAndSubjectid(Long userid, Long subjectid);
	
	List<RequestAccess> findAllByRoleidAndSubjectid(Long roleid, Long subjectid);
	
	@Modifying
    @Transactional
    @Query("UPDATE RequestAccess SET roleid = 201 WHERE idaccess = ?1")
    public void removeUserFromSubject(Long idaccess);
}
