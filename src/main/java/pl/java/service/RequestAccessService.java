package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.RequestAccess;
import pl.java.repository.RequestAccessRepository;


@Service
public class RequestAccessService {
	
private RequestAccessRepository requestAccessRepository;
	
	@Autowired
	public void setRequestAccessRepository(RequestAccessRepository requestAccessRepository) {
		this.requestAccessRepository = requestAccessRepository;
	}
	
	public List<RequestAccess> findRequestAccess(Long userid, Long subjectid) {
    	return requestAccessRepository.findAllByUseridAndSubjectid(userid, subjectid);
    }
	
	public List<RequestAccess> findAccessWithUsers(Long roleid, Long subjectid) {
    	return requestAccessRepository.findAllByRoleidAndSubjectid(roleid, subjectid);
    }
	
	public void addNewAccess(RequestAccess requestAccess) {
		requestAccessRepository.save(requestAccess);
	}
	
	public void removeUserFromSubject(Long idaccess) {
		requestAccessRepository.removeUserFromSubject(idaccess);
	}
}
