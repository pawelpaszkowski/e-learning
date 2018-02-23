package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.Access;
import pl.java.repository.AccessRepository;

@Service
public class AccessService {
	
	private AccessRepository accessRepository;
	
	@Autowired
	public void setAccessRepository(AccessRepository accessRepository) {
		this.accessRepository = accessRepository;
	}
	
	public List<Access> findAccess(Long userid, Long subjectid) {
    	return accessRepository.findAllByUseridAndSubjectid(userid, subjectid);
    }
	
	public List<Access> findAccessWithUsers(Long roleid, Long subjectid) {
    	return accessRepository.findAllByRoleidAndSubjectid(roleid, subjectid);
    }
	
	public void addNewAccess(Access access) {
		accessRepository.save(access);
	}
	
	public void removeUserFromSubject(Long idaccess) {
		accessRepository.removeUserFromSubject(idaccess);
	}
}
