package pl.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.java.model.Access;

public interface AccessRepository extends JpaRepository<Access, Long>{

//	@Query("SELECT * FROM Access a WHERE a.userid = ?1 AND a.subjectid = ?2")
//    List<Access> findAccess(Long userid, Long subjectid);
	

    List<Access> findAllByUseridAndSubjectid(Long userid, Long subjectid);
    
    List<Access> findAllByRoleidAndSubjectid(Long roleid, Long subjectid);
    
    
    @Modifying
    @Transactional
    @Query("UPDATE Access SET roleid = 201 WHERE idaccess = ?1")
    public void removeUserFromSubject(Long idaccess);
}
